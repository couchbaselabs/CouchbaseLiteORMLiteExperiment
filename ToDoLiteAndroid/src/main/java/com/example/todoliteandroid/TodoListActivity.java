package com.example.todoliteandroid;

import android.os.Bundle;
import android.app.Activity;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v4.app.NavUtils;
import android.annotation.TargetApi;
import android.os.Build;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.todoliteandroid.db.DatabaseHelper;
import com.example.todoliteandroid.model.TodoLiteList;
import com.example.todoliteandroid.model.TodoLiteTask;
import com.j256.ormlite.android.apptools.OrmLiteBaseActivity;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TodoListActivity extends OrmLiteBaseActivity<DatabaseHelper> {

    public static final String INTENT_PARAMETER_LIST_NAME = "INTENT_PARAMETER_LIST_NAME";
    private TodoLiteTaskArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todolist);

        Bundle b = getIntent().getExtras();
        String listName = b.getString(INTENT_PARAMETER_LIST_NAME);

        setupActionBar(listName);
        attachGestureListener();
        createListCreationHandler();

    }

    @Override
    protected void onResume() {
        super.onResume();
        try {
            fillList();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
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

    private void fillList() throws SQLException {
        final ListView listView = (ListView) findViewById(R.id.listViewTodoList);
        Dao<TodoLiteTask, Integer> dao = getHelper().getTodoLiteTaskDao();
        QueryBuilder<TodoLiteTask, Integer> builder = dao.queryBuilder();

        // uncomment to sort items by a field and limit # of items returned
        // builder.orderBy(TodoLiteTask.DATE_FIELD_NAME, false).limit(30L);

        List<TodoLiteTask> tasks = dao.query(builder.prepare());
        adapter = new TodoLiteTaskArrayAdapter(this, R.layout.layout_taskrow, R.id.taskRowLabel, tasks);
        listView.setAdapter(adapter);
    }

    private void createListCreationHandler() {
        final EditText editText = (EditText) findViewById(R.id.editTextNewTask);
        editText.setImeOptions(EditorInfo.IME_ACTION_DONE);
        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_DONE || keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                    String newTaskName = editText.getText().toString();
                    TodoLiteTask todoLiteTask = new TodoLiteTask(newTaskName);
                    try {
                        Dao<TodoLiteTask, Integer> dao = getHelper().getTodoLiteTaskDao();
                        dao.create(todoLiteTask);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                    adapter.add(todoLiteTask);
                    editText.setText("");
                }
                return false;
            }
        });
    }


    private void attachGestureListener() {
        final ListView listview = (ListView) findViewById(R.id.listViewTodoList);
        TaskListGestureListener.OnFlingActionListener onFlingListener = new TaskListGestureListener.OnFlingActionListener() {
            @Override
            public void deleteItemAtPosition(float x, float y) {
                int itemId = listview.pointToPosition((int)x,(int)y);
                TodoLiteTask todoLiteTask = (TodoLiteTask) listview.getAdapter().getItem(itemId);
                try {
                    Dao<TodoLiteTask, Integer> dao = getHelper().getTodoLiteTaskDao();
                    dao.delete(todoLiteTask);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                adapter.remove(todoLiteTask);
            }
        };
        TaskListGestureListener listener = new TaskListGestureListener(onFlingListener);
        GestureDetector gestureDetector = new GestureDetector(this, listener);
        TaskListTouchListener onTouchListener = new TaskListTouchListener(gestureDetector);
        listview.setOnTouchListener(onTouchListener);
    }


}
