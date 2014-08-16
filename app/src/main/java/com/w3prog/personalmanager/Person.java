package com.w3prog.personalmanager;


import java.util.ArrayList;

public class Person {
    private long id;
    private String FirstName;
    private String LastName;
    private String strImg;
    private String post;
    private ArrayList<Contact> Contacts;

    //Полный конструктор для получения данных из БД
    public Person(int id, String firstName, String lastName, String strImg, String post) {
        this.id = id;
        FirstName = firstName;
        LastName = lastName;
        this.strImg = strImg;
        this.post = post;
    }
    //сокращенный для ввенесения данных в БД
    public Person(String firstName, String lastName, String strImg, String post) {
        FirstName = firstName;
        LastName = lastName;
        this.strImg = strImg;
        this.post = post;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public String getStrImg() {
        return strImg;
    }

    public void setStrImg(String strImg) {
        this.strImg = strImg;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public long getId() {
        return id;
    }

    public ArrayList<Contact> getContacts() {
        return Contacts;
    }
    //добавить элемент в коллекцию контактов
    public void addContact(Contact c){
        Contacts.add(c);
    }


    //Удалить элемент в коллекцию контактов
    public void removeContect(Contact c){
        Contacts.remove(c);
    }
    //заменить элемент в колекции контактов
    public void updateContact(int id,Contact c){
        Contacts.set(id,c);
    }
}
