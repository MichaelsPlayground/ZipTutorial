package de.androidcrypto.ziptutorial;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.os.Build;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/* ##### place your imports here ##### */

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

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

        // upload 2 files test1.txt and test2.txt to internal storage/files directory manually
        final List<String> srcFiles = Arrays.asList("test1.txt", "test2.txt");
        String zippedFilePath = "zippedfile.zip";
        try {
            File zippedFile = new File(getFilesDir(), zippedFilePath);
            printlnX("zippedFile: " + zippedFile.toString());
            FileOutputStream fos = new FileOutputStream(zippedFile);
            ZipOutputStream zipOut = new ZipOutputStream(fos);
            for (String srcFile : srcFiles) {
                File srcDir = new File(getFilesDir(), "");
                File fileToZip = new File(srcDir, srcFile);
                FileInputStream fis = new FileInputStream(fileToZip);
                ZipEntry zipEntry = new ZipEntry(fileToZip.getName());
                zipOut.putNextEntry(zipEntry);
                byte[] bytes = new byte[1024];
                int length;
                while ((length = fis.read(bytes)) >= 0) {
                    zipOut.write(bytes, 0, length);
                }
                //zipOut.closeEntry();
                fis.close();
            }
            zipOut.close();
            fos.close();
            printlnX("files successfully zipped");
        } catch (IOException e) {
            e.printStackTrace();
            printlnX("Exception: " + e);
        }
        printlnX("");
    }
}