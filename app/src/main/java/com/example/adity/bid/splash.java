package com.example.adity.bid;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

/**
 * Created by adity on 6/23/2017.
 */

public class splash extends Activity {
    private Handler handler=new Handler();
    @Override
    protected  void  onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);

        ImageView iv=(ImageView)findViewById(R.id.iv);
        iv.setScaleType(ImageView.ScaleType.FIT_XY);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                try {

                    Intent intent = new Intent(getApplicationContext(), login_or_signup.class);
                    startActivity(intent);
                    finish();  // Finish splash activity since it is no more needed
                } catch (Exception ignored) {
                    ignored.printStackTrace();
                }
            }
        },2000);

    }
}
