package com.example.lp.androidexam;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
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
    protected float speed;
    protected boolean isSolid;

// Animations relaterede variabler
    protected int bitmapHeight, bitmapWidth;
    protected Bitmap bitmap;
    protected int currentFrame = 0;
    protected int direction = 0;
    protected int rowsInSheet;
    protected int columnsInSheet;
    protected long startTime;
    protected long animationDelay;
    protected int frameCount;

    public void setAnimationDelay(long _value)
    {
        animationDelay = _value;
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
        if(frameCount > 1)
        {

            long elapsedTime = (System.nanoTime() -startTime) / 1000000;

            if(elapsedTime > animationDelay)
            {
                currentFrame++;
                startTime = System.nanoTime();

                if(currentFrame > frameCount)
                {
                    currentFrame = 0;
                }
            }
        }

        if(rect != null)
        {
            rect.set(pos.x-rect.width()/2,pos.y -rect.height()/2,
                    pos.x+rect.width()/2,pos.y+rect.height()/2);
        }
    }

    public void draw(Canvas _canvas)
    {
        if(bitmap != null)
        {
            int sourceY = direction * bitmapHeight;
            int sourceX = currentFrame * bitmapWidth;

            Rect sourceRect = new Rect(sourceX, sourceY, sourceX + bitmapWidth, sourceY + bitmapHeight);
            rect = new Rect(pos.x, pos.y, pos.x + bitmapWidth, pos.y + bitmapHeight);

            _canvas.drawBitmap(bitmap, sourceRect, rect, null);


        }
        if(rect != null)
        {
            Paint paint = new Paint();
            paint.setColor(Color.MAGENTA);
            paint.setStyle(Paint.Style.STROKE);
            _canvas.drawRect(rect,paint);
        }
    }

    protected void doCollision(GameObject _other)
    {

    }

    public boolean onCollisionEnter(GameObject _other)
    {
        if(_other != this && _other.rect != null) {
            if (Rect.intersects(rect, _other.getRect())) {
                if (!colliders.contains(_other)) {
                    colliders.add(_other);
                }
            }
        }

        return false;
    }

    public void onCollisionExit()
    {
        for(GameObject go:colliders) {
            if (!rect.contains(go.getRect())) {
                    colliders.remove(go);
            }
        }

    }

    public void onCollisionStay(GameObject _other)
    {
        for(GameObject go:colliders)
        {
            doCollision(go);
        }
    }
}
