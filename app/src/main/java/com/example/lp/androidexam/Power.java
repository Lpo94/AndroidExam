package com.example.lp.androidexam;

import android.graphics.BitmapFactory;
import android.graphics.Point;



    public class Power extends GameObject
    {


        public Power(Point _pos, int _rows, int _columns, int _bitmapId, long _animationSpeed, int _frameCount)
        {
            pos = _pos;
            rowsInSheet = _rows;
            columnsInSheet = _columns;
            bitmap = BitmapFactory.decodeResource(StaticValues.staticContext.getResources(),R.drawable.powerupcoin);
            bitmapHeight = bitmap.getHeight() / rowsInSheet;
            bitmapWidth = bitmap.getWidth() / columnsInSheet;
            setAnimationDelay(_animationSpeed);
            frameCount = _frameCount;

        }
       /* public void update() {

        }*/
    }
