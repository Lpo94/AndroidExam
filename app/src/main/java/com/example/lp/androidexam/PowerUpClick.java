package com.example.lp.androidexam;

import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.graphics.Rect;


public class PowerUpClick extends GameObject
{
    public static boolean Clickable = false;


    public PowerUpClick(Point _pos, int _rows, int _columns, int _bitmapId, long _animationSpeed, int _frameCount) {
        pos = _pos;
        rowsInSheet = _rows;
        columnsInSheet = _columns;
        bitmap = BitmapFactory.decodeResource(StaticValues.staticContext.getResources(),_bitmapId);
        bitmapHeight = bitmap.getHeight() / rowsInSheet;
        bitmapWidth = bitmap.getWidth() / columnsInSheet;
        setAnimationDelay(_animationSpeed);
        frameCount = _frameCount;
        rect = new Rect(100,100,100,100);
    }


}

