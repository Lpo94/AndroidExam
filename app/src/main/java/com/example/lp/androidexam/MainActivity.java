package com.example.lp.androidexam;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import org.w3c.dom.Text;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

         getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

           DisplayMetrics dm = new DisplayMetrics();
           getWindowManager().getDefaultDisplay().getMetrics(dm);

            StaticValues.SCREEN_WIDTH = dm.widthPixels;
            StaticValues.SCREEN_HEIGHT = dm.heightPixels;

            setContentView(GameView.Instance(this));

    }


    public void back()
    {
        Intent i = new Intent(this, Menu.class);
        startActivity(i);
        setContentView(R.layout.activity_menu);
    }
}