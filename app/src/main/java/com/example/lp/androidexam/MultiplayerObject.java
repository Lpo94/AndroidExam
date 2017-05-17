package com.example.lp.androidexam;

import android.graphics.BitmapFactory;
import android.graphics.Point;

/**
 * Created by Lars-Peter on 16/05/2017.
 */

public class MultiplayerObject extends GameObject    {
    public MultiplayerObject(Point _pos)
    {
        bitmap = BitmapFactory.decodeResource(StaticValues.Instance().staticContext.getResources(),R.drawable.giraf_sheet);

        pos = _pos;
        rowsInSheet = 1;
        columnsInSheet = 14;
        bitmapHeight = bitmap.getHeight() / rowsInSheet;
        bitmapWidth = bitmap.getWidth() / columnsInSheet;
        setAnimationDelay(100);
        frameCount = 14;



    }





}
