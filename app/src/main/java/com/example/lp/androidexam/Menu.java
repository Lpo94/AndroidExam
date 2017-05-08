package com.example.lp.androidexam;

import android.content.Intent;
import android.os.PowerManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Menu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
    }

    public void Start(View v)
    {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }

    public void HowTo(View v)
    {
        Intent i = new Intent(this, HowTo.class);
        startActivity(i);
    }

    public void Exit(View v)
    {
        finish();
    }
}
