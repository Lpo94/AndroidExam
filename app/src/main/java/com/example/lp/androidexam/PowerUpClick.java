package com.example.lp.androidexam;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;


public class PowerUpClick extends GameObject
{
    public boolean clickable = false;

    private Bitmap powerScreenSpeed;
    private Bitmap  powerScreenFireball;


    private static PowerUpClick instance;
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
        rowsInSheet = 1;
        columnsInSheet = 1;
        bitmap = BitmapFactory.decodeResource(StaticValues.Instance().staticContext.getResources(), R.drawable.powerupscreen1);
        bitmapHeight = bitmap.getHeight() / rowsInSheet;
        bitmapWidth = bitmap.getWidth() / columnsInSheet;
        frameCount = 1;
        rect = new Rect(100,100,100,100); // denne nødvendig?? blir den ik auto sat?
        powerScreenSpeed = BitmapFactory.decodeResource(StaticValues.Instance().staticContext.getResources(), R.drawable.powerupscreen2);
        powerScreenFireball = BitmapFactory.decodeResource(StaticValues.Instance().staticContext.getResources(),R.drawable.powerupscreen3);
    }


    public void update()
    {
        pos = new Point(StaticValues.Instance().SCREEN_WIDTH / 2, StaticValues.Instance().SCREEN_HEIGHT / 2);
    }

    @Override
    public void draw(Canvas _canvas)
    {
        if (clickable == true)
        {
            if (StaticValues.Instance().globalPlayer.canShoot)
            {
                _canvas.drawBitmap(powerScreenFireball,StaticValues.Instance().SCREEN_WIDTH/2 -50 ,150,null);
            }

            if (StaticValues.Instance().globalPlayer.canSprint)
            {
                _canvas.drawBitmap(powerScreenSpeed, StaticValues.Instance().SCREEN_WIDTH / 2 - 50, 150, null);
            }

            bitmap.recycle();
        }
        else
        {
            _canvas.drawBitmap(bitmap, StaticValues.Instance().SCREEN_WIDTH / 2 - 50, 150, null);
        }
    }
}

