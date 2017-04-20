package com.example.lp.androidexam;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Created by LP on 18-04-2017.
 */

public abstract class GameObject {

    protected Rect rect;
    protected Point pos;
    protected ArrayList<GameObject> colliders;

    public GameObject()
    {
        colliders = new ArrayList<>();
    }

    public Point GetPos()
    {
        return pos;
    }

    public Point SetPos(Point _pos)
    {
        pos = _pos;
        return pos;
    }

    public Rect GetRect()
    {
        return rect;
    }



    public void Update()
    {



    }

    public void Draw(Canvas _canvas)
    {


    }

    protected void DoCollision(GameObject _other)
    {


    }

    public boolean OnCollisionEnter(GameObject _other)
    {
        if(Rect.intersects(rect,_other.GetRect()))
        {
            if(!colliders.contains(_other))
            {
                colliders.add(_other);
            }
        }

        return false;
    }

    public void OnCollisionExit(GameObject _other)
    {
        if(!rect.contains(_other.GetRect()))
        {
            if(colliders.contains(_other))
            {
                colliders.remove(_other);
            }

        }
    }







}
