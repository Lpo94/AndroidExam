package com.example.lp.androidexam;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;

import java.util.ArrayList;

/**
 * Created by LP on 18-04-2017.
 */

public abstract class GameObject {

    protected Rect rect;
    protected Point pos;
    protected ArrayList<GameObject> colliders;

// Animations relaterede variabler
    protected boolean usesAnimation;
    protected int bitmapHeight, bitmapWidth;
    protected Bitmap bitmap;
    protected int currentFrame = 0;
    protected int direction = 0;
    protected int rowsInSheet;
    protected int columnsInSheet;

    public void setUsesAnimation(boolean _value)
    {
        usesAnimation = _value;
    }

    public GameObject()
    {
        colliders = new ArrayList<>();
    }

    public Point getPos()
    {
        return pos;
    }

    public Point setPos(Point _pos)
    {
        pos = _pos;
        return pos;
    }

    public Rect getRect()
    {
        return rect;
    }



    public void update()
    {
        if(usesAnimation)
        {
            currentFrame = ++ currentFrame % columnsInSheet;
        }
    }

    public void draw(Canvas _canvas)
    {
        if(usesAnimation)
        {
            int sourceY = direction * bitmapHeight;
            int sourceX = currentFrame * bitmapWidth;

            Rect sourceRect = new Rect(sourceX, sourceY, sourceX + bitmapWidth, sourceY + bitmapHeight);
            Rect dstRect = new Rect(pos.x, pos.y, pos.x + bitmapWidth, pos.y + bitmapHeight);

            _canvas.drawBitmap(bitmap, sourceRect, dstRect, null);
        }
    }

    protected void doCollision(GameObject _other)
    {
    }

    public boolean onCollisionEnter(GameObject _other)
    {
        if(Rect.intersects(rect,_other.getRect()))
        {
            if(!colliders.contains(_other))
            {
                colliders.add(_other);
            }
        }

        return false;
    }

    public void onCollisionExit(GameObject _other)
    {
        if(!rect.contains(_other.getRect()))
        {
            if(colliders.contains(_other))
            {
                colliders.remove(_other);
            }
        }
    }
}
