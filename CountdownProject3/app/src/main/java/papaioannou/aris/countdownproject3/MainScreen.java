/* Created by: Aristotelis Papaioannou
 * CIS 436 - Project 3
*/

package papaioannou.aris.countdownproject3;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class MainScreen extends AppCompatActivity {

    int countdownSeconds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Nobody wants a message from you", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        Spinner spinner = (Spinner) findViewById(R.id.HighestSpinner); //Start the highest notification spinner disabled
        spinner.setEnabled(false);                                     //
    }


    //When Set Countdown button is clicked
    public void SetCountdownClick(View view)
    {
        EditText countdownBox = (EditText) findViewById(R.id.timeToNumb); //Box they set Time to Countdown
        String errorText = "Text Box was left Blank"; //Error Message;
        int seconds;

        if (countdownBox.getText().toString().length() != 0 ) //If the Text box is not empty
        {
            seconds = getTheSeconds(countdownBox);

            populateSpinner(seconds); //set up the highest notification spinner

            countdownSeconds = seconds;
        }
        else
        {
            countdownBox.setHint(errorText); //Set the Error text to red
            countdownBox.setHintTextColor(Color.RED); //
        }


    }


    //Get the seconds from the textbox
    private int getTheSeconds(EditText countdownBox)
    {
        int seconds;

        if(countdownBox.getText().toString().length() > 2)
        {
            seconds = Integer.valueOf(countdownBox.getText().toString().substring(0, 3)); //Only get the first 3 chars in the string (Limits it from not making the int over the int.max val
        }
        else
        {
            seconds = Integer.valueOf(countdownBox.getText().toString()); //Get the whole value of the string
        }

        seconds = validateNumber(seconds, countdownBox); //Make sure the number is not over 120 or less than 0

        return seconds;

    }


    //Make sure the seconds is not over 120 or less than 5
    //Will also make the seconds a multiple of 5
    //is passed in seconds and the box that the user entered seconds in
    private int validateNumber(int seconds, EditText countdownBox)
    {
        if(seconds > 120) //is seconds over 120
        {
            seconds = 120;
            Toast.makeText(this, "120 is the Max", Toast.LENGTH_SHORT).show();
        }
        else if (seconds < 5) //is seconds less than 5
        {
            seconds = 5;
            Toast.makeText(this, "5 is the Min", Toast.LENGTH_SHORT).show();
        }
        else //otherwise make it a multiple of 5
        {
            seconds = 5*(Math.round(seconds/5));
        }

        countdownBox.setText(seconds + ""); //set the text to the seconds
        countdownBox.setTextColor(Color.BLACK); //change the text to black
        return seconds;
    }

    //fills the spinner with the Highest Notification values
    //is passed in seconds
    private void populateSpinner(int seconds)
    {
        ArrayList<String> highNotif = new ArrayList<String>(); //Array list for Highest Notifications
        Spinner hnSpinner = (Spinner) findViewById(R.id.HighestSpinner); //Setting the Highest Notification spinner
        TextView hnTextView = (TextView) findViewById(R.id.highNot_text);

        String[] hnVals = {"1","5","10","20","30","60","90"}; //7 values for highest notification (7 in for loop)
        int hfLength = 6; //Default value will be to add all highest values to notification

        hnSpinner.setEnabled(true); //enable the spinner
        hnTextView.setTextColor(Color.BLACK);

        if(seconds > 90) //If seconds is higher than 90 seconds
        {
            hfLength = 7; //Spinner will show 1,5,10,20,30,60,90
        }
        else if (seconds > 60 && seconds <= 90) // If seconds is 61-90
        {
            hfLength = 6; //Spinner will show 1,5,10,20,30,60
        }
        else if (seconds > 30 && seconds <= 60) //If seconds is 31 - 60
        {
            hfLength = 5; //Spinner will show 1,5,10,20,30
        }
        else if (seconds > 20 && seconds <= 30) //If seconds is 21 - 30
        {
            hfLength = 4; //Spinner will show 1,5,10,20
        }
        else if (seconds > 10 && seconds <= 20) //If seconds is 11 - 20
        {
            hfLength = 3; //Spinner will show 1,5,10
        }
        else if (seconds > 5 && seconds <= 10) //if seconds is 6 - 10
        {
            hfLength = 2; //Spinner will show 1,5
        }
        else //If seconds is 5 or less (cant be less than 5)
        {
            hfLength = 1; //Spinner will show 1
        }


        for (int i = 0; i < hfLength; i++) //Populate the spinner based on how many notfications it needs
        {
            highNotif.add(" " + hnVals[i] + " Second(s)"); //add it to the spinner
        }


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, highNotif);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        hnSpinner.setAdapter(adapter);

    }

    public void startCountdown(View view)
    {
        TextView messageBox = (TextView) findViewById(R.id.message_text_box);
        EditText countdownBox = (EditText) findViewById(R.id.timeToNumb); //Box they set Time to Countdown

        int seconds = countdownSeconds;

        if(messageBox.getText().toString().length() != 0)
        {
            Toast.makeText(this, "Countdown for "+ messageBox.getText().toString() +" has started", Toast.LENGTH_SHORT).show();
            Toast.makeText(this, seconds+ "", Toast.LENGTH_SHORT).show();

        }
        else
        {
            Toast.makeText(this, "Please Enter a Message", Toast.LENGTH_SHORT).show();
            messageBox.setHint("Enter Message Here");
            messageBox.setHintTextColor(Color.RED);
        }


    }

}
