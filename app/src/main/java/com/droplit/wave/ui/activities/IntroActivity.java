package com.droplit.wave.ui.activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import com.droplit.wave.MainActivity;
import com.droplit.wave.ui.fragments.intro.FirstSlide;
import com.github.paolorotolo.appintro.AppIntro;


public class IntroActivity extends AppIntro {

    // Please DO NOT override onCreate. Use init
    @Override
    public void init(Bundle savedInstanceState) {

        // Add your slide's fragments here
        // AppIntro will automatically generate the dots indicator and buttons.
        addSlide(new FirstSlide(), getApplicationContext());
        //addSlide(new SecondSlide(), getApplicationContext());
        //addSlide(new ThirdSlide(), getApplicationContext());
        //addSlide(new FourthSlide(), getApplicationContext());

        // OPTIONAL METHODS
        // Override bar/separator color
        setBarColor(Color.parseColor("#3F51B5"));
        setSeparatorColor(Color.parseColor("#2196F3"));

        // Hide Skip button
        showSkipButton(false);

        // Turn vibration on and set intensity
        // NOTE: you will probably need to ask VIBRATE permesssion in Manifest
        //setVibrate(true);
        //setVibrateIntensity(30);
    }

    @Override
    public void onSkipPressed() {
        // Do something when users tap on Skip button.
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }

    @Override
    public void onDonePressed() {
        // Do something when users tap on Done button.
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }
}