package com.example.room.database;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class TestData {
    public static SimpleDateFormat formatter= new SimpleDateFormat("dd/mm/yyyy", Locale.getDefault());
    static List<PersonneEntity> listPersons;
    static {
        listPersons = new ArrayList<PersonneEntity>();
        try {
            listPersons.add(new PersonneEntity(formatter.parse("10/02/2021"),"nom1"));
            listPersons.add(new PersonneEntity( formatter.parse("10/02/2021"),"nom2"));
            listPersons.add(new PersonneEntity(formatter.parse("10/02/2021"),"nom3"));
            listPersons.add(new PersonneEntity(formatter.parse("10/02/2021"),"nom4"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
    public static List<PersonneEntity> getAll() {
        return listPersons;
    }
}
