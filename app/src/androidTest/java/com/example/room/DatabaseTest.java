package com.example.room;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.room.Room;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.example.room.database.AppDatabase;
import com.example.room.database.PersonneDao;
import com.example.room.database.PersonneEntity;
import com.example.room.database.TestData;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.text.ParseException;
import java.util.List;

@RunWith(AndroidJUnit4.class)
public class DatabaseTest {
    private AppDatabase database;
    private PersonneDao dao;
    private PersonneEntity personneEntity;

    // chaque test
    @Before
    public void createDb() {
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        database = Room.inMemoryDatabaseBuilder(appContext, AppDatabase.class).build();
        dao = database.personneDao();
    }

    // chaque test
    @After
    public void closeDb() {
        database.close();
    }

    @Test
    public void insertAll() {
        dao.insertAll(TestData.getAll());
        int count = dao.getCount();
        Assert.assertEquals(4, count);
    }

    @Test
    public void insertPersonne() throws ParseException {
        try {
            personneEntity = new PersonneEntity(10, TestData.formatter.parse("01/10/1981"), "PHAM");
        } catch (ParseException e) {
            throw e;
        }

        dao.insertPersonne(personneEntity);

        PersonneEntity person = dao.getPersonneById(10);
        Assert.assertEquals("PHAM", person.getNom());
        Assert.assertEquals("01/10/1981", TestData.formatter.format(person.getDate()));
    }

    @Test
    public void insertPersonneWithoutId() throws ParseException {
        try {
            personneEntity = new PersonneEntity(TestData.formatter.parse("01/10/1981"), "PHAM");
        } catch (ParseException e) {
            throw e;
        }

        dao.insertPersonne(personneEntity);

        PersonneEntity person = dao.getPersonneById(1);
        Assert.assertEquals("PHAM", person.getNom());
        Assert.assertEquals("01/10/1981", TestData.formatter.format(person.getDate()));

    }

    @Test
    public  void deletePersonne() throws ParseException {
        try {
            personneEntity = new PersonneEntity(10, TestData.formatter.parse("01/10/1981"), "PHAM");
        } catch (ParseException e) {
            throw e;
        }

        dao.insertPersonne(personneEntity);
        PersonneEntity person = dao.getPersonneById(10);
        Assert.assertEquals("PHAM", person.getNom());

        dao.deletePersonne(personneEntity);
        Assert.assertEquals(0, dao.getCount());

    }

    @Test
    public void getPersonneById() {
        dao.insertAll(TestData.getAll());
        PersonneEntity person = dao.getPersonneById(3);
        Assert.assertEquals("nom3", person.getNom());
    }

//    @Test
//    public void getAllLiveData() {
//        dao.insertAll(TestData.getAll());
//        LiveData<List<PersonneEntity>> allPersonsLiveData = dao.getAllLiveData();
//        Assert.assertEquals(4, allPersonsLiveData.getValue().size());
//    }

//    @Test
//    public void getAll() {
//        dao.insertAll(TestData.getAll());
//        List<PersonneEntity> allPersons = dao.getAll();
//        Assert.assertEquals(4, allPersons.size());
//    }

    @Test
    public void deleteAll() {
        dao.insertAll(TestData.getAll());
        int countBefore = dao.getCount();
        Assert.assertEquals(4, countBefore);
        dao.deleteAll();
        int countAfter = dao.getCount();
        Assert.assertEquals(0, countAfter);
    }

    @Test
    public void getCount(){
        int countBefore = dao.getCount();
        Assert.assertEquals(0, countBefore);
        dao.insertAll(TestData.getAll());
        int countAfter = dao.getCount();
        Assert.assertEquals(4, countAfter);
    }
}
