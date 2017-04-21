package com.example.lp.androidexam;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;

/**
 * Created by LP on 19-04-2017.
 */

public class Player extends GameObject {
    public int colour;
    private boolean canMove;

    public void setCanmove(boolean _value)
    {
        canMove = _value;
    }

    public Player(Point _pos) {
        super();
        pos = _pos;
        rect = new Rect(100,100,200,200);

        colour = new Color().GREEN;
    }

    @Override
    public void update() {
        super.update();

        if(canMove)
        {
            rect.set(pos.x-rect.width()/2,pos.y -rect.height()/2,
                    pos.x+rect.width()/2,pos.y+rect.height()/2);
        }
    }

    @Override
    protected void doCollision(GameObject _other) {
        super.doCollision(_other);
    }

    @Override
    public void draw(Canvas _canvas) {
        Paint paint = new Paint();
        paint.setColor(colour);
        _canvas.drawRect(rect,paint);
    }


}
