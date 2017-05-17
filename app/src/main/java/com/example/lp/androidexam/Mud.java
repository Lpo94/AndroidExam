package com.example.lp.androidexam;

import android.graphics.BitmapFactory;
import android.graphics.Point;

/**
 * Created by Shark on 25-04-2017.
 */

public class Mud extends GameObject
{

    public Mud(Point _pos)
    {
        pos = _pos;
        rowsInSheet = 1;
        columnsInSheet = 12;
        bitmap = BitmapFactory.decodeResource(StaticValues.staticContext.getResources(),R.drawable.mud);
        bitmapHeight = bitmap.getHeight() / rowsInSheet;
        bitmapWidth = bitmap.getWidth() / columnsInSheet;
        animationDelay = 150;
        frameCount = 11;
    }

}
