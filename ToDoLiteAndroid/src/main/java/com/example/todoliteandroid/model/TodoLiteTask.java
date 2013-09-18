package com.example.todoliteandroid.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class TodoLiteTask {

    @DatabaseField(id = true)
    private String name;

    @DatabaseField
    private boolean isSelected;

    public TodoLiteTask() {
    }

    public TodoLiteTask(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    @Override
    public String toString() {
        return name;
    }
}
