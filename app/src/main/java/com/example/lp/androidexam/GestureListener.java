package com.example.lp.androidexam;

import android.view.GestureDetector;
import android.view.MotionEvent;

/**
 * Created by LP on 20-04-2017.
 */

public class GestureListener extends GestureDetector.SimpleOnGestureListener{

    @Override
    public boolean onDown(MotionEvent e) {
        return true;
    }
//
//    @Override
//    public boolean onSingleTapConfirmed(MotionEvent e) {
//
//        float x = e.getX();
//        float y = e.getY();
//        if (x < StaticValues.SCREEN_WIDTH / 2) {
//            StaticValues.tempObjects.get(0).getPos().x -= 20;
//        }
//        else if (x > StaticValues.SCREEN_WIDTH / 2) {
//            StaticValues.tempObjects.get(0).getPos().x += 20;
//        }
//
//        return true;
//    }

    @Override
    public void onLongPress(MotionEvent e) {
        float x = e.getX();
        float y = e.getY();
        if (x < StaticValues.SCREEN_WIDTH / 2) {
            StaticValues.tempObjects.get(0).getPos().x -= 20;
        }
        else if (x > StaticValues.SCREEN_WIDTH / 2) {
            StaticValues.tempObjects.get(0).getPos().x += 20;
        }
    }

    @Override
    public boolean onDoubleTap(MotionEvent e) {
        StaticValues.tempObjects.get(0).getPos().y -= 20;
        return true;
    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return true;
    }
}
