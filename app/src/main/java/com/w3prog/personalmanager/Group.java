package com.w3prog.personalmanager;

import java.util.ArrayList;

public class Group {
    private long id;
    private String name;
    private String description;
    private ArrayList<Person> personsInGroup;

    public Group(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public Group(long id, String name, String description) {
        this.id = id;
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

    public ArrayList<Person> getPersonsInGroup() {
        return personsInGroup;
    }

    public void AddPerson(Person p) {
        personsInGroup.add(p);
    }

    public void updatePerson(int i, Person p) {
        personsInGroup.set(i, p);
    }

    public void removePerson(Person p) {
        personsInGroup.remove(p);
    }

    @Override
    public String toString() {
        return name;
    }
}
