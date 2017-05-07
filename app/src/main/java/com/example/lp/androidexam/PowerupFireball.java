package com.example.lp.androidexam;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;

import java.util.ArrayList;

/**
 * Created by SharkGaming on 05/05/2017.
 */

public class PowerupFireball extends GameObject
{
    private ArrayList<Player> hasAlreadyCollected;

    public PowerupFireball(Point _pos)
    {
        hasAlreadyCollected = new ArrayList<>();

        pos = _pos;
        isSolid = false;

        rowsInSheet = 1;
        columnsInSheet = 8;
        bitmap = BitmapFactory.decodeResource(StaticValues.staticContext.getResources(),R.drawable.pickup_fireball);
        bitmapHeight = bitmap.getHeight() / rowsInSheet;
        bitmapWidth = bitmap.getWidth() / columnsInSheet;
        animationDelay = 100;
        frameCount = 7;
        currentFrame = 1;
    }

    private void removeThis()
    {
        StaticValues.tempObjects.remove(this);
    }

    public boolean canPlayerCollect(Player _player)
    {
        if(hasAlreadyCollected.contains(_player))
        {
            return false;
        }
        else
        {
            playerHasCollected(_player);
            return true;
        }
    }

    private void playerHasCollected(Player _player)
    {
        hasAlreadyCollected.add(_player);

        if(hasAlreadyCollected.size() > 3)
        {
            removeThis();
        }
    }
}


