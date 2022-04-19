package com.example.room.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.room.database.PersonneEntity;
import com.example.room.reposistory.AppReposistory;

import java.util.List;

// car on a besoin un context pour reposistory donc
// on extends AndroidViewModel au lieu de ViewModel
public class MainViewModel extends AndroidViewModel {
    private AppReposistory reposistory;
    public LiveData<List<PersonneEntity>> mPersons;

    public MainViewModel(@NonNull Application application) {
        super(application);
        reposistory = AppReposistory.getInstance(application.getApplicationContext());
        mPersons = reposistory.mPersons;
    }

    public void addAllPersons(List<PersonneEntity> persons) {
        reposistory.addAllPersons(persons);
    }

    public void deleteAllPersons(List<PersonneEntity> persons) {
        reposistory.deleteAllPersons(persons);
    }
}
