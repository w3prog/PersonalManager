package com.w3prog.personalmanager;

import java.util.ArrayList;

public class Task {

    private long id;
    private String name;
    private String description;
    private ArrayList<Action> Actions;

    public Task(long id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public Task(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void addAction(Action action) {
        Actions.add(action);
    }

    public void removeAction(Action action) {
        Actions.remove(action);
    }

    public void removeAction(int i) {
        Actions.remove(i);
    }

    public void updateAction(int i, Action action) {
        Actions.set(i, action);
    }

    @Override
    public String toString() {
        return name;
    }
}
