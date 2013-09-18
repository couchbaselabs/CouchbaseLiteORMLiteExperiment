package com.example.todoliteandroid;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.todoliteandroid.db.DatabaseHelper;
import com.example.todoliteandroid.model.TodoLiteList;
import com.j256.ormlite.android.apptools.OrmLiteBaseActivity;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;

import java.sql.SQLException;
import java.util.List;

public class MainActivity extends OrmLiteBaseActivity<DatabaseHelper> {

    TodoLiteArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);  // Remove title bar
        setContentView(R.layout.activity_main);
        createListCreationHandler();
        attachListClickListener();
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    private void fillList() throws SQLException {
        final ListView listView = (ListView) findViewById(R.id.listViewAllTodoLists);
        Dao<TodoLiteList, Integer> dao = getHelper().getTodoLiteListDao();
        QueryBuilder<TodoLiteList, Integer> builder = dao.queryBuilder();

        // uncomment to sort items by a field and limit # of items returned
        // builder.orderBy(TodoLiteTask.DATE_FIELD_NAME, false).limit(30L);

        List<TodoLiteList> list = dao.query(builder.prepare());
        adapter = new TodoLiteArrayAdapter(this, android.R.layout.simple_list_item_1, list);
        listView.setAdapter(adapter);
    }


    private void createListCreationHandler() {
        final EditText editText = (EditText) findViewById(R.id.editTextNewTodoList);
        editText.setImeOptions(EditorInfo.IME_ACTION_DONE);
        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (userHitEnter(actionId, keyEvent)) {
                    String newListName = editText.getText().toString();
                    TodoLiteList todoLiteList = new TodoLiteList(newListName);
                    try {
                        Dao<TodoLiteList, Integer> dao = getHelper().getTodoLiteListDao();
                        dao.create(todoLiteList);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                    adapter.add(todoLiteList);
                    editText.setText("");
                }
                return false;
            }

            private boolean userHitEnter(int actionId, KeyEvent keyEvent) {
                // on the emulator, actionId is 0 for some reason, so look at keyEvent
                return actionId == EditorInfo.IME_ACTION_DONE || keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER;
            }

        });
    }

    private void attachListClickListener() {
        final ListView listview = (ListView) findViewById(R.id.listViewAllTodoLists);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Intent intent = new Intent(MainActivity.this, TodoListActivity.class);
                Bundle b = new Bundle();
                String listItem = (String)listview.getItemAtPosition(position);
                b.putString(TodoListActivity.INTENT_PARAMETER_LIST_NAME, listItem);
                intent.putExtras(b);
                startActivity(intent);
            }
        });
    }

}
