package com.w3prog.personalmanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.Date;


public class DataBase {

    private static final String TAG = "DataBase.class";
    //Таблица PERSON
    private static final String TABLE_PERSON = "person";
    private static final String ROW_PERSON_ID = "id";
    private static final String ROW_PERSON_FIRST_NAME = "firstName";
    private static final String ROW_PERSON_LAST_NAME = "lastName";
    private static final String ROW_PERSON_SRC_IMG = "src_img";
    private static final String ROW_PERSON_POST = "post";
    //Таблица CONTACT
    private static final String TABLE_CONTACT = "contact";
    private static final String ROW_CONTACT_ID = "id";
    private static final String ROW_CONTACT_ID_PERSON = "id_person";
    private static final String ROW_CONTACT_DESCRIPTION = "description";
    //Таблица GROUP
    private static final String TABLE_GROUP = "groupContact";
    private static final String ROW_GROUP_ID = "id";
    private static final String ROW_GROUP_NAME_GROUP = "NameGroup";
    private static final String ROW_GROUP_DESCRIPTION = "GroupDescription";

    //Таблица TASK
    private static final String TABLE_TASK = "task";
    private static final String ROW_TASK_ID = "id";
    private static final String ROW_TASK_NAME = "NameTask";
    private static final String ROW_TASK_DESCRIPTION = "description";
    //Таблица ACTION
    private static final String TABLE_ACTION = "action";
    private static final String ROW_ACTION_ID = "id";
    private static final String ROW_ACTION_NAME = "NameAction";
    private static final String ROW_ACTION_DATE = "Date";
    private static final String ROW_ACTION_DESCRIPTION = "Description";
    private static final String ROW_ACTION_TASK_ID = "taskId";
    //Таблица PERSON_IN_ACTION
    private static final String TABLE_PERSON_IN_ACTION = "person_in_action";
    private static final String ROW_PERSON_IN_ACTION_ID_PERSON = "idPerson";
    private static final String ROW_PERSON_IN_ACTION_ID_ACTION = "idAction";
    private static final String ROW_PERSON_IN_ACTION_RESULT = "result";
    //Таблица PERSON_IN_GROUP
    private static final String TABLE_PERSON_IN_GROUP = "PersonInGroup";
    private static final String ROW_PERSON_IN_GROUP_ID_PERSON = "idPerson";
    private static final String ROW_PERSON_IN_GROUP_ID_GROUP = "idGroup";

    private static final String TOTAL_RESULT = "totalResult";

    Context context;
    SQLiteDatabase database;
    private static DataBase sDataBase;

    private DataBase(Context context) {
        this.context = context;
        myDataBaseHelper newDBH = new myDataBaseHelper(context);
        database = newDBH.getWritableDatabase();
        generateDataBase();
    }

    //операции с таблицей Person
    public long insertNewPerson() {
        ContentValues cv = new ContentValues();
        cv.put(ROW_PERSON_FIRST_NAME, "");
        cv.put(ROW_PERSON_LAST_NAME, "");
        cv.put(ROW_PERSON_SRC_IMG, "");
        cv.put(ROW_PERSON_POST, "");
        return database.insert(TABLE_PERSON, null, cv);
    }

    public long insertPerson(Person p) {
        ContentValues cv = new ContentValues();
        cv.put(ROW_PERSON_FIRST_NAME, p.getFirstName());
        cv.put(ROW_PERSON_LAST_NAME, p.getLastName());
        cv.put(ROW_PERSON_SRC_IMG, p.getStrImg());
        cv.put(ROW_PERSON_POST, p.getPost());
        return database.insert(TABLE_PERSON, null, cv);
    }

    public void updatePerson(int id, Person p) {
        ContentValues cv = new ContentValues();

        cv.put(ROW_PERSON_ID, p.getId());
        cv.put(ROW_PERSON_FIRST_NAME, p.getFirstName());
        cv.put(ROW_PERSON_LAST_NAME, p.getLastName());
        cv.put(ROW_PERSON_SRC_IMG, p.getStrImg());
        cv.put(ROW_PERSON_POST, p.getPost());
        database.update(TABLE_PERSON, cv, ROW_PERSON_ID + " = ?",
                new String[]{Integer.toString(id)});

    }

    public void deletePerson(int id) {
        database.delete(TABLE_PERSON, ROW_PERSON_ID + " = " + id, null);
    }

    public Person getPerson(long id) {
        Cursor c = database.query(TABLE_PERSON,
                null,
                ROW_PERSON_ID + "=" + id,
                null, null, null, null);
        Person person;

        if (c.moveToFirst()) {
            person = new Person(
                    c.getInt(c.getColumnIndex(ROW_PERSON_ID)),
                    c.getString(c.getColumnIndex(ROW_PERSON_FIRST_NAME)),
                    c.getString(c.getColumnIndex(ROW_PERSON_LAST_NAME)),
                    c.getString(c.getColumnIndex(ROW_PERSON_SRC_IMG)),
                    c.getString(c.getColumnIndex(ROW_PERSON_POST))
            );
            return person;
        } else {
            return null;
        }
    }

    public ArrayList<Person> getPersons() {
        ArrayList<Person> arrayList = new ArrayList<Person>();
        Cursor c = database.rawQuery("Select " + ROW_PERSON_ID + ", " +
                ROW_PERSON_FIRST_NAME + ", " +
                ROW_PERSON_LAST_NAME + ", " +
                ROW_PERSON_SRC_IMG + ", " +
                ROW_PERSON_POST + " from " + TABLE_PERSON + " " +
                "order by " + ROW_PERSON_ID, null);
        if (c.moveToFirst()) {
            do {
                arrayList.add(new Person(
                        c.getInt(c.getColumnIndex(ROW_PERSON_ID)),
                        c.getString(c.getColumnIndex(ROW_PERSON_FIRST_NAME)),
                        c.getString(c.getColumnIndex(ROW_PERSON_LAST_NAME)),
                        c.getString(c.getColumnIndex(ROW_PERSON_SRC_IMG)),
                        c.getString(c.getColumnIndex(ROW_PERSON_POST))
                ));
                c.moveToNext();
            } while (c.isAfterLast() == false);
        }
        return arrayList;
    }
//Конец операций с таблицей Person

//Операции с таблицей Contacts

    public long insertNewConcact(Person person) {
        ContentValues cv = new ContentValues();
        cv.put(ROW_CONTACT_ID_PERSON, person.getId());
        cv.put(ROW_CONTACT_DESCRIPTION, "");
        return database.insert(TABLE_CONTACT, null, cv);
    }

    public long insertContact(Contact c) {
        ContentValues cv = new ContentValues();
        cv.put(ROW_CONTACT_ID_PERSON, c.getIdPerson());
        cv.put(ROW_CONTACT_DESCRIPTION, c.getDescription());
        return database.insert(TABLE_CONTACT, null, cv);
    }

    public void updateContact(int id, Contact c) {
        ContentValues cv = new ContentValues();

        cv.put(ROW_CONTACT_ID, c.getIdContact());
        cv.put(ROW_CONTACT_ID_PERSON, c.getIdPerson());
        cv.put(ROW_CONTACT_DESCRIPTION, c.getDescription());
        database.update(TABLE_CONTACT, cv, ROW_CONTACT_ID + " = ?",
                new String[]{Integer.toString(id)});
    }

    public void deleteContact(int id) {
        database.delete(TABLE_CONTACT, ROW_CONTACT_ID + " = " + id, null);
    }

    //скорее всего не понадобится
    public Contact getContact(int id) {

        Cursor c = database.query(TABLE_CONTACT,
                null,
                ROW_CONTACT_ID + "=" + id,
                null, null, null, null);
        Contact contact;
        if (c.moveToFirst()) {
            contact = new Contact(
                    c.getInt(c.getColumnIndex(ROW_CONTACT_ID)),
                    c.getInt(c.getColumnIndex(ROW_CONTACT_ID_PERSON)),
                    c.getString(c.getColumnIndex(ROW_CONTACT_DESCRIPTION))
            );
            return contact;
        } else {
            return null;
        }
    }

    //скорее всего не понадобится
    public ArrayList<Contact> getContacts() {
        ArrayList<Contact> arrayList = new ArrayList<Contact>();
        Cursor c = database.rawQuery("Select " + ROW_CONTACT_ID + ", " +
                ROW_CONTACT_ID_PERSON + ", " +
                ROW_CONTACT_DESCRIPTION + " from " + TABLE_CONTACT + " " +
                "order by " + ROW_CONTACT_ID, null);
        if (c.moveToFirst()) {
            do {
                arrayList.add(new Contact(
                        c.getInt(c.getColumnIndex(ROW_CONTACT_ID)),
                        c.getInt(c.getColumnIndex(ROW_CONTACT_ID_PERSON)),
                        c.getString(c.getColumnIndex(ROW_CONTACT_DESCRIPTION))
                ));
                c.moveToNext();
            } while (c.isAfterLast() == false);
        }
        return arrayList;
    }

    public ArrayList<Contact> getPhoneContactPerson(Person person) {
        ArrayList<Contact> arrayList = new ArrayList<Contact>();
        Cursor c = database.query(TABLE_CONTACT,
                null,
                ROW_CONTACT_ID_PERSON + "=" + person.getId(),
                null, null, null, null);

        if (c.moveToFirst()) {
            do {
                arrayList.add(new Contact(
                        c.getInt(c.getColumnIndex(ROW_CONTACT_ID)),
                        c.getInt(c.getColumnIndex(ROW_CONTACT_ID_PERSON)),
                        c.getString(c.getColumnIndex(ROW_CONTACT_DESCRIPTION))
                ));
                c.moveToNext();
            } while (c.isAfterLast() == false);
        }
        return arrayList;
    }

    //Окончание операций с таблицей Contacts
    // начало операций с таблицей Action
    public long insertNewAction() {
        ContentValues cv = new ContentValues();
        cv.put(ROW_ACTION_NAME, "");
        cv.put(ROW_ACTION_DESCRIPTION, "");
        cv.put(ROW_ACTION_DATE, (new Date()).getTime());

        return database.insert(TABLE_ACTION, null, cv);
    }

    public long insertAction(Action c) {
        ContentValues cv = new ContentValues();
        cv.put(ROW_ACTION_NAME, c.getName());
        cv.put(ROW_ACTION_DATE, c.getDate().getTime());
        cv.put(ROW_ACTION_DESCRIPTION, c.getDescription());
        return database.insert(TABLE_ACTION, null, cv);
    }

    public long insertAction(Action c, long task) {
        ContentValues cv = new ContentValues();
        cv.put(ROW_ACTION_NAME, c.getName());
        cv.put(ROW_ACTION_DATE, c.getDate().getTime());
        cv.put(ROW_ACTION_DESCRIPTION, c.getDescription());
        cv.put(ROW_ACTION_TASK_ID, task);
        return database.insert(TABLE_ACTION, null, cv);
    }

    public long insertAction(Action c, Task task) {
        ContentValues cv = new ContentValues();
        cv.put(ROW_ACTION_NAME, c.getName());
        cv.put(ROW_ACTION_DATE, c.getDate().getTime());
        cv.put(ROW_ACTION_DESCRIPTION, c.getDescription());
        cv.put(ROW_ACTION_TASK_ID, task.getId());
        return database.insert(TABLE_ACTION, null, cv);
    }

    public void updateAction(int id, Action c) {
        ContentValues cv = new ContentValues();
        //тип int
        cv.put(ROW_ACTION_ID, id);
        cv.put(ROW_ACTION_NAME, c.getName());
        cv.put(ROW_ACTION_DATE, c.getDate().getTime());
        cv.put(ROW_ACTION_DESCRIPTION, c.getDescription());
        if (c.getTask() != null) {
            cv.put(ROW_ACTION_TASK_ID, c.getTask().getId());
        }
        int updCount = database.update(TABLE_ACTION, cv, ROW_ACTION_ID + " = ?",
                new String[]{Integer.toString(id)});
    }

    public void deleteAction(int id) {
        database.delete(TABLE_ACTION, ROW_ACTION_ID + " = " + id, null);
    }

    public Action getAction(long id) {
        //todo нужно решить проблему со ссылками
        Cursor c = database.query(TABLE_ACTION,
                null,
                ROW_ACTION_ID + "=" + id,
                null, null, null, null);
        Action action;
        if (c.moveToFirst()) {
            action = new Action(
                    c.getInt(c.getColumnIndex(ROW_ACTION_ID)),
                    c.getString(c.getColumnIndex(ROW_ACTION_NAME)),
                    new Date(c.getLong(c.getColumnIndex(ROW_ACTION_DATE))),
                    c.getString(c.getColumnIndex(ROW_ACTION_DESCRIPTION))
            );
            Task task = getTask(c.getLong(c.getColumnIndex(ROW_ACTION_TASK_ID)));
            action.setTask(task);
            return action;
        } else {
            return null;
        }
    }

    public ArrayList<Action> getActions() {
        ArrayList<Action> arrayList = new ArrayList<Action>();
        Cursor c = database.rawQuery("Select " + ROW_ACTION_ID + ", " +
                ROW_ACTION_NAME + ", " +
                ROW_ACTION_DATE + ", " +
                ROW_ACTION_DESCRIPTION + " from " + TABLE_ACTION + " " +
                "order by " + ROW_ACTION_ID, null);
        if (c.moveToFirst()) {
            do {
                arrayList.add(new Action(
                        c.getInt(c.getColumnIndex(ROW_ACTION_ID)),
                        c.getString(c.getColumnIndex(ROW_ACTION_NAME)),
                        new Date(c.getLong(c.getColumnIndex(ROW_ACTION_DATE))),
                        c.getString(c.getColumnIndex(ROW_ACTION_DESCRIPTION))
                ));
                c.moveToNext();
            } while (c.isAfterLast() == false);
        }
        return arrayList;
    }

    public ArrayList<Action> getActionOfTask(Task task) {
        ArrayList<Action> arrayList = new ArrayList<Action>();
        Cursor c = database.query(TABLE_ACTION, null,
                ROW_ACTION_TASK_ID + "=" + task.getId(),
                null, null, null, null);
        if (c.moveToFirst()) {
            do {
                arrayList.add(new Action(
                        c.getInt(c.getColumnIndex(ROW_ACTION_ID)),
                        c.getString(c.getColumnIndex(ROW_ACTION_NAME)),
                        new Date(c.getLong(c.getColumnIndex(ROW_ACTION_DATE))),
                        c.getString(c.getColumnIndex(ROW_ACTION_DESCRIPTION))
                ));
                c.moveToNext();
            } while (c.isAfterLast() == false);
        }
        return arrayList;
    }

    // конец операция с таблицей Actions
// начало операций с таблицей Task

    //скорее всего не понадобится
    public long insertTask(Task task) {
        ContentValues cv = new ContentValues();
        cv.put(ROW_TASK_NAME, task.getName());
        cv.put(ROW_TASK_DESCRIPTION, task.getDescription());
        return database.insert(TABLE_TASK, null, cv);
    }

    public long insertNewTask() {
        ContentValues cv = new ContentValues();
        cv.put(ROW_TASK_NAME, "");
        cv.put(ROW_TASK_DESCRIPTION, "");
        return database.insert(TABLE_TASK, null, cv);
    }

    public void updateTask(int id, Task task) {
        ContentValues cv = new ContentValues();
        cv.put(ROW_TASK_ID, id);
        cv.put(ROW_TASK_NAME, task.getName());
        cv.put(ROW_ACTION_DESCRIPTION, task.getDescription());
        database.update(TABLE_TASK, cv, ROW_TASK_ID + " = ?",
                new String[]{Integer.toString(id)});
    }

    public void deleteTask(long id) {
        database.delete(TABLE_ACTION, ROW_ACTION_TASK_ID + " = " + id, null);
        database.delete(TABLE_TASK, ROW_TASK_ID + " = " + id, null);
    }

    public Task getTask(long taskID) {

        Cursor c = database.query(TABLE_TASK,
                null,
                ROW_TASK_ID + "=" + taskID,
                null, null, null, null);
        Task task;
        if (c.moveToFirst()) {

            task = new Task(
                    c.getInt(c.getColumnIndex(ROW_TASK_ID)),
                    c.getString(c.getColumnIndex(ROW_TASK_NAME)),
                    c.getString(c.getColumnIndex(ROW_TASK_DESCRIPTION))
            );
            return task;
        } else {
            return null;
        }
    }

    public ArrayList<Task> getTasks() {
        ArrayList<Task> arrayList = new ArrayList<Task>();
        Cursor c = database.rawQuery("Select " + ROW_TASK_ID + ", " +
                ROW_TASK_NAME + ", " +
                ROW_TASK_DESCRIPTION +
                " from " + TABLE_TASK + " " +
                "order by " + ROW_TASK_ID, null);
        if (c.moveToFirst()) {
            do {

                arrayList.add(new Task(
                        c.getInt(c.getColumnIndex(ROW_TASK_ID)),
                        c.getString(c.getColumnIndex(ROW_TASK_NAME)),
                        c.getString(c.getColumnIndex(ROW_TASK_DESCRIPTION))
                ));
                c.moveToNext();
            } while (c.isAfterLast() == false);
        }
        return arrayList;
    }
// Конец операций с таблицей Task

    //Начало операции с таблицей персоны в мероприятии
    public long insertRowPersonInAction(long personIN, long actionId) {
        ContentValues cv = new ContentValues();
        cv.put(ROW_PERSON_IN_ACTION_ID_ACTION, actionId);
        cv.put(ROW_PERSON_IN_ACTION_ID_PERSON, personIN);
        cv.put(ROW_PERSON_IN_ACTION_RESULT, 0);
        return database.insert(TABLE_PERSON_IN_ACTION, null, cv);
    }

    public long insertRowPersonInAction(long personIN, long actionId,long result) {
        ContentValues cv = new ContentValues();
        cv.put(ROW_PERSON_IN_ACTION_ID_ACTION, actionId);
        cv.put(ROW_PERSON_IN_ACTION_ID_PERSON, personIN);
        cv.put(ROW_PERSON_IN_ACTION_RESULT, result);
        return database.insert(TABLE_PERSON_IN_ACTION, null, cv);
    }

    public void updateRowPersonInAction(long personIN, long actionId, long result) {
        ContentValues cv = new ContentValues();
        cv.put(ROW_PERSON_IN_ACTION_ID_ACTION, actionId);
        cv.put(ROW_PERSON_IN_ACTION_ID_PERSON, personIN);
        cv.put(ROW_PERSON_IN_ACTION_RESULT, result);
        database.update(TABLE_PERSON_IN_ACTION, cv,
                ROW_PERSON_IN_ACTION_ID_ACTION + " = " +Long.toString(actionId)
                        + " and "
                        + ROW_PERSON_IN_ACTION_ID_PERSON + " =  " + Long.toString(personIN)
                ,
                null);
    }

    public void deleteRowPersonInAction(long personId, long actionId) {
        database.delete(TABLE_PERSON_IN_ACTION,
                ROW_PERSON_IN_ACTION_ID_ACTION + " = " + actionId + " and " +
                        ROW_PERSON_IN_ACTION_ID_PERSON + " = " + personId, null
        );
    }

    public long getResult(long actionId, long personId){
        Cursor c = database.rawQuery("Select " + ROW_PERSON_IN_ACTION_RESULT + " from "
        + TABLE_PERSON_IN_ACTION +" where "+ ROW_PERSON_IN_ACTION_ID_PERSON + "=" +personId
        + " and " + ROW_PERSON_IN_ACTION_ID_ACTION +"="+ actionId,null);
        Long l;
        if (c.moveToFirst()){
            l = c.getLong(c.getColumnIndex(ROW_PERSON_IN_ACTION_RESULT));
        }else
            return 0;
        return l;
    }

    public long getTotalResult(long personId, long taskId){
        Cursor c = database.rawQuery("Select sum(" +ROW_PERSON_IN_ACTION_RESULT +")"+" as " +
                TOTAL_RESULT+" from "+ TABLE_PERSON_IN_ACTION +
                        " as PA inner join " + TABLE_ACTION +" as TA on TA."+ROW_ACTION_ID+
                " = " + ROW_PERSON_IN_ACTION_ID_ACTION +
                " where "+ ROW_PERSON_IN_ACTION_ID_PERSON + "=" +personId
                + " and " + ROW_ACTION_TASK_ID +"="+ taskId,null);
        Long l;
        if (c.moveToFirst()){
            l = c.getLong(c.getColumnIndex(TOTAL_RESULT));
        }else
            return 0;
        return l;
    }

    public long getTotalResultOfDate(java.sql.Date dateLast,
                                     java.sql.Date dateNew,
                                     long personId){
        Cursor c = database.rawQuery("Select sum(" +ROW_PERSON_IN_ACTION_RESULT +")"+" as " +
                TOTAL_RESULT+" from "+ TABLE_PERSON_IN_ACTION +
                " as PA inner join " + TABLE_ACTION +" as TA on TA."+ROW_ACTION_ID+
                " = " + ROW_PERSON_IN_ACTION_ID_ACTION +
                " where "+ ROW_PERSON_IN_ACTION_ID_PERSON + "=" +personId
                + " and " + ROW_ACTION_DATE + ">" + dateNew.getTime() + " and " +
                ROW_ACTION_DATE + "<" + dateLast.getTime(),null);
        Long l;
        if (c.moveToFirst()){
            l = c.getLong(c.getColumnIndex(TOTAL_RESULT));
        }else
            return 0;
        return l;
    }
    //наполняет мероприятие результами действий персон в нем.
    public Action getRowsPersonInAction(Action action) {
        long actionID = action.getId();
        Cursor c = database.rawQuery("Select " + ROW_PERSON_ID + ", " +
                ROW_PERSON_FIRST_NAME + ", " +
                ROW_PERSON_LAST_NAME + ", " +
                ROW_PERSON_SRC_IMG + ", " +
                ROW_PERSON_IN_ACTION_RESULT + ", " +
                ROW_PERSON_POST + " from " + TABLE_PERSON + " as TP " + " inner join " +
                TABLE_PERSON_IN_ACTION + " as PA" + " on " + "TP." + ROW_PERSON_ID + "=" +
                "PA." + ROW_PERSON_IN_ACTION_ID_PERSON + " where " +
                ROW_PERSON_IN_ACTION_ID_ACTION + "=" + actionID +
                //todo эта строка может вызвать ошибку!!!!!
                "  order by " + ROW_PERSON_IN_ACTION_ID_ACTION, null);
        if (c.moveToFirst()) {
            do {
                action.addPerson(
                        new Person(
                                c.getInt(c.getColumnIndex(ROW_PERSON_ID)),
                                c.getString(c.getColumnIndex(ROW_PERSON_FIRST_NAME)),
                                c.getString(c.getColumnIndex(ROW_PERSON_LAST_NAME)),
                                c.getString(c.getColumnIndex(ROW_PERSON_SRC_IMG)),
                                c.getString(c.getColumnIndex(ROW_PERSON_POST))),
                        //
                        c.getLong(c.getColumnIndex(ROW_PERSON_IN_ACTION_RESULT)));
                c.moveToNext();
            } while (c.isAfterLast() == false);
        } else {
            return action;
        }
        return action;
    }


    //Конец операции с таблицей персоны в мероприятии

    //начало операций с таблицей group
    public long insertNewGroup() {
        ContentValues cv = new ContentValues();
        cv.put(ROW_GROUP_NAME_GROUP, "");
        cv.put(ROW_GROUP_DESCRIPTION, "");
        return database.insert(TABLE_GROUP, null, cv);
    }

    public long insertGroup(Group group) {
        ContentValues cv = new ContentValues();
        cv.put(ROW_GROUP_NAME_GROUP, group.getName());
        cv.put(ROW_GROUP_DESCRIPTION, group.getDescription());
        return database.insert(TABLE_GROUP, null, cv);
    }

    public void updateGroup(long id, Group group) {
        ContentValues cv = new ContentValues();
        cv.put(ROW_TASK_ID, id);
        cv.put(ROW_TASK_NAME, group.getName());
        cv.put(ROW_ACTION_DESCRIPTION, group.getDescription());
        database.update(TABLE_TASK, cv, ROW_TASK_ID + " = ?",
                new String[]{Long.toString(id)});
    }

    public void deleteGroup(Long l) {
        database.delete(TABLE_GROUP, ROW_GROUP_ID + " = " + l, null);
    }

    public Group getGroup(long groupID) {
        Cursor c = database.query(TABLE_GROUP, null,
                ROW_TASK_ID + "=" + groupID,
                null, null, null, null);
        Group group;
        if (c.moveToFirst()) {
            group = new Group(
                    c.getInt(c.getColumnIndex(ROW_GROUP_ID)),
                    c.getString(c.getColumnIndex(ROW_GROUP_NAME_GROUP)),
                    c.getString(c.getColumnIndex(ROW_GROUP_DESCRIPTION))
            );
            return group;
        } else {
            return null;
        }
    }

    public ArrayList<Group> getGroups() {
        ArrayList<Group> arrayList = new ArrayList<Group>();
        Cursor c = database.rawQuery("Select " + ROW_GROUP_ID + ", " +
                ROW_GROUP_NAME_GROUP + ", " +
                ROW_GROUP_DESCRIPTION +
                " from " + TABLE_GROUP + " " +
                "order by " + ROW_GROUP_ID, null);
        if (c.moveToFirst()) {
            do {
                arrayList.add(new Group(
                        c.getInt(c.getColumnIndex(ROW_GROUP_ID)),
                        c.getString(c.getColumnIndex(ROW_GROUP_NAME_GROUP)),
                        c.getString(c.getColumnIndex(ROW_GROUP_DESCRIPTION))
                ));
                c.moveToNext();
            } while (c.isAfterLast() == false);
        }
        return arrayList;
    }


    public ArrayList<Person> getPersonsInGroup(Person person){
        ArrayList<Person> persons = new ArrayList<Person>();
        Cursor c = database.rawQuery("Select " + ROW_PERSON_ID + ", " +
                ROW_PERSON_FIRST_NAME + ", " +
                ROW_PERSON_LAST_NAME + ", " +
                ROW_PERSON_SRC_IMG + ", " +
                ROW_PERSON_POST + " from " + TABLE_PERSON + " as TP inner join " +
                TABLE_PERSON_IN_GROUP + " as TPIG on Tp." + ROW_PERSON_ID +
                "=TPIG."+ROW_PERSON_IN_GROUP_ID_GROUP+
                " where " + ROW_PERSON_IN_GROUP_ID_PERSON + "=" + person.getId() +
                "order by " + ROW_PERSON_ID, null);
        if (c.moveToFirst()) {
            do {
                persons.add(new Person(
                        c.getInt(c.getColumnIndex(ROW_PERSON_ID)),
                        c.getString(c.getColumnIndex(ROW_PERSON_FIRST_NAME)),
                        c.getString(c.getColumnIndex(ROW_PERSON_LAST_NAME)),
                        c.getString(c.getColumnIndex(ROW_PERSON_SRC_IMG)),
                        c.getString(c.getColumnIndex(ROW_PERSON_POST))
                ));
                c.moveToNext();
            } while (c.isAfterLast() == false);
        }
        return persons;
    }
    // конец операций с таблицей group

    //Начало операция с таблице person in group
    public long insertRowPersonInGroup(long personId, long groupId) {
        ContentValues cv = new ContentValues();
        cv.put(ROW_PERSON_IN_GROUP_ID_PERSON, personId);
        cv.put(ROW_PERSON_IN_GROUP_ID_GROUP, groupId);
        return database.insert(TABLE_PERSON_IN_GROUP, null, cv);
    }

    public void deleteRowPersonInGroup(long personId, long groupId) {
        database.delete(TABLE_PERSON_IN_GROUP,
                ROW_PERSON_IN_GROUP_ID_PERSON + " = " + personId + " and " +
                        ROW_PERSON_IN_GROUP_ID_GROUP + " = " + groupId, null
        );
    }

    public ArrayList<Person> getRowsPersonInGroup(Group group) {
        long groupID = group.getId();
        Cursor c = database.rawQuery("Select " + ROW_PERSON_ID + ", " +
                ROW_PERSON_FIRST_NAME + ", " +
                ROW_PERSON_LAST_NAME + ", " +
                ROW_PERSON_SRC_IMG + ", " +
                ROW_PERSON_POST + " from " + TABLE_PERSON + " as TP " + " inner join " +
                TABLE_PERSON_IN_GROUP + " as PG" + " on " + "TP." + ROW_PERSON_ID + "=" +
                "PG." + ROW_PERSON_IN_GROUP_ID_PERSON + " where " +
                ROW_PERSON_IN_GROUP_ID_GROUP + "=" + groupID, null);
        ArrayList<Person> arrayList = new ArrayList<Person>();
        if (c.moveToFirst()) {
            do {
                arrayList.add(new Person(
                        c.getInt(c.getColumnIndex(ROW_PERSON_ID)),
                        c.getString(c.getColumnIndex(ROW_PERSON_FIRST_NAME)),
                        c.getString(c.getColumnIndex(ROW_PERSON_LAST_NAME)),
                        c.getString(c.getColumnIndex(ROW_PERSON_SRC_IMG)),
                        c.getString(c.getColumnIndex(ROW_PERSON_POST))
                ));
                c.moveToNext();
            } while (c.isAfterLast() == false);
        } else {
            return null;
        }
        return arrayList;
    }

    //Конец операции с таблице person in group
    //основной отчет возращает операции выполненные одной персоной в одной задаче
    public ArrayList<Action> getReportPersonActionInTask(Person person, Task task) {
        long personID = person.getId();
        long taskID = task.getId();
        Cursor c = database.rawQuery("Select " + ROW_ACTION_ID + ", " +
                ROW_ACTION_NAME + ", " +
                ROW_ACTION_DESCRIPTION + ", " +
                ROW_ACTION_DATE + ", " +
                ROW_ACTION_TASK_ID + " from " + TABLE_ACTION + " as TA " +
                " inner join " + TABLE_PERSON_IN_ACTION + " as PA" +
                " on " + "TA." + ROW_ACTION_ID + "=" +
                "PA." + ROW_PERSON_IN_ACTION_ID_ACTION + " where " +
                ROW_PERSON_IN_ACTION_ID_PERSON + "=" + personID +
                " and " + ROW_ACTION_TASK_ID + "=" + taskID, null);
        ArrayList<Action> arrayList = new ArrayList<Action>();
        if (c.moveToFirst()) {
            do {
                arrayList.add(new Action(
                        c.getInt(c.getColumnIndex(ROW_ACTION_ID)),
                        c.getString(c.getColumnIndex(ROW_ACTION_NAME)),
                        new Date(c.getLong(c.getColumnIndex(ROW_ACTION_DATE))),
                        c.getString(c.getColumnIndex(ROW_ACTION_DESCRIPTION))
                ));
                c.moveToNext();
            } while (c.isAfterLast() == false);
        } else {
            return null;
        }
        return arrayList;
    }

    public ArrayList<Person> getReportTotalTask( Task task) {
        long taskID = task.getId();
        Cursor c = database.rawQuery("Select TP." + ROW_PERSON_ID + ", " +
                ROW_PERSON_FIRST_NAME + ", " +
                ROW_PERSON_LAST_NAME + ", " +
                ROW_PERSON_POST + ", " +
                ROW_PERSON_SRC_IMG + " from " + TABLE_ACTION + " as TA " +
                " inner join " + TABLE_PERSON_IN_ACTION + " as PA" +
                " on " + "TA." + ROW_ACTION_ID + "=" +
                "PA." + ROW_PERSON_IN_ACTION_ID_ACTION +
                " inner join " + TABLE_PERSON + " as TP " + " on " +
                "TP." + ROW_PERSON_ID + "=" + "PA." + ROW_PERSON_IN_ACTION_ID_PERSON +
                " where " +
                ROW_ACTION_TASK_ID + "=" + taskID, null);
        ArrayList<Person> arrayList = new ArrayList<Person>();
        if (c.moveToFirst()) {
            do {
                arrayList.add(new Person(
                        c.getInt(c.getColumnIndex(ROW_PERSON_ID)),
                        c.getString(c.getColumnIndex(ROW_PERSON_FIRST_NAME)),
                        c.getString(c.getColumnIndex(ROW_PERSON_LAST_NAME)),
                        c.getString(c.getColumnIndex(ROW_PERSON_SRC_IMG)),
                        c.getString(c.getColumnIndex(ROW_PERSON_POST))
                ));
                c.moveToNext();
            } while (c.isAfterLast() == false);
        } else {
            return null;
        }
        return arrayList;
    }

    public ArrayList<Person> getTotalReportOfTime(java.sql.Date dateLast,
                                                  java.sql.Date dateNew){
        ArrayList<Person> persons = new ArrayList<Person>();
        Cursor c = database.rawQuery("Select TP." + ROW_PERSON_ID + ", " +
                ROW_PERSON_FIRST_NAME + ", " +
                ROW_PERSON_LAST_NAME + ", " +
                ROW_PERSON_POST + ", " +
                ROW_PERSON_SRC_IMG + " from " + TABLE_ACTION + " as TA " +
                " inner join " + TABLE_PERSON_IN_ACTION + " as PA" +
                " on " + "TA." + ROW_ACTION_ID + "=" +
                "PA." + ROW_PERSON_IN_ACTION_ID_ACTION +
                " inner join " + TABLE_PERSON + " as TP " + " on " +
                "TP." + ROW_PERSON_ID + "=" + "PA." + ROW_PERSON_IN_ACTION_ID_PERSON +
                " where " + ROW_ACTION_DATE + ">" + dateNew.getTime() + " and " +
                ROW_ACTION_DATE + "<" + dateLast.getTime(), null);
        if (c.moveToFirst()) {
            do {
                persons.add(new Person(
                        c.getInt(c.getColumnIndex(ROW_PERSON_ID)),
                        c.getString(c.getColumnIndex(ROW_PERSON_FIRST_NAME)),
                        c.getString(c.getColumnIndex(ROW_PERSON_LAST_NAME)),
                        c.getString(c.getColumnIndex(ROW_PERSON_SRC_IMG)),
                        c.getString(c.getColumnIndex(ROW_PERSON_POST))
                ));
                c.moveToNext();
            } while (c.isAfterLast() == false);
        } else {
            return null;
        }

        return persons;

    }


    public void generateDataBase() {

        database.delete(TABLE_ACTION, "", null);
        database.delete(TABLE_PERSON, "", null);
        database.delete(TABLE_CONTACT, "", null);
        database.delete(TABLE_TASK, "", null);
        database.delete(TABLE_PERSON_IN_ACTION, "", null);
        database.delete(TABLE_GROUP, "", null);
        database.delete(TABLE_PERSON_IN_GROUP, "", null);


        long person1 = insertPerson(new Person("Иван", "Иванов", "", "Участник"));
        long person2 = insertPerson(new Person("Петр", "Пертров", "", "Участник"));
        long person3 = insertPerson(new Person("Владимир", "Сидоров", "", "Участник"));
        long person4 = insertPerson(new Person("Игорь", "Васнецов", "", "Участник"));
        long person5 = insertPerson(new Person("Александр", "Пушкин", "", "Участник"));
        long person6 = insertPerson(new Person("Константин", "Новожилов", "", "Участник"));

        Long task = insertTask(new Task("Хозяственная деятельность", ""));

        insertContact(new Contact(person1,  "123450000"));
        insertContact(new Contact(person1,  "234560000"));

        insertContact(new Contact(person2, "901230000"));
        insertContact(new Contact(person2,  "012340000"));
        insertContact(new Contact(person2,  "134560000"));
        insertContact(new Contact(person2,  "245670000"));

        insertContact(new Contact(person3,  "356780000"));
        insertContact(new Contact(person3,  "467890000"));
        insertContact(new Contact(person3,  "578900000"));
        insertContact(new Contact(person3,  "689010000"));

        insertContact(new Contact(person4, "123450000"));
        insertContact(new Contact(person4, "234560000"));
        insertContact(new Contact(person4, "345670000"));
        insertContact(new Contact(person4, "456780000"));

        insertContact(new Contact(person5, "901230000"));
        insertContact(new Contact(person5, "012340000"));
        insertContact(new Contact(person5, "134560000"));
        insertContact(new Contact(person5, "245670000"));

        insertContact(new Contact(person6, "356780000"));
        insertContact(new Contact(person6, "467890000"));
        insertContact(new Contact(person6, "578900000"));
        insertContact(new Contact(person6, "689010000"));

        Long action1 = insertAction(
                new Action("Инвентаризация", new Date(), ""), task);
        Long action2 = insertAction(
                new Action("Получить товары от заказчика", new Date(), ""), task);
        Long action3 = insertAction(
                new Action("Встеча с ОАО Азот", new Date(), ""), task);

        insertRowPersonInAction(person1, action1,10L);
        insertRowPersonInAction(person2, action1,10L);
        insertRowPersonInAction(person3, action1,10L);
        insertRowPersonInAction(person4, action1,10L);
        insertRowPersonInAction(person5, action1,10L);
        insertRowPersonInAction(person6, action1,10L);

        insertRowPersonInAction(person1, action2);
        insertRowPersonInAction(person2, action2);
        insertRowPersonInAction(person3, action2);

        insertRowPersonInAction(person4, action3);
        insertRowPersonInAction(person5, action3);
        insertRowPersonInAction(person6, action3);

        Long group = insertGroup(new Group("Рабочая группа", ""));

        insertRowPersonInGroup(person1, group);
        insertRowPersonInGroup(person2, group);
        insertRowPersonInGroup(person3, group);
        insertRowPersonInGroup(person4, group);
        insertRowPersonInGroup(person5, group);
        insertRowPersonInGroup(person6, group);

    }

    public static DataBase get(Context newContext) {
        if (sDataBase == null)
            sDataBase = new DataBase(newContext);
        return sDataBase;
    }

    public ArrayList<Action> getReportPersonActionInDate(Person person,
                                                         java.sql.Date dateLast,
                                                         java.sql.Date dateNew) {
        ArrayList<Action> actions = new ArrayList<Action>();
        long personID = person.getId();
        Cursor c = database.rawQuery("Select " + ROW_ACTION_ID + ", " +
                ROW_ACTION_NAME + ", " +
                ROW_ACTION_DESCRIPTION + ", " +
                ROW_ACTION_DATE + ", " +
                ROW_ACTION_TASK_ID + " from " + TABLE_ACTION + " as TA " +
                " inner join " + TABLE_PERSON_IN_ACTION + " as PA" +
                " on " + "TA." + ROW_ACTION_ID + "=" +
                "PA." + ROW_PERSON_IN_ACTION_ID_ACTION + " where " +
                ROW_PERSON_IN_ACTION_ID_PERSON + "=" + personID +
                " and " + ROW_ACTION_DATE + ">" + dateNew.getTime() + " and " +
                ROW_ACTION_DATE + "<" + dateLast.getTime(), null);
        ArrayList<Action> arrayList = new ArrayList<Action>();
        if (c.moveToFirst()) {
            do {
                arrayList.add(new Action(
                        c.getInt(c.getColumnIndex(ROW_ACTION_ID)),
                        c.getString(c.getColumnIndex(ROW_ACTION_NAME)),
                        new Date(c.getLong(c.getColumnIndex(ROW_ACTION_DATE))),
                        c.getString(c.getColumnIndex(ROW_ACTION_DESCRIPTION))
                ));
                c.moveToNext();
            } while (c.isAfterLast() == false);
        } else {
            return null;
        }
        return arrayList;
    }

    private class myDataBaseHelper extends SQLiteOpenHelper {

        private static final String TAG = "DataBase.myDataBaseHelper";
        private static final String DATA_BASE_NAME = "PersonManagersDataBase";
        private static final int DATA_BASE_VERSION = 1;


        public myDataBaseHelper(Context context) {
            super(context, DATA_BASE_NAME, null, DATA_BASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            createTable(db);

        }

        private void createTable(SQLiteDatabase db) {
            db.execSQL("create table " + TABLE_PERSON + "("
                    + ROW_PERSON_ID + " integer primary key autoincrement, "
                    + ROW_PERSON_FIRST_NAME + " text, "
                    + ROW_PERSON_LAST_NAME + " text, "
                    + ROW_PERSON_SRC_IMG + " text, "
                    + ROW_PERSON_POST + " text" + ");");

            db.execSQL("create table " + TABLE_CONTACT + "("
                    + ROW_CONTACT_ID + " integer primary key autoincrement, "
                    + ROW_CONTACT_ID_PERSON + " integer, "
                    + ROW_CONTACT_DESCRIPTION + " text" + ");");

            db.execSQL("create table " + TABLE_TASK + "("
                    + ROW_TASK_ID + " integer primary key autoincrement, "
                    + ROW_TASK_NAME + " text, "
                    + ROW_TASK_DESCRIPTION + " text" + ");");

            db.execSQL("create table " + TABLE_ACTION + "("
                    + ROW_ACTION_ID + " integer primary key autoincrement, "
                    + ROW_ACTION_NAME + " text, "
                    + ROW_ACTION_DESCRIPTION + " text, "
                    + ROW_ACTION_DATE + " integer, "
                    + ROW_ACTION_TASK_ID + " integer" + ");");

            db.execSQL("create table " + TABLE_PERSON_IN_ACTION + "("
                    + ROW_PERSON_IN_ACTION_ID_PERSON + " integer , "
                    + ROW_PERSON_IN_ACTION_ID_ACTION + " integer, "
                    + ROW_PERSON_IN_ACTION_RESULT + " integer " + ");");

            db.execSQL("create table " + TABLE_PERSON_IN_GROUP + "("
                    + ROW_PERSON_IN_GROUP_ID_PERSON + " integer, "
                    + ROW_PERSON_IN_GROUP_ID_GROUP + " integer" + ");");

            db.execSQL("create table " + TABLE_GROUP + "("
                    + ROW_GROUP_ID + " integer primary key autoincrement, "
                    + ROW_GROUP_DESCRIPTION + " text, "
                    + ROW_GROUP_NAME_GROUP + " text" + ");");

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }

}
