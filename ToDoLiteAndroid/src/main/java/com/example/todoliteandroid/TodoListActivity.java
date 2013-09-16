package com.example.todoliteandroid;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v4.app.NavUtils;
import android.annotation.TargetApi;
import android.os.Build;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TodoListActivity extends Activity {

    public static final String INTENT_PARAMETER_LIST_NAME = "INTENT_PARAMETER_LIST_NAME";
    private TodoLiteTaskArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todolist);

        Bundle b = getIntent().getExtras();
        String listName = b.getString(INTENT_PARAMETER_LIST_NAME);

        setupActionBar(listName);
        attachListAdapter();
        createListCreationHandler();

    }

    /**
     * Set up the {@link android.app.ActionBar}, if the API is available.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private void setupActionBar(String title) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
        getActionBar().setDisplayHomeAsUpEnabled(true);
        }
        setTitle(title);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.todo_list, menu);
        return true;
    }
    

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // This ID represents the Home or Up button. In the case of this
                // activity, the Up button is shown. Use NavUtils to allow users
                // to navigate up one level in the application structure. For
                // more details, see the Navigation pattern on Android Design:
                //
                // http://developer.android.com/design/patterns/navigation.html#up-vs-back
                //
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void createListCreationHandler() {
        final EditText editText = (EditText) findViewById(R.id.editTextNewTask);
        editText.setImeOptions(EditorInfo.IME_ACTION_DONE);
        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    String newTaskName = editText.getText().toString();
                    adapter.add(new TodoLiteTask(newTaskName));
                    editText.setText("");
                }
                return false;
            }
        });
    }

    private void attachListAdapter() {
        final ListView listview = (ListView) findViewById(R.id.listViewTodoList);
        List<TodoLiteTask> fakeList = new ArrayList<TodoLiteTask>(
                Arrays.asList(new TodoLiteTask("Laundry"), new TodoLiteTask("Dishes"))
        );
        adapter = new TodoLiteTaskArrayAdapter(this, android.R.layout.simple_list_item_1, fakeList);
        listview.setAdapter(adapter);
    }



}
