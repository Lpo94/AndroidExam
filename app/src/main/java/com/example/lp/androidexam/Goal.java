package com.example.lp.androidexam;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Shark on 01-05-2017.
 */

public class Goal extends GameObject {

    public ArrayList<Player> finishedPlayers;
    GameView gv;

    public Goal(Point _pos)
    {
        finishedPlayers = new ArrayList<>();
  /*      gv = GameView.getInstance(StaticValues.Instance().staticContext); Sæt den her til null så vi ik riskierer at få flere overlappende spil */ // KASPER HER!!

        pos = _pos;
        rowsInSheet = 1;
        columnsInSheet = 1;
        bitmap = BitmapFactory.decodeResource(StaticValues.Instance().staticContext.getResources(),R.drawable.goal);
        bitmapHeight = bitmap.getHeight() / rowsInSheet;
        bitmapWidth = bitmap.getWidth() / columnsInSheet;
        frameCount = 1;
    }


    public void addPlayerToList(Player _player)
    {
        finishedPlayers.add(_player);

        Log.w("finishedplayers size: ", String.valueOf(finishedPlayers.size()));
        Log.w("allPlayers size: ", String.valueOf(StaticValues.Instance().allPlayers.size()));

        if(finishedPlayers.size() == StaticValues.Instance().allPlayers.size())
        {
            // game over flyt til score skærm når reset virker
            // Flyt til menu
      /*      gv = null; // Er det her nok?*/ // KASPER HER!!
/*            Intent backToMenu = new Intent(StaticValues.Instance().staticContext, MainActivity.class);
            StaticValues.Instance().staticContext.startActivity(backToMenu);*/
// evt brug shared preferences her istedet for bundle til at vidergive datane

            Intent endScreen = new Intent(StaticValues.Instance().staticContext, EndScreenActivity.class);
            Bundle customParameter = new Bundle();
            customParameter.putStringArray("finishedPlayers", new String[]
                    {
                            String.valueOf(finishedPlayers.get(0).getPlayerNumber()),
                            String.valueOf(finishedPlayers.get(1).getPlayerNumber()),
                            String.valueOf(finishedPlayers.get(2).getPlayerNumber()),
                            String.valueOf(finishedPlayers.get(3).getPlayerNumber()),
                    });
            endScreen.putExtras(customParameter);
            StaticValues.Instance().staticContext.startActivity(endScreen);
    /*        finish(); */
        }

    }
}
