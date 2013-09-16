package com.example.todoliteandroid;

import android.content.Context;
import android.widget.ArrayAdapter;

import java.util.List;

public class TodoLiteTaskArrayAdapter extends ArrayAdapter<TodoLiteTask> {

    public TodoLiteTaskArrayAdapter(Context context, int textViewResourceId, List<TodoLiteTask> objects) {
        super(context, textViewResourceId, objects);
    }

}
