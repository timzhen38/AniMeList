package com.example.animelist;

import android.app.Application;
import com.parse.Parse;

public class ParseApplication extends Application {
    @Override
    public void onCreate(){
        super.onCreate();

        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("fwFDQYObut4lJYaA10gMBtJeeCLFxJLDnIcE4xv7")
                .clientKey("k9gF9jLlUVDVPT2akZS8tEPpl67yviTAWP7FFUUU")
                .server("https://parseapi.back4app.com")
                .build()
        );
    }
}



