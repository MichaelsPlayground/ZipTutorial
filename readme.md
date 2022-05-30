# Console with Roboto font

This concept study shows how to use your own fonts in an Android app. For this you need  
to download a font, e.g. the Roboto font family using this link from Google:

https://fonts.google.com/specimen/Roboto#standard-styles

After you unpacked the zip-file you have some files, all ending with ttf.

The following steps are described in this article:
https://developer.android.com/guide/topics/ui/look-and-feel/fonts-in-xml.html

Step 1: create a directory called **font** within the ressource (res) folder

Step 2: copy one or more of your downloaded fonts in the new folder but follow 
the filename rules - they filename has to be in lowercase letters only and only  
the underscore is allowed, for that reason my font file is named **roboto_regular.ttf**.

Step 3: As I want to use the new font within my TextView I'm adding this file to 
the TextView in activity_main.xml:
**android:fontFamily="@font/roboto_regular"**

Step 4: usually you won't see the new font in the Android Studio Design preview, just 
run the program on the emulator or real device for testing.

**Note:** this app is perfect to run your Java code on an Android device where there is no 
console output available. The app redirects the System.out.println to the TextView, 
that is embedded in a ScrollView for longer outputs. 

**App facts:** The app was generated on Android Studio Chipmunk 2021.2.1 with 
Build #AI-212.5712.43.2112.8512546, built on April 28, 2022. 
Runtime version: 11.0.12+0-b1504.28-7817840 aarch64, VM: OpenJDK 64-Bit Server.
The app is compiled on Android SDK 32 ("12") and is runnable on Android SDK 23+.
