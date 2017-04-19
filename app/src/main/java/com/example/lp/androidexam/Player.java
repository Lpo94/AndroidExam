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
    private int colour;


    public Player(Point _pos, Rect _rect) {
        super(_pos, _rect);
        colour = new Color().RED;
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
        Paint paint = new Paint(colour);
        _canvas.drawRect(rect,paint);
    }
}
