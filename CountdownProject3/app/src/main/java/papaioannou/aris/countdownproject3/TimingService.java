package papaioannou.aris.countdownproject3;

import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

public class TimingService extends Service {

    MainScreen obj = new MainScreen();
    CountDownTimer countdown = null;

    NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
            .setSmallIcon(R.drawable.baughpicture)
            .setContentTitle("")
            .setContentText(""); //Setting up the Notification Builder

    Uri sound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
    public int  NotifID = 001;

    public TimingService() {
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        final int secon = intent.getExtras().getInt("secs");
        final int hnLength = intent.getExtras().getInt("high");
        final String message = intent.getExtras().getString("message");
        final NotificationManager notifMnger = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        countdown = new CountDownTimer(secon*1000, 1000) {
            @Override
            public void onTick(long miliseconds)
            {

                if(miliseconds/1000 == 90 && hnLength == 6 )
                {
                    notifyUser(message, 90, notifMnger);
                }
                else if (miliseconds/1000 == 60 && hnLength >= 5)
                {
                    notifyUser(message, 60, notifMnger);
                }
                else if (miliseconds/1000 == 30 &&  hnLength >= 4)
                {
                    notifyUser(message, 30, notifMnger);
                }
                else if (miliseconds/1000 == 20 && hnLength >= 3)
                {
                    notifyUser(message, 20, notifMnger);
                }
                else if (miliseconds/1000 == 10 && hnLength >= 2)
                {
                    notifyUser(message, 10, notifMnger);
                }
                else if (miliseconds/1000 == 5 && hnLength >= 1)
                {
                    notifyUser(message, 5, notifMnger);
                }
                else if (miliseconds/1000 == 1 && hnLength >= 0)
                {
                    notifyUser(message, 1, notifMnger);
                }
            }

            @Override
            public void onFinish() //When the countdown finishes
            {
                NotifID++;
                builder.setContentTitle("Timer Finished!");
                builder.setContentText("Time for: " + message);
                builder.setSound(sound);
                notifMnger.notify(NotifID, builder.build());
                onDestroy();
            }
        };

        countdown.start();
        return START_STICKY;
    }

    private void notifyUser(String message, int secon, NotificationManager notifMnger)
    {
        NotifID++;
        builder.setContentTitle("Timer Checkpoint!");
        builder.setContentText(secon + " seconds until " + message);
        builder.setSound(sound);
        notifMnger.notify(NotifID, builder.build());
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onDestroy()
    {
        countdown.cancel();
        super.onDestroy();
        Toast.makeText(this, "Service Stopped", Toast.LENGTH_SHORT).show();
    }
}
