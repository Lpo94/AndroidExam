package com.example.lp.androidexam;

import android.graphics.BitmapFactory;
import android.graphics.Point;

/**
 * Created by Shark on 25-04-2017.
 */

public class Mud extends GameObject {

    public Mud(Point _pos, int _rows, int _columns, int _bitmapId, long _animationSpeed, int _frameCount)
    {
        pos = _pos;
        rowsInSheet = _rows;
        columnsInSheet = _columns;
        bitmap = BitmapFactory.decodeResource(StaticValues.staticContext.getResources(),R.drawable.mud);
        bitmapHeight = bitmap.getHeight() / rowsInSheet;
        bitmapWidth = bitmap.getWidth() / columnsInSheet;
        setAnimationDelay(_animationSpeed);
        frameCount = _frameCount;
    }
}
