package com.example.room.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(entities = {PersonneEntity.class}, version = 1)
@TypeConverters(DateConverter.class)
public abstract class AppDatabase extends RoomDatabase{
    public static final String DATABASE_NAME = "AppDatabase.db";
    // ce variable est une instance de base de donnée qui est partagé
    // par plusieurs thread, elle doit être toujours en mémoire, jamais
    // en cache
    private static volatile AppDatabase instance;
    // l'acces à l'instance doit être synchronisé
    private static final Object LOCK = new Object();

    public abstract PersonneDao personneDao();

    public static AppDatabase getInstance(Context context) {
        if (instance == null) {
            synchronized (LOCK) {
                if (instance == null) {
                    instance = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class,
                            DATABASE_NAME)
//                            .allowMainThreadQueries()
//                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return instance;
    }
}
