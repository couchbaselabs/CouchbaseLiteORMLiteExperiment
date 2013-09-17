package com.example.todoliteandroid;

import android.inputmethodservice.Keyboard;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.Toast;

public class TaskListGestureListener extends GestureDetector.SimpleOnGestureListener {

    private static final int SWIPE_MIN_DISTANCE = 150;
    private static final int SWIPE_MAX_OFF_PATH = 100;
    private static final int SWIPE_THRESHOLD_VELOCITY = 100;

    private MotionEvent mLastOnDownEvent = null;
    private OnFlingActionListener onFlingActionListener = null;

    public TaskListGestureListener(OnFlingActionListener onFlingActionListener) {
        super();
        this.onFlingActionListener = onFlingActionListener;
    }

    @Override
    public boolean onDown(MotionEvent e)
    {
        mLastOnDownEvent = e;
        return super.onDown(e);
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY)
    {
        if(e1 == null){
            e1 = mLastOnDownEvent;
        }
        if(e1==null || e2==null){
            return false;
        }

        float dX = e2.getX() - e1.getX();
        float dY = e1.getY() - e2.getY();

        if (Math.abs(dY) < SWIPE_MAX_OFF_PATH && Math.abs(velocityX) >= SWIPE_THRESHOLD_VELOCITY && Math.abs(dX) >= SWIPE_MIN_DISTANCE ) {
            if (dX > 0) {
                System.out.println("Right Swipe");
            } else {
                System.out.println("Left Swipe");
            }
            try {
                onFlingActionListener.deleteItemAtPosition(e1.getX(), e1.getY());
            } catch (Exception e) {
                e.printStackTrace();
            }
            return true;
        }
        else if (Math.abs(dX) < SWIPE_MAX_OFF_PATH && Math.abs(velocityY)>=SWIPE_THRESHOLD_VELOCITY && Math.abs(dY)>=SWIPE_MIN_DISTANCE ) {
            if (dY>0) {
                System.out.println("Up Swipe");
            } else {
                System.out.println("Down Swipe");
            }
            return true;
        }
        return false;
    }

    public static interface OnFlingActionListener {
        void deleteItemAtPosition(float x, float y);
    }


}
