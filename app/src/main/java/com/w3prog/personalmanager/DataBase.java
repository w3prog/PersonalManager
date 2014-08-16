package com.w3prog.personalmanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class DataBase {

    private static final String TAG = "DataBase.class";
    //Таблица PERSON
    private static final String TABLE_PERSON = "person";
    private static final String ROW_PERSON_ID = "id";
    private static final String ROW_PERSON_FIRSTNAME = "firstName";
    private static final String ROW_PERSON_LASTNAME = "lastName";
    private static final String ROW_PERSON_SRC_IMG = "src_img";
    private static final String ROW_PERSON_POST = "post";
    //Таблица CONTACT
    private static final String TABLE_CONTACT = "contact";
    private static final String ROW_CONTACT_ID = "id";
    private static final String ROW_CONTACT_ID_PERSON = "id_person";
    private static final String ROW_CONTACT_TYPE = "type";
    private static final String ROW_CONTACT_DESCRIPTION = "description";
    //Таблица GROUP
    private static final String TABLE_GROUP = "groupContact";
    private static final String ROW_GROUP_ID = "id";
    private static final String ROW_GROUP_NAMEGROUP = "NameGroup";
    //Таблица TASK
    private static final String TABLE_TASK = "task";
    private static final String ROW_TASK_ID = "id";
    private static final String ROW_TASK_NAME = "NameTask";
    //Таблица ACTION
    private static final String TABLE_ACTION = "action";
    private static final String ROW_ACTION_ID = "id";
    private static final String ROW_ACTION_NAME = "NameAction";
    private static final String ROW_ACTION_DATE = "Date";
    private static final String ROW_ACTION_DESCRIPTION = "Description";
    //Таблица PERSON_IN_ACTION
    private static final String TABLE_PERSON_IN_ACTION = "person_in_action";
    private static final String ROW_PERSON_IN_ACTION_ID_PERSION = "idPersion";
    private static final String ROW_PERSON_IN_ACTION_ID_ACTION = "idAction";
    //Таблица ACTION_IN_TASK
    private static final String TABLE_ACTION_IN_TASK = "ActionINTask";
    private static final String ROW_ACTION_IN_TASK_ID_ACTION = "idAction";
    private static final String ROW_ACTION_IN_TASK_ID_TASK = "idTask";
    //Таблица PERSON_IN_GROUP
    private static final String TABLE_PERSON_IN_GROUP = "PersonInGroup";
    private static final String ROW_PERSON_IN_GROUP_ID_PERSON = "idPerson";
    private static final String ROW_PERSON_IN_GROUP_ID_GROUP = "idGroup";

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
    public long insertPerson(Person p){
        ContentValues cv = new ContentValues();
        cv.put(ROW_PERSON_FIRSTNAME, p.getFirstName());
        cv.put(ROW_PERSON_LASTNAME, p.getLastName() );
        cv.put(ROW_PERSON_SRC_IMG, p.getStrImg());
        cv.put(ROW_PERSON_POST, p.getPost());
        Log.d(TAG,"Строка " + p.getFirstName() + " " + p.getLastName() + " прошла");
        return database.insert(TABLE_PERSON, null, cv);
    }

    public void updatePerson( int id, Person p ){
        ContentValues cv = new ContentValues();

        cv.put(ROW_PERSON_ID, p.getId());
        cv.put(ROW_PERSON_FIRSTNAME, p.getFirstName());
        cv.put(ROW_PERSON_LASTNAME, p.getLastName());
        cv.put(ROW_PERSON_SRC_IMG, p.getStrImg());
        cv.put(ROW_PERSON_POST, p.getPost());
        Log.d(TAG,"Выполнение операций updatePerson id= " +id);
        int updCount = database.update(TABLE_PERSON, cv, ROW_PERSON_ID + " = ?",
                new String[] { Integer.toString(id) });

    }

    public void deletePerson(int id){
        Log.d(TAG,"Выполнение операций deletePerson для строки с id равным " + id);
        database.delete(TABLE_PERSON, ROW_PERSON_ID + " = " + id, null);
    }

    public Person getPerson(long id){
        Cursor c = database.query(TABLE_PERSON,
                null,
                ROW_PERSON_ID+"="+id,
                null,null,null,null);
        Person person;
        Log.d(TAG, "Работа с курсором id = " + id);

        if (c.moveToFirst()){
            person = new Person(
                    c.getInt(c.getColumnIndex(ROW_PERSON_ID)),
                    c.getString(c.getColumnIndex(ROW_PERSON_FIRSTNAME)),
                    c.getString(c.getColumnIndex(ROW_PERSON_LASTNAME)),
                    c.getString(c.getColumnIndex(ROW_PERSON_SRC_IMG)),
                    c.getString(c.getColumnIndex(ROW_PERSON_POST))
            );
            Log.d(TAG,"Работа с курсором завершена");
            return person;
        } else {
            Log.d(TAG,"Курсор пустой");
            return null;
        }
    }

    public ArrayList<Person> getPersons(){
        ArrayList<Person>  arrayList = new ArrayList<Person>();
        Cursor c = database.rawQuery("Select "+ROW_PERSON_ID+", " +
                ROW_PERSON_FIRSTNAME+", " +
                ROW_PERSON_LASTNAME+", " +
                ROW_PERSON_SRC_IMG+", " +
                ROW_PERSON_POST+" from " + TABLE_PERSON + " "+
                "order by " + ROW_PERSON_ID,null);
        if (c.moveToFirst()){
            do {
                arrayList.add( new Person(
                        c.getInt(c.getColumnIndex(ROW_PERSON_ID)),
                        c.getString(c.getColumnIndex(ROW_PERSON_FIRSTNAME)),
                        c.getString(c.getColumnIndex(ROW_PERSON_LASTNAME)),
                        c.getString(c.getColumnIndex(ROW_PERSON_SRC_IMG)),
                        c.getString(c.getColumnIndex(ROW_PERSON_POST))
                ));
                c.moveToNext();
            } while (c.isAfterLast() == false);
            Log.d(TAG,"Работа с курсором завершена getPersons");
        } else {
            Log.d(TAG,"Курсор c группами событий getPersons пустой");
        }
        return arrayList;
    }
//Конец операций с таблицей Person

//Операции с таблицей Contacts
    public long insertContact(Contact c){
        ContentValues cv = new ContentValues();
        cv.put(ROW_CONTACT_ID_PERSON, c.getIdPerson());
        cv.put(ROW_CONTACT_TYPE, c.getType() );
        cv.put(ROW_CONTACT_DESCRIPTION, c.getDescription());
        return database.insert(TABLE_CONTACT, null, cv);
    }

    public void updateContact(int id,Contact c){
        ContentValues cv = new ContentValues();

        cv.put(ROW_CONTACT_ID, c.getIdContact());
        cv.put(ROW_CONTACT_ID_PERSON, c.getIdPerson());
        cv.put(ROW_CONTACT_TYPE, c.getType());
        cv.put(ROW_CONTACT_DESCRIPTION, c.getDescription());
        Log.d(TAG, "Выполнение операций updateContact id= " + id);
        int updCount = database.update(TABLE_CONTACT, cv, ROW_CONTACT_ID + " = ?",
                new String[] { Integer.toString(id) });
    }

    public void deleteContact(int id){
        Log.d(TAG,"Выполнение операций deleteContact для строки с id равным " + id);
        database.delete(TABLE_CONTACT, ROW_CONTACT_ID + " = " + id, null);
    }

    public Contact getContact(int id){

        Cursor c = database.query(TABLE_CONTACT,
                null,
                ROW_CONTACT_ID+"="+id,
                null,null,null,null);
        Contact contact;
        Log.d(TAG, "Работа с курсором getContact id = " + id);

        if (c.moveToFirst()){
            contact = new Contact(
                    c.getInt(c.getColumnIndex(ROW_CONTACT_ID)),
                    c.getInt(c.getColumnIndex(ROW_CONTACT_ID_PERSON)),
                    c.getString(c.getColumnIndex(ROW_CONTACT_TYPE)),
                    c.getString(c.getColumnIndex(ROW_CONTACT_DESCRIPTION))
            );
            Log.d(TAG,"Работа с курсором завершена");
            return contact;
        } else {
            Log.d(TAG,"Курсор пустой");
            return null;
        }
    }

    public  ArrayList<Contact> getContacts(){
        ArrayList<Contact>  arrayList = new ArrayList<Contact>();
        Cursor c = database.rawQuery("Select "+ROW_CONTACT_ID+", " +
                ROW_CONTACT_ID_PERSON+", " +
                ROW_CONTACT_TYPE+", " +
                ROW_CONTACT_DESCRIPTION+" from " + TABLE_CONTACT + " "+
                "order by " + ROW_CONTACT_ID,null);
        if (c.moveToFirst()){
            do {
                arrayList.add( new Contact(
                        c.getInt(c.getColumnIndex(ROW_CONTACT_ID)),
                        c.getInt(c.getColumnIndex(ROW_CONTACT_ID_PERSON)),
                        c.getString(c.getColumnIndex(ROW_CONTACT_TYPE)),
                        c.getString(c.getColumnIndex(ROW_CONTACT_DESCRIPTION))
                ));
                c.moveToNext();
            } while (c.isAfterLast() == false);
            Log.d(TAG,"Работа с курсором завершена getContacts");
        } else {
            Log.d(TAG,"Курсор c группами событий getContacts пустой");
        }
        return arrayList;
    }

    public ArrayList<Contact> getPhoneContactPerson(Person person){
        ArrayList<Contact>  arrayList = new ArrayList<Contact>();
        /*Cursor c = database.rawQuery("Select "+ROW_CONTACT_ID+", " +
                ROW_CONTACT_ID_PERSON+", " +
                ROW_CONTACT_TYPE+", " +
                ROW_CONTACT_DESCRIPTION+" from " + TABLE_CONTACT + " "+
                "where " + ROW_CONTACT_ID_PERSON + person.getId()+
                    //    " and "+ROW_CONTACT_TYPE +" = Phone "+
                "order by " + ROW_CONTACT_ID,null);*/
        Cursor c = database.query(TABLE_CONTACT,
                null,
                ROW_CONTACT_ID_PERSON+"="+person.getId(),
                null,null,null,null);

        if (c.moveToFirst()){
            do {
                arrayList.add( new Contact(
                        c.getInt(c.getColumnIndex(ROW_CONTACT_ID)),
                        c.getInt(c.getColumnIndex(ROW_CONTACT_ID_PERSON)),
                        c.getString(c.getColumnIndex(ROW_CONTACT_TYPE)),
                        c.getString(c.getColumnIndex(ROW_CONTACT_DESCRIPTION))
                ));
                c.moveToNext();
            } while (c.isAfterLast() == false);
            Log.d(TAG,"Работа с курсором завершена getContacts");
        } else {
            Log.d(TAG,"Курсор c группами событий getContacts пустой");
        }
        return arrayList;
    }
//Окончание операций с таблицей Contacts

// начало операций с таблицей Action
    public long insertAction(Action c){
        ContentValues cv = new ContentValues();
        cv.put(ROW_ACTION_NAME, c.getName());
        cv.put(ROW_ACTION_DATE, c.getDate().toString() );
        cv.put(ROW_ACTION_DESCRIPTION,c.getDescription());
        return database.insert(TABLE_ACTION, null, cv);
    }

    public void updateAction(int id,Action c){
        ContentValues cv = new ContentValues();

        cv.put(ROW_ACTION_ID, id);
        cv.put(ROW_ACTION_NAME, c.getName());
        cv.put(ROW_ACTION_DATE, c.getDate().toString() );
        cv.put(ROW_ACTION_DESCRIPTION,c.getDescription());
        Log.d(TAG, "Выполнение операций updateContact id= " + id);
        int updCount = database.update(TABLE_ACTION, cv, ROW_ACTION_ID + " = ?",
                new String[] { Integer.toString(id) });
    }

    public void deleteAction(int id){
        Log.d(TAG,"Выполнение операций deleteContact для строки с id равным " + id);
        database.delete(TABLE_ACTION, ROW_ACTION_ID + " = " + id, null);
    }

    public Action getAction(long id){

        Cursor c = database.query(TABLE_ACTION,
                null,
                ROW_ACTION_ID+"="+id,
                null,null,null,null);
        Action action;
        if (c.moveToFirst()){
            //todo Выписывает исключение не очень хорошо

            Date newDate = null;
            try {
                newDate = new SimpleDateFormat()
                        .parse(c.getString(c.getColumnIndex(ROW_ACTION_DATE)));
            } catch (ParseException e) {
                Log.d(TAG,"Ошибка Парсинга данных");
                e.printStackTrace();
            }
            if( newDate == null ) newDate =new Date();

            action = new Action(
                    c.getInt(c.getColumnIndex(ROW_ACTION_ID)),
                    c.getString(c.getColumnIndex(ROW_ACTION_NAME)),
                    newDate,
                    c.getString(c.getColumnIndex(ROW_ACTION_DESCRIPTION))
            );
            Log.d(TAG,"Работа с курсором завершена");
            return action;
        } else {
            Log.e(TAG,"Курсор пустой");
            return null;
        }
    }

    public  ArrayList<Action> getActions(){
        ArrayList<Action>  arrayList = new ArrayList<Action>();
        Cursor c = database.rawQuery("Select "+ROW_ACTION_ID+", " +
                ROW_ACTION_NAME+", " +
                ROW_ACTION_DATE+", " +
                ROW_ACTION_DESCRIPTION+" from " + TABLE_ACTION + " "+
                "order by " + ROW_ACTION_ID,null);
        if (c.moveToFirst()){
            do {

                Date newDate = null;
                try {
                    newDate = new SimpleDateFormat()
                            .parse(c.getString(c.getColumnIndex(ROW_ACTION_DATE)));
                } catch (ParseException e) {
                    Log.d(TAG,"Ошибка Парсинга данных");
                    e.printStackTrace();
                }
                if( newDate == null ) newDate =new Date();

                arrayList.add( new Action(
                        c.getInt(c.getColumnIndex(ROW_ACTION_ID)),
                        c.getString(c.getColumnIndex(ROW_ACTION_NAME)),
                        newDate,
                        c.getString(c.getColumnIndex(ROW_ACTION_DESCRIPTION))
                ));
                c.moveToNext();
            } while (c.isAfterLast() == false);
            Log.d(TAG,"Работа с курсором завершена getActions");
        } else {
            Log.d(TAG,"Курсор c группами событий getActions пустой");
        }
        return arrayList;
    }

    public ArrayList<Action> getActionsOfContacts(Contact contact){
        //Придется добавить еще одну таблицу
        return null;
    }

    public ArrayList<Action> getActionofTask(Contact contact,Task task){
        //Придется добавить сложный запрос к базе данных
        return null;
    }
// конец операция с таблицей Actions

    public void generateDataBase(){

        database.delete(TABLE_ACTION, "", null);
        database.delete(TABLE_PERSON, "", null);
        database.delete(TABLE_CONTACT, "", null);


        long person1 = insertPerson(new Person("Иван","Иванов","","Участник"));
        long person2 = insertPerson(new Person("Петр","Пертров","","Участник"));
        long person3 = insertPerson(new Person("Владимир","Сидоров","","Участник"));
        long person4 = insertPerson(new Person("Игорь","Васнецов","","Участник"));
        long person5 = insertPerson(new Person("Александр","Пушкин","","Участник"));
        long person6 = insertPerson(new Person("Константин","Новожилов","","Участник"));

        insertContact(new Contact(person1,"Phone","12345"));
        insertContact(new Contact(person1,"Email","23456"));
        insertContact(new Contact(person1,"WebSite","www.vk.com/person"));
        insertContact(new Contact(person1,"WebSite","www.mail.com/person"));

        insertContact(new Contact(person2,"Phone","90123"));
        insertContact(new Contact(person2,"Phone","01234"));
        insertContact(new Contact(person2,"Phone","13456"));
        insertContact(new Contact(person2,"Phone","24567"));

        insertContact(new Contact(person3,"Phone","35678"));
        insertContact(new Contact(person3,"Phone","46789"));
        insertContact(new Contact(person3,"Phone","57890"));
        insertContact(new Contact(person3,"Phone","68901"));

        insertContact(new Contact(person4,"Phone","12345"));
        insertContact(new Contact(person4,"Phone","23456"));
        insertContact(new Contact(person4,"Phone","34567"));
        insertContact(new Contact(person4,"Phone","45678"));

        insertContact(new Contact(person5,"Phone","90123"));
        insertContact(new Contact(person5,"Phone","01234"));
        insertContact(new Contact(person5,"Phone","13456"));
        insertContact(new Contact(person5,"Phone","24567"));

        insertContact(new Contact(person6,"Phone","35678"));
        insertContact(new Contact(person6,"Phone","46789"));
        insertContact(new Contact(person6,"Phone","57890"));
        insertContact(new Contact(person6,"Phone","68901"));

        insertAction(new Action("Инвентаризация", new Date(),""));
        insertAction(new Action("Получить товары от заказчика", new Date(),""));
        insertAction(new Action("Встеча с ОАО Азот", new Date(),""));
    }

    public static DataBase Get(Context newContext){
        if (sDataBase == null)
            sDataBase = new DataBase(newContext);
        return sDataBase;
    }

    private class myDataBaseHelper extends SQLiteOpenHelper{

        private static final String TAG = "DataBase.myDataBaseHelper";
        private static final String DATA_BASE_NAME = "PrilName";
        private static final int DATA_BASE_VERSION = 1;


        public myDataBaseHelper(Context context) {
            super(context, DATA_BASE_NAME, null, DATA_BASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            createTable(db);

        }

        private void createTable(SQLiteDatabase db){
            db.execSQL("create table "+TABLE_PERSON+ "("
                    +ROW_PERSON_ID+" integer primary key autoincrement, "
                    +ROW_PERSON_FIRSTNAME+" text, "
                    +ROW_PERSON_LASTNAME+" text, "
                    +ROW_PERSON_SRC_IMG+" text, "
                    +ROW_PERSON_POST+" text" +");");



            db.execSQL("create table "+TABLE_CONTACT+ "("
                    +ROW_CONTACT_ID+" integer primary key autoincrement, "
                    +ROW_CONTACT_ID_PERSON+" integer, "
                    +ROW_CONTACT_TYPE+" text, "
                    +ROW_CONTACT_DESCRIPTION+" text" +");");

            db.execSQL("create table "+TABLE_GROUP+ "("
                    +ROW_GROUP_ID+" integer primary key autoincrement, "
                    +ROW_GROUP_NAMEGROUP+" text" +");");

            db.execSQL("create table "+TABLE_TASK+ "("
                    +ROW_TASK_ID+" integer primary key autoincrement, "
                    +ROW_TASK_NAME+" text" +");");

            db.execSQL("create table "+TABLE_ACTION+ "("
                    +ROW_ACTION_ID+" integer primary key autoincrement, "
                    +ROW_ACTION_NAME+" text, "
                    +ROW_ACTION_DESCRIPTION+" text, "
                    +ROW_ACTION_DATE+" text" +");");

            //Todo Узнать про реализацию связи многие ко многим в SQLite
/*
            db.execSQL("create table "+TABLE_PERSON_IN_ACTION+ "("
                    +ROW_PERSON_IN_ACTION_ID_PERSION+" integer primary key, "
                    +ROW_PERSON_IN_ACTION_ID_ACTION+" integer primary key" +");");

            db.execSQL("create table "+TABLE_ACTION_IN_TASK+ "("
                    +ROW_ACTION_IN_TASK_ID_ACTION+" integer primary key, "
                    +ROW_ACTION_IN_TASK_ID_TASK+" integer primary key" +");");

            db.execSQL("create table "+TABLE_PERSON_IN_GROUP+ "("
                    +ROW_PERSON_IN_GROUP_ID_PERSON+" integer primary key, "
                    +ROW_PERSON_IN_GROUP_ID_GROUP+" integer primary key" +");");
*/

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }

}
