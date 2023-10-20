package com.samansepahvand.cryptoapp.helper;


import android.app.Activity;
import android.os.Handler;
import android.view.View;

import com.samansepahvand.cryptoapp.MyApplication;

public class    Utility extends Activity {


    public static final int DelayTimeDialogAnimation = 500;

    public static void AnimRotate(View view) {
        view.startAnimation(MyApplication.SetAnimation("Rotate"));
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
            }
        }, DelayTimeDialogAnimation);
    }

}