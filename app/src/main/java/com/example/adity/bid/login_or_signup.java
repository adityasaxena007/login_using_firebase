package com.example.adity.bid;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;

import com.google.firebase.auth.FirebaseAuth;

public class login_or_signup extends TabActivity {
TabHost tabHost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tabHost=getTabHost();
        TabHost.TabSpec spec;
        Intent intent;


        if(FirebaseAuth.getInstance().getCurrentUser()!=null)
        {
            finish();
            startActivity(new Intent(login_or_signup.this,list.class));
        }


        /************* TAB1 ************/
        // Create  Intents to launch an Activity for the tab (to be reused)
        intent = new Intent().setClass(this, Sign_in_activity.class);
        spec = tabHost.newTabSpec("First").setIndicator("SIGN_IN")
                .setContent(intent);

        //Add intent to tab
        tabHost.addTab(spec);
        /************* TAB2 ************/
        intent = new Intent().setClass(this, Sign_up_activity.class);
        spec = tabHost.newTabSpec("Second").setIndicator("SIGN_UP")
                .setContent(intent);
        tabHost.addTab(spec);
       // tabHost.getTabWidget().getChildAt(0).setBackgroundResource(R.drawable.in);

        //tabHost.getTabWidget().getChildAt(1).setBackgroundResource(R.drawable.up);

        tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {




            }
        });

        // Set drawable images to tab
      /*  tabHost.getTabWidget().getChildAt(1).setBackgroundResource(R.drawable.Sign_up_activity);
        tabHost.getTabWidget().getChildAt(2).setBackgroundResource(R.drawable.tab3);

        // Set Tab1 as Default tab and change image
        tabHost.getTabWidget().setCurrentTab(0);
        tabHost.getTabWidget().getChildAt(0).setBackgroundResource(R.drawable.tab1_over);*/


    }


    }

