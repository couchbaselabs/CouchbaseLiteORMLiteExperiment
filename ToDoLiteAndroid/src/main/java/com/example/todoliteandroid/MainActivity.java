package com.example.todoliteandroid;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends Activity {

    TodoLiteArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);  // Remove title bar
        setContentView(R.layout.activity_main);
        createListCreationHandler();
        attachListAdapter();
        attachListClickListener();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    private void createListCreationHandler() {
        final EditText editText = (EditText) findViewById(R.id.editTextNewTodoList);
        editText.setImeOptions(EditorInfo.IME_ACTION_DONE);
        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    String newListName = editText.getText().toString();
                    adapter.add(newListName);
                    editText.setText("");
                }
                return false;
            }
        });
    }

    private void attachListAdapter() {
        final ListView listview = (ListView) findViewById(R.id.listViewAllTodoLists);
        List<String> fakeList = new ArrayList<String>(Arrays.asList("Foo List", "Bar List"));
        adapter = new TodoLiteArrayAdapter(this, android.R.layout.simple_list_item_1, fakeList);
        listview.setAdapter(adapter);
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
