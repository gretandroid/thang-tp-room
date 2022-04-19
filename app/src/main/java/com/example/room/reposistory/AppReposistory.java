package com.example.room.reposistory;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.room.database.AppDatabase;
import com.example.room.database.PersonneDao;
import com.example.room.database.PersonneEntity;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

// communicate avec la DB
public class AppReposistory {
    private static AppReposistory instance;
    public LiveData<List<PersonneEntity>> mPersons;
    private AppDatabase database;
    // all Room queries must be invoked in another thread
    private Executor executor = Executors.newSingleThreadExecutor();

    private AppReposistory(Context context) {
        database = AppDatabase.getInstance(context);
        // load data into LiveData
        getAllPersons();
    }

    public static AppReposistory getInstance(Context context) {
        if (instance == null) {
            instance = new AppReposistory(context);
        }
        return instance;
    }

    private void getAllPersons() {
        PersonneDao dao = database.personneDao();
        mPersons = dao.getAll();
    }

    public void addAllPersons(List<PersonneEntity> persons) {
        executor.execute(() -> {
            database.personneDao().insertAll(persons);
        });
    }

    public void deleteAllPersons() {
        executor.execute(() -> {
            database.personneDao().deleteAll();
        });
    }
}
