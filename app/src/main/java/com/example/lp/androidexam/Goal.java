package com.example.lp.androidexam;

import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;

import java.util.ArrayList;

/**
 * Created by Shark on 01-05-2017.
 */

public class Goal extends GameObject {

    public ArrayList<Player> finishedPlayers;
    GameView gv;

    public Goal(Point _pos, int _rows, int _columns, int _bitmapId, long _animationSpeed, int _frameCount)
    {
        finishedPlayers = new ArrayList<>();
        gv = GameView.Instance(StaticValues.staticContext);

        pos = _pos;
        rowsInSheet = _rows;
        columnsInSheet = _columns;
        bitmap = BitmapFactory.decodeResource(StaticValues.staticContext.getResources(),R.drawable.goal);
        bitmapHeight = bitmap.getHeight() / rowsInSheet;
        bitmapWidth = bitmap.getWidth() / columnsInSheet;
        setAnimationDelay(_animationSpeed);
        frameCount = _frameCount;
    }


    public void addPlayerToList(Player _player)
    {
        finishedPlayers.add(_player);

        if(finishedPlayers.size() == StaticValues.allPlayers.size())
        {
            // game over flyt til score skærm når reset virker
            // Flyt til menu
            // all gameobjects = null skal jeg bruge den toRemove?
            // lav en lsite med alle obejkter der ikke er gameobjeter så de samme med go kan removes fra listen

            gv = null;
            gv.getGameThreadThread().setRunning(false);

            // Launch menuen
        }
    }
}
