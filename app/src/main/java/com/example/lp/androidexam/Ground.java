package com.example.lp.androidexam;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.widget.Button;

/**
 * Created by LP on 25-04-2017.
 */

public class Ground extends GameObject {

    public Ground(Point _pos, Rect _rect)
    {
        isSolid = true;
        rect = _rect;
        pos = _pos;
    }

    @Override
    public void update() {
        super.update();

        if(pos.x > 2000 && pos.y < 0 && StaticValues.Instance().endgame == false)
        {
            //player.canMove = false;
            //StaticValues.Instance().gameObjects = StaticValues.Instance().objectsToRemove;
            //mainActivity.back();
            //StaticValues.Instance().globalPlayer = null;
        }
    }

    @Override
    public void draw(Canvas _canvas) {
        super.draw(_canvas);
        Paint paint = new Paint();
        paint.setColor(Color.CYAN);
        _canvas.drawRect(rect,paint);

        paint.setColor(Color.BLACK);
        paint.setTextSize(48);
        _canvas.drawText("X:" + pos.x + " Y:" + pos.y,pos.x,pos.y,paint);
    }
}
