package com.example.todoliteandroid;

import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

public class TaskListTouchListener implements View.OnTouchListener {

    private GestureDetector gestureDetector;

    public TaskListTouchListener(GestureDetector gestureDetector) {
        this.gestureDetector = gestureDetector;
    }

    @Override
    public boolean onTouch(View v, MotionEvent e) {
        if (gestureDetector.onTouchEvent(e)) {
            return true;
        } else {
            return false;
        }
    }

}
