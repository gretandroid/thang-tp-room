package com.example.room;

import static com.example.room.R.id.iconImageView;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.room.database.PersonneEntity;
import com.example.room.database.TestData;
import com.example.room.recyclerview.PersonAdapter;
import com.example.room.viewmodel.MainViewModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements PersonAdapter.PersonAdapterListener {

    private RecyclerView recyclerView;
    private PersonAdapter adapter;
    private List<PersonneEntity> personList = new ArrayList<>();
    private MainViewModel mViewModel;
    private Menu menu;
    private Map<Integer, PersonneEntity> checkedIdPersonMap = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (recyclerView == null) {
            recyclerView = findViewById(R.id.recyclerView);
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
            if (adapter == null) {
                adapter = new PersonAdapter(personList, this);
                recyclerView.setLayoutManager(new LinearLayoutManager(this));
                recyclerView.setAdapter(adapter);
            } else {
                Log.d("App", "changed notified");
                // notify adapter a change
                adapter.notifyDataSetChanged();
            }

        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.menu = menu;
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);
        MenuItem deleteMenuItem = menu.findItem(R.id.deleteAllData);
        deleteMenuItem.setVisible(false);
        return true;
    }

    public void onClickAddAllData(MenuItem item) {
        mViewModel.addAllPersons(TestData.getAll());
    }

    public void onClickDeleteAllData(MenuItem item) {
        List<PersonneEntity> personList = new ArrayList<>(checkedIdPersonMap.values());
        mViewModel.deleteAllPersons(personList);
        checkedIdPersonMap.clear();
        refreshMenuItems();
    }

    @Override
    public void onClick(View view, PersonneEntity person) {
        if (view instanceof ImageView && view.getId() == iconImageView) {
            PersonneEntity foundPerson = checkedIdPersonMap.get(person.getId());
            if (foundPerson == null) checkedIdPersonMap.put(person.getId(), person);
            if (foundPerson != null) checkedIdPersonMap.remove(person.getId());
            // update Menu item visible
            refreshMenuItems();
        }
    }

    private void refreshMenuItems() {
        int checkedItemCounter = checkedIdPersonMap.size();
        MenuItem deleteMenuItem = menu.findItem(R.id.deleteAllData);
        MenuItem addAllData = menu.findItem(R.id.addAllData);
        deleteMenuItem.setVisible(checkedItemCounter > 0 ? true : false);
        addAllData.setVisible(checkedItemCounter > 0 ? false : true);
        if (checkedItemCounter == 0) adapter.notifyDataSetChanged();
    }
}