package com.example.lp.androidexam;

import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.graphics.Rect;

import java.util.ArrayList;

/**
 * Created by SharkGaming on 20/04/2017.
 */

public class FireObject extends GameObject
{


    public FireObject(Point _pos)
    {
        isSolid = false;
        pos = _pos;
        rowsInSheet = 1;
        columnsInSheet = 3;
        bitmap = BitmapFactory.decodeResource(StaticValues.Instance().staticContext.getResources(),R.drawable.fire);
        bitmapHeight = bitmap.getHeight() / rowsInSheet;
        bitmapWidth = bitmap.getWidth() / columnsInSheet;
        animationDelay = 100;
        frameCount = 2;
//        rect = new Rect(100,100,200,200);
    }

    public void removeThis()
    {
        StaticValues.Instance().tempObjects.remove(this);
    }
}
