package com.w3prog.personalmanager;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class PersonUtil {

    // возращает дату в текстовом формате
    public static String writeDate(Date date){
        Calendar calendar =Calendar.getInstance();
        calendar.setTime(date);
        int year = calendar.get(calendar.YEAR);
        String month = toMinth(calendar.get(calendar.MONTH));
        int days = calendar.get(calendar.DAY_OF_MONTH);

        return "" +days + " " + month + " " + year;

    }

    // возвращат время в текстовом формате
    public static String writeTime(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int Hour = calendar.get(calendar.HOUR_OF_DAY);
        int minute = calendar.get(calendar.MINUTE);
        return "" + Hour + " : " + minute;
    }

    // возращает текстовое представления месяца
    private static String toMinth(int s) {

        switch (s){
            case 0:
                return "Января";
            case 1:
                return "Февраля";
            case 2:
                return "Марта";
            case 3:
                return "Апреля";
            case 4:
                return "Мая";
            case 5:
                return "Июня";
            case 6:
                return "Июля";
            case 7:
                return "Августа";
            case 8:
                return "Сентября";
            case 9:
                return "Октября";
            case 10:
                return "Ноября";
            case 11:
                return "Декабря";
            default:
                return null;
        }
    }

    // выводит достижения персоны между определенными датами
    public ArrayList<Action> оценитьперсону(Person person,Date startDate,Date endDate){
        ArrayList<Action> Actions =null;
        //Получить мероприятия текущей персоны запрос к базе данных


        //Data
        return Actions;
    }
}
