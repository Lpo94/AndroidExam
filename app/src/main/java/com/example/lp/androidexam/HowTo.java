package com.example.lp.androidexam;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class HowTo extends AppCompatActivity {

    private int text = 0;
    Button button;
    TextView textView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_how_to);
        button = (Button) findViewById(R.id.Previous_button);
        button.setVisibility(View.INVISIBLE);
        Text();
    }

    public void Text()
    {
        if(text == 0)
        {
            textView = (TextView) findViewById(R.id.textView);
            textView.setVisibility(View.VISIBLE);
            textView = (TextView) findViewById(R.id.textView2);
            textView.setVisibility(View.INVISIBLE);
            button = (Button) findViewById(R.id.Previous_button);
            button.setVisibility(View.INVISIBLE);
            button = (Button) findViewById(R.id.Next_button);
            button.setVisibility(View.VISIBLE);
        }

        else if(text == 1)
        {
            textView = (TextView) findViewById(R.id.textView2);
            textView.setVisibility(View.VISIBLE);
            textView = (TextView) findViewById(R.id.textView);
            textView.setVisibility(View.INVISIBLE);
            button = (Button) findViewById(R.id.Previous_button);
            button.setVisibility(View.VISIBLE);
            button = (Button) findViewById(R.id.Next_button);
            button.setVisibility(View.INVISIBLE);
        }
    }

    public void Next(View v)
    {
        text ++;
        Text();
    }

    public void Previous(View v)
    {
        text --;
        Text();
    }

    public void Back(View v)
    {
        text = 0;
        finish();
        Intent i = new Intent(this, Menu.class);
        startActivity(i);
    }
}
