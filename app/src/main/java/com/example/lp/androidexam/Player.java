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


    public Player(Point _pos) {
        super();
        pos = _pos;
        rect = new Rect(100,100,200,200);

        colour = new Color().GREEN;
    }

    @Override
    public void Update() {
        super.Update();

        rect.set(pos.x-rect.width()/2,pos.y -rect.height()/2,
                pos.x+rect.width()/2,pos.y+rect.height()/2);
    }

    @Override
    protected void DoCollision(GameObject _other) {
        super.DoCollision(_other);
    }

    @Override
    public void Draw(Canvas _canvas) {
        Paint paint = new Paint();
        paint.setColor(colour);
        _canvas.drawRect(rect,paint);
    }


}
