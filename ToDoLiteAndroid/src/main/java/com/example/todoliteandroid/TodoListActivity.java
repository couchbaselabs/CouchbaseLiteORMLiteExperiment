package com.example.todoliteandroid;

import android.app.Activity;
import android.os.Bundle;

public class TodoListActivity extends Activity {

    public static String INTENT_PARAMETER_LIST_NAME = "INTENT_PARAMETER_LIST_NAME";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle b = getIntent().getExtras();
        String listName = b.getString(INTENT_PARAMETER_LIST_NAME);
        System.out.println(listName);

    }
}
