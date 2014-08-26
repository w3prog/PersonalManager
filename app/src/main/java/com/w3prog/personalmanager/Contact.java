package com.w3prog.personalmanager;

public class Contact {
    private long idContact;
    private long idPerson;
    private String description;

    //Полный конструктор для получения данных из БД
    public Contact(int idContact, int idPerson, String description) {
        this.idContact = idContact;
        this.idPerson = idPerson;
        this.description = description;
    }

    //сокращенный для ввенесения данных в БД
    public Contact(long idPerson, String description) {
        this.idPerson = idPerson;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return description;
    }
}
