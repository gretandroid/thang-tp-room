package com.example.room.database;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface PersonneDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertPersonne(PersonneEntity personneEntity);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<PersonneEntity> personnes);

    @Delete
    void deletePersonne(PersonneEntity personneEntity);

    @Query("SELECT * FROM person WHERE id=:id")
    PersonneEntity getPersonneById(int id);

//    default LiveData<List<PersonneEntity>> getAllLiveData() {
//        return new MutableLiveData<>(getAll());
//    }

    @Query("SELECT * FROM person ORDER BY date DESC")
    LiveData<List<PersonneEntity>> getAll();

    @Query("DELETE FROM person")
    int deleteAll();

    @Query("DELETE FROM person where id in (:idList)")
    int deleteAllPersons(List<Integer> idList);

    @Query("SELECT count(*) FROM person")
    int getCount();
}
