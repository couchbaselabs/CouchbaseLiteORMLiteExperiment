package com.example.todoliteandroid;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.example.todoliteandroid.model.TodoLiteTask;

import java.util.List;

public class TodoLiteTaskArrayAdapter extends ArrayAdapter<TodoLiteTask> {

    private final List<TodoLiteTask> list;
    private final Context context;
    private TodoLiteTaskUpdateListener taskUpdateListener;

    public TodoLiteTaskArrayAdapter(Context context, int resource, int textViewResourceId, List<TodoLiteTask> objects) {
        super(context, resource, textViewResourceId, objects);
        this.context = context;
        this.list = objects;
    }

    static class ViewHolder {
        protected TextView text;
        protected CheckBox checkbox;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = null;
        if (convertView == null) {
            Activity activity = (Activity) context;
            LayoutInflater inflator = activity.getLayoutInflater();
            view = inflator.inflate(R.layout.layout_taskrow, null);
            final ViewHolder viewHolder = new ViewHolder();
            viewHolder.text = (TextView) view.findViewById(R.id.taskRowLabel);
            viewHolder.checkbox = (CheckBox) view.findViewById(R.id.checkBox);
            viewHolder.checkbox
                    .setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                        @Override
                        public void onCheckedChanged(CompoundButton buttonView,
                                                     boolean isChecked) {
                            TodoLiteTask todoLiteTask = (TodoLiteTask) viewHolder.checkbox
                                    .getTag();
                            todoLiteTask.setSelected(isChecked);
                            if (taskUpdateListener != null) {
                                taskUpdateListener.taskUpdated(todoLiteTask);
                            }
                        }
                    });
            view.setTag(viewHolder);
            viewHolder.checkbox.setTag(list.get(position));
        } else {
            view = convertView;
            ((ViewHolder) view.getTag()).checkbox.setTag(list.get(position));
        }

        ViewHolder holder = (ViewHolder) view.getTag();
        TodoLiteTask todoLiteTask = list.get(position);
        String taskName = todoLiteTask.toString();
        holder.text.setText(taskName);
        holder.checkbox.setChecked(todoLiteTask.isSelected());
        return view;

    }

    public void setTaskUpdateListener(TodoLiteTaskUpdateListener taskUpdateListener) {
        this.taskUpdateListener = taskUpdateListener;
    }

    public static interface TodoLiteTaskUpdateListener {
        void taskUpdated(TodoLiteTask task);
    }

}
