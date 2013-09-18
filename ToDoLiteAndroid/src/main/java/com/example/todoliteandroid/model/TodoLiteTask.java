package com.example.todoliteandroid.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class TodoLiteTask {

    @DatabaseField(id = true)
    private String name;

    @DatabaseField
    private boolean isSelected;

    @DatabaseField(canBeNull = false, foreign = true)
    private TodoLiteList list;

    public TodoLiteTask() {
    }

    public TodoLiteTask(String name, TodoLiteList list) {
        this.name = name;
        this.list = list;
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

    public TodoLiteList getList() {
        return list;
    }

    public void setList(TodoLiteList list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return name;
    }
}
