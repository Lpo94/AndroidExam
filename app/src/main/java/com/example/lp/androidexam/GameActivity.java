package com.example.lp.androidexam;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Point;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Window;
import android.view.WindowManager;

public class GameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        StaticValues.SCREEN_WIDTH = dm.widthPixels;
        StaticValues.SCREEN_HEIGHT = dm.heightPixels;


        setContentView(new GameView(this));



        BroadcastReceiver msgReciever = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String text = intent.getStringExtra("theMessage");
                String[] textSplit = text.split("|");
                int xPos = Integer.parseInt(textSplit[0]);
                int yPos = Integer.parseInt(textSplit[0]);
                StaticValues.btPlayer.getPos().x = xPos;
                StaticValues.btPlayer.getPos().y = yPos;
//
//            messageBoard += messages;
//            incomingMessages.setText(messageBoard);
            }
        };

        LocalBroadcastManager.getInstance(this).registerReceiver(msgReciever,new IntentFilter("mBTPlayerUpdate"));

    }
}
