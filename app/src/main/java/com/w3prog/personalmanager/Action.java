package com.w3prog.personalmanager;


import java.util.ArrayList;
import java.util.Date;

public class Action {

    private long id;
    private String name;
    private Date date;
    private String description;
    private Task task;

    private ArrayList<PersonsResult> PersonsInAction = new ArrayList<PersonsResult>();


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

    public ArrayList<PersonsResult> getPersonsInAction() {
        return PersonsInAction;
    }
    // добавить персону

    public void setPersons(ArrayList<PersonsResult> persons) {
        this.PersonsInAction = persons;
    }

    public void addPerson(Person c, Long l) {
        PersonsInAction.add(new PersonsResult(c, l));
    }

    //Удалить элемент в коллекцию персон
    public void removePerson(Person c) {
        PersonsInAction.remove(c);
    }

    //заменить элемент в колекции персон
    public void updatePerson(int id, Person c, Long l) {
        PersonsInAction.set(id, new PersonsResult(c, l));
    }

    public class PersonsResult {
        Person person;
        Long res;

        private PersonsResult(Person person, Long res) {
            this.person = person;
            this.res = res;
        }

        public Person getPerson() {
            return person;
        }

        public void setPerson(Person person) {
            this.person = person;
        }

        public Long getRes() {
            return res;
        }

        public void setRes(Long res) {
            this.res = res;
        }
    }
}
