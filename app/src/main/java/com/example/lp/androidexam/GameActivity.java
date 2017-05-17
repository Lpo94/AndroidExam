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
import android.widget.Toast;

public class GameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        if(StaticValues.Instance().gameState == GameState.BluetoothMultiplayer)
        {

            StaticValues.Instance().mBTService = new BTService(this);

            StaticValues.Instance().mBTService.startClient(StaticValues.Instance().connectedDevice,StaticValues.Instance().MY_UUID_INSECURE);

        }


        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        StaticValues.Instance().staticContext = this;

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        StaticValues.Instance().SCREEN_WIDTH = dm.widthPixels;
        StaticValues.Instance().SCREEN_HEIGHT = dm.heightPixels;


        setContentView(new GameView(this));



        BroadcastReceiver msgReciever = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String text = intent.getStringExtra("theMessage");
                String[] textSplit = text.split("|");
                if(textSplit.length > 0) {
                    int xPos = Integer.parseInt(textSplit[0]);
                    int yPos = Integer.parseInt(textSplit[0]);
                }
//                StaticValues.Instance().btPlayer.getPos().x = xPos;
//                StaticValues.Instance().btPlayer.getPos().y = yPos;

                Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
//
//            messageBoard += messages;
//            incomingMessages.setText(messageBoard);
            }
        };

        LocalBroadcastManager.getInstance(this).registerReceiver(msgReciever,new IntentFilter("mBTPlayerUpdate"));

        setContentView(new GameView(this));
    }


    public void endGame()
    {
        GameView.gameThreadThread.setRunning(false);
        GameView.gameThreadThread = null;
        StaticValues.Instance().mBTService.stopService();
        StaticValues.Instance().mBTService = null;


        Intent intent = new Intent(this, MainActivity.class);
        StaticValues.Instance().staticContext.startActivity(intent);
        finish();


    }
}
