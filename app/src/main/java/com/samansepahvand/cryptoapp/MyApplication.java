package com.samansepahvand.cryptoapp;


import android.app.Application;
import android.content.Context;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.activeandroid.ActiveAndroid;


public class MyApplication extends Application {

    private static Context context;
    private static Animation animation;

    @Override
    public void onCreate() {
        super.onCreate();
        ActiveAndroid.initialize(this);
        MyApplication.context = getApplicationContext();
    }



    public  static Animation SetAnimation(String typeAnimation) {

        switch (typeAnimation) {
            case "Rotate":
                animation = AnimationUtils.loadAnimation(context, R.anim.anim_rotate_menu);
                break;
            case "RotateSlow":
                animation = AnimationUtils.loadAnimation(context, R.anim.rotate_anim);
                break;
            case "FadeIn":
                animation = AnimationUtils.loadAnimation(context, R.anim.fade_anim_dialog);
                break;
            case "FadeInLong":
                animation = AnimationUtils.loadAnimation(context, R.anim.fade_anim);
                break;
            case "SlideUp":
                animation = AnimationUtils.loadAnimation(context, R.anim.slide_up);
                break;
            case "SlideUpItem":
                animation = AnimationUtils.loadAnimation(context, R.anim.slide_up_item);
                break;
            case "SlideUpSlow":
                animation = AnimationUtils.loadAnimation(context, R.anim.slide_up_slow);
                break;
            case "FadeOut":
                animation = AnimationUtils.loadAnimation(context, R.anim.fade_out);
                break;
            case "SlideDownShort":
                animation = AnimationUtils.loadAnimation(context, R.anim.slide_down_short_destination);
                break;
            case "SlideDown":
                animation = AnimationUtils.loadAnimation(context, R.anim.slide_down);
                break;
            case "SlideDownSlow":
                animation = AnimationUtils.loadAnimation(context, R.anim.slide_down_slow);
                break;



        }
        return animation;

    }

}