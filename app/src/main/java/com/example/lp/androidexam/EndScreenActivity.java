package com.example.lp.androidexam;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import org.w3c.dom.Text;

import java.util.ArrayList;

import static com.example.lp.androidexam.R.id.place1;
import static com.example.lp.androidexam.R.id.place2;
import static com.example.lp.androidexam.R.id.place3;
import static com.example.lp.androidexam.R.id.place4;

public class EndScreenActivity extends AppCompatActivity {

    private ArrayList<String> listedPlayers;

    private EditText firstText;
    private EditText secondText;
    private EditText thirdText;
    private EditText fourthText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setupScreen();

        setContentView(R.layout.activity_end_screen);
    }

    private void setupScreen()
    {
        Bundle customParameter = getIntent().getExtras();
        if(customParameter != null)
        {
            listedPlayers = customParameter.getStringArrayList("finishedPlayers");

            firstText = (EditText) findViewById(place1);
            firstText.setText(listedPlayers.get(0));

            secondText = (EditText) findViewById(place2);
            secondText.setText(listedPlayers.get(1));

            thirdText = (EditText) findViewById(place3);
            thirdText.setText(listedPlayers.get(2));

            fourthText = (EditText) findViewById(place4);
            fourthText.setText(listedPlayers.get(3));

/*            for(int i = 0; i<listedPlayers.size(); i++)
            {
                if(i == 1) first  ="Player " + listedPlayers.get(i-1);
                if(i == 2) second ="Player " + listedPlayers.get(i-1);
                if(i == 3) third  ="Player " + listedPlayers.get(i-1);
                if(i == 4) fourth ="Player " + listedPlayers.get(i-1);
            }*/
        }


      /*  firstText = (EditText) findViewById(place1);
        firstText.setText(first);

        secondText = (EditText) findViewById(place2);
        secondText.setText(second);

        thirdText = (EditText) findViewById(place3);
        thirdText.setText(third);

        fourthText = (EditText) findViewById(place4);
        fourthText.setText(fourth);*/



        //text.getbyid.place1.settext = bla bla

        // first = _list[0]
        // ad en Winner on 1th place is: ud fra første pladsen
    }


    public void ReturnToMenu(View v)
    {
        Intent i = new Intent(this, Menu.class);
        startActivity(i);
    }
}
