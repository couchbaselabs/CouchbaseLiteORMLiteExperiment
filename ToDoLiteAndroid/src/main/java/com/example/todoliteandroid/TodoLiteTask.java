package com.example.todoliteandroid;

public class TodoLiteTask {

    private String name;

    public TodoLiteTask(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
