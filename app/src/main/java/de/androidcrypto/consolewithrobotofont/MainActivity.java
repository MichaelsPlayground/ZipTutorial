package de.androidcrypto.consolewithrobotofont;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.os.Build;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/* ##### place your imports here ##### */

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

/* ##### place your imports here ##### */

public class MainActivity extends AppCompatActivity {

    String consoleText = "";
    String APPTITLE = "change the application title here";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnClearConsole = findViewById(R.id.btnClearConsole);
        Button btnRunCode = findViewById(R.id.btnRunCode);

        btnClearConsole.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //
                TextView textViewConsole = (TextView) findViewById(R.id.textviewConsole);
                consoleText = "";
                textViewConsole.setText(consoleText);
            }
        });

        btnRunCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearConsole();
                runMain();
            }
        });
    }

    public void clearConsole() {
        consoleText = "";
        TextView textViewConsole = (TextView) findViewById(R.id.textviewConsole);
        textViewConsole.setText(consoleText);
        MainActivity.this.setTitle(APPTITLE);
    }

    public void printlnX(String print) {
        consoleText = consoleText + print + "\n";
        TextView textViewConsole = (TextView) findViewById(R.id.textviewConsole);
        textViewConsole.setText(consoleText);
        System.out.println();
    }

    private static String getAndroidVersion() {
        String release = Build.VERSION.RELEASE;
        int sdkVersion = Build.VERSION.SDK_INT;
        return "Android SDK: " + sdkVersion + " (" + release + ")";
    }

    /* ############# your code comes below ####################
       change all code: System.out.println("something");
       to printlnX("something");
     */
    // place your main method here
    private void runMain() {
        printlnX("Android version: " + getAndroidVersion());
        printlnX("Get actual timestamp");
        for (int i = 0; i < 100; i++) {
            printlnX("Actual timestamp nr: " + i + " is " + getTimestampFormatted());
        }

        printlnX("");
    }

    private static String getTimestampFormatted() {
        // java.time is available from SDK 26+
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            DateTimeFormatter dtf = null;
            dtf = DateTimeFormatter.ofPattern("uuuu-MM-dd HH:mm:ss");
            LocalDateTime now = LocalDateTime.now();
            return dtf.format(now);
        } else {
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Calendar cal = Calendar.getInstance();
            return dateFormat.format(cal.getTime());
        }
    }
}