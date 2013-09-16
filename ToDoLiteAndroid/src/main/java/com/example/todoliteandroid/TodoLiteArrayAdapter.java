package com.example.todoliteandroid;

import android.content.Context;
import android.widget.ArrayAdapter;

import java.util.List;

public class TodoLiteArrayAdapter extends ArrayAdapter<String> {

    public TodoLiteArrayAdapter(Context context, int textViewResourceId, List<String> objects) {
        super(context, textViewResourceId, objects);
    }
}
