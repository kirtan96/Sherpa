package com.sherpa.sherpa.Activites;

/**
 * Created by Kirtan on 10/21/15.
 */

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.sherpa.sherpa.R;
import com.sherpa.sherpa.UserList;

import java.util.ArrayList;

public class SplashScreen extends AppCompatActivity {

    ArrayList<String> list = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        list.add("Start Screen");
        list.add("Stream Screen");
        list.add("End Screen");
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent mainIntent = new Intent(SplashScreen.this, MainActivity.class);
                startActivity(mainIntent);
                finish();
            }
        }, 1000);
    }


    ArrayList<String> list2 = new ArrayList<String>();

    public void onPushOpen(Context context, Intent intent) {
        Log.e("Push", "Clicked");
        Intent i = new Intent(context, UserList.class);
        //i.putExtras(intent.getExtras());
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
    }


    class Producer extends Thread {

        public void run() {
            for (int i = 0; i < list.size(); i++) {
                list2.add(list.get(i));
                try {
                    sleep((int) (Math.random() * 10));
                } catch (InterruptedException e) {
                }
            }
        }
    }

    class Consumer extends Thread {

        public void run() {
            synchronized (this){
                for (int i = 0; i < list.size(); i++) {
                    list2.remove(list.get(i));
            }
            }
        }
    }

    class SplashScreenThreadSync {
        public void main(String args[]) {
            Producer p1 = new Producer();
            Consumer c1 = new Consumer();
            p1.start();
            c1.start();
        }

    }
}