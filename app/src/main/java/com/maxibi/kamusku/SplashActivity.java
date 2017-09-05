package com.maxibi.kamusku;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;

/**
 * Created by faiz-faizal on 8/19/2017.
 */

public class SplashActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
        String themeName = pref.getString("theme","Day");

        if(themeName.equals("Day"))
        {
            setTheme(R.style.AppThemeDay);
        }
        else if(themeName.equals("Night"))
        {
            setTheme(R.style.AppThemeNight);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen_layout);

        Thread welcomeThread = new Thread()
        {
            @Override
            public void run() {
                try{
                    super.run();
                    sleep(1000); //1 second delay
                }
                catch (Exception e){
                }
                finally{
                    Intent i = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(i);
                    finish();
                }
            }

            };
            welcomeThread.start();

    }
}
