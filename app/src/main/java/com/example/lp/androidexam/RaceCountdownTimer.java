package com.example.lp.androidexam;

import android.graphics.BitmapFactory;
import android.graphics.Point;

/**
 * Created by SharkGaming on 21/04/2017.
 */

public class RaceCountdownTimer extends GameObject
{
    public RaceCountdownTimer(Point _pos, int _rows, int _columns, int _bitmapId, long _animationSpeed, int _frameCount)
    {
        pos = _pos;
        rowsInSheet = _rows;
        columnsInSheet = _columns;
        bitmap = BitmapFactory.decodeResource(StaticValues.staticContext.getResources(),_bitmapId);
        bitmapHeight = bitmap.getHeight() / rowsInSheet;
        bitmapWidth = bitmap.getWidth() / columnsInSheet;
        setAnimationDelay(_animationSpeed);
        frameCount = _frameCount;
    }
}