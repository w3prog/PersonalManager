package com.w3prog.personalmanager;

public class Contact {
    private long idContact;
    private long idPerson;
    private String type;
    private String description;

    //Полный конструктор для получения данных из БД
    public Contact(int idContact, int idPerson, String type, String description) {
        this.idContact = idContact;
        this.idPerson = idPerson;
        this.type = type;
        this.description = description;
    }
    //сокращенный для ввенесения данных в БД
    public Contact(long idPerson, String type, String description) {
        this.idPerson = idPerson;
        this.type = type;
        this.description = description;
    }

    public long getIdContact() {
        return idContact;
    }

    public long getIdPerson() {
        return idPerson;
    }

    public void setIdPerson(int idPerson) {
        this.idPerson = idPerson;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
