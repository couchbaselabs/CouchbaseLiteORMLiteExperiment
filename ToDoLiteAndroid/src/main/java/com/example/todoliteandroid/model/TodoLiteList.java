package com.example.todoliteandroid.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class TodoLiteList {

    @DatabaseField(id = true)
    private String name;

    public TodoLiteList() {
    }

    public TodoLiteList(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
