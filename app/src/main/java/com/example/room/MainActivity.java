package com.example.room;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.room.database.AppDatabase;
import com.example.room.database.PersonneDao;
import com.example.room.database.PersonneEntity;
import com.example.room.database.TestData;
import com.example.room.viewmodel.MainViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    private ListView listView;
    private ArrayAdapter<PersonneEntity> arrayAdapter;
    private List<PersonneEntity> personList = new ArrayList<>();
    private MainViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (listView == null) {
            listView = findViewById(R.id.listView);
        }

        // create/get a view model singleton
        mViewModel = new ViewModelProvider(this).get(MainViewModel.class);

        // observe data
        mViewModel.mPersons.observe(this, persons -> {
            // IMPORTANT : Room replace la list dans LiveModel à chaque changement
            // il faut utiliser un prop container personList
            personList.clear();
            personList.addAll(persons);
            // create a singleton adapter and affect to listView
            if (arrayAdapter == null) {
                arrayAdapter = new ArrayAdapter<PersonneEntity>(
                        getApplicationContext(),
                        android.R.layout.simple_list_item_1,
                        personList
                );
                listView.setAdapter(arrayAdapter);
            }
            else {
                Log.d("App", "changed notified");
                // notify adapter a change
                arrayAdapter.notifyDataSetChanged();
            }

        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    public void onClickAddAllData(MenuItem item) {
        mViewModel.addAllPersons(TestData.getAll());
    }

    public void onClickDeleteAllData(MenuItem item) {
        mViewModel.deleteAllPersons();
    }
}