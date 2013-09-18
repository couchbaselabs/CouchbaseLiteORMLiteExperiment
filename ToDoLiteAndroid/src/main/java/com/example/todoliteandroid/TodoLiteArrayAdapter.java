package com.example.todoliteandroid;

import android.content.Context;
import android.widget.ArrayAdapter;

import com.example.todoliteandroid.model.TodoLiteList;

import java.util.List;

public class TodoLiteArrayAdapter extends ArrayAdapter<TodoLiteList> {

    public TodoLiteArrayAdapter(Context context, int textViewResourceId, List<TodoLiteList> objects) {
        super(context, textViewResourceId, objects);
    }
}
