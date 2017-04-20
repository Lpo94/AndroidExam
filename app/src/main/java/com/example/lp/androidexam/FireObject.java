package com.example.lp.androidexam;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;

/**
 * Created by SharkGaming on 20/04/2017.
 */

public class FireObject extends GameObject
{


    public FireObject(Context _context, Point _pos, int _rows, int _columns)
    {
        setUsesAnimation(true);
        pos = _pos;
        rowsInSheet = _rows;
        columnsInSheet = _columns;
        bitmap = BitmapFactory.decodeResource(_context.getResources(), R.drawable.fire);
        bitmapHeight = bitmap.getHeight() / rowsInSheet;
        bitmapWidth = bitmap.getWidth() / columnsInSheet;
    }
}
