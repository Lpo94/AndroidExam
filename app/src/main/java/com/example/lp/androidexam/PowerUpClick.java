package com.example.lp.androidexam;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;


public class PowerUpClick extends GameObject
{
    public boolean clickable = false;
    private static PowerUpClick instance;
    public enum btnStates {empty, fire, speed,};
    public btnStates state;

    public static PowerUpClick getInstance()
    {
        if(instance == null)
        {
            instance = new PowerUpClick();
        }
        return instance;
    }

    private PowerUpClick()
    {
        state = btnStates.empty;
        rowsInSheet = 1;
        columnsInSheet = 3;
        bitmap = BitmapFactory.decodeResource(StaticValues.Instance().staticContext.getResources(), R.drawable.powerup_button);
        bitmapHeight = bitmap.getHeight() / rowsInSheet;
        bitmapWidth = bitmap.getWidth() / columnsInSheet;
        frameCount = 3;
    }

    @Override
    public void update()
    {
        pos = new Point(StaticValues.Instance().SCREEN_WIDTH / 2 - (bitmapWidth/2), (StaticValues.Instance().SCREEN_HEIGHT / 5) * 4);
        switch (state)
        {
            case empty:
                clickable = false;
                currentFrame = 0;
                break;

            case speed:
                clickable = true;
                currentFrame = 1;
                break;

            case fire:
                clickable = true;
                currentFrame = 2;
                break;
        }
    }


    @Override
    public void draw(Canvas _canvas)
    {
        super.draw(_canvas);
    }
}
