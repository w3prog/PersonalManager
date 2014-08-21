package com.w3prog.personalmanager;


import java.util.ArrayList;
import java.util.Date;

public class Action {

    private long id;
    private String name;
    private Date date;
    private String description;
    private Task task;

    private ArrayList<Person> PersonsInAction = new ArrayList<Person>();

    //Для получения из базы данных
    public Action(long id, String name, Date date, String description) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.description = description;
    }
    //Для внесения в базу данных
    public Action(String name, Date date, String description) {
        this.name = name;
        this.date = date;
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public ArrayList<Person> getPersonsInAction() {
        return PersonsInAction;
    }
    // добавить персону
    public void addPerson(Person c){
        PersonsInAction.add(c);
    }

    //Удалить элемент в коллекцию персон
    public void removePerson(Person c){
        PersonsInAction.remove(c);
    }
    //заменить элемент в колекции персон
    public void updatePerson(int id,Person c){
        PersonsInAction.set(id,c);
    }
}
