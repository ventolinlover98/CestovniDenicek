package com.example.cestovnidenicek;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cestovnidenicek.Adapter.Adapter;
import com.example.cestovnidenicek.Constants.Constants;
import com.example.cestovnidenicek.Data.AppData;
import com.example.cestovnidenicek.Database.RoomDB;

import java.util.ArrayList;
import java.util.List;

public class AddActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    List<String> titles;
    List<Integer> images;
    Adapter adapter;
    RoomDB database;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add);
        recyclerView = findViewById(R.id.recyclerView);

        addAddTitles();
        addAllImages();
        persistAppData();
        database = RoomDB.getInstance(this);
        Log.d("AddActivity", "aaaaaaaaaaaaaaaaaaa" + database.mainDao().getAllSelected(false).get(0).getItemname());


        adapter = new Adapter(this, titles, images, AddActivity.this);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(adapter);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });



    }

    private void persistAppData(){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = prefs.edit();

        database = RoomDB.getInstance(this);
        AppData appData = new AppData(database);
        int last = prefs.getInt(AppData.LAST_VERSION, 0);
        if(!prefs.getBoolean(Constants.FIRST_TIME_CAMEL_CASE, false)){
            appData.persistAllData();
            editor.putBoolean(Constants.FIRST_TIME_CAMEL_CASE, true);
            editor.commit();

        }else if(last < AppData.NEW_VERSION){
            database.mainDao().deleteAllSystemItems(Constants.SYSTEM_SMALL);
            appData.persistAllData();
            editor.putInt(AppData.LAST_VERSION, AppData.NEW_VERSION);
            editor.commit();
        }
    }

    private void addAddTitles() {
        titles = new ArrayList<>();
        titles.add(Constants.NEZ_POJEDU_CAMEL_CASE);
        titles.add(Constants.NOUZOVE_KONTAKTY_CAMEL_CASE);
        titles.add(Constants.DOPRAVA_CAMEL_CASE);
        titles.add(Constants.UBYTOVANI_CAMEL_CASE);
        titles.add(Constants.ROZPOCET_CAMEL_CASE);
        titles.add(Constants.CESTOVNI_DOKUMENTY_CAMEL_CASE);
        titles.add(Constants.REZERVACE_A_VSTUPENKY_CAMEL_CASE);
        titles.add(Constants.SUVENYRY_CAMEL_CASE);
        titles.add(Constants.RESTAURACE_V_OKOLI_CAMEL_CASE);
        titles.add(Constants.MUJ_LIST_CAMEL_CASE);
    }

    private void addAllImages() {
        images = new ArrayList<>();
        images.add(R.drawable.cas);
        images.add(R.drawable.pomoc);
        images.add(R.drawable.autobus);
        images.add(R.drawable.postel);
        images.add(R.drawable.prasatko);
        images.add(R.drawable.dokument);
        images.add(R.drawable.listek);
        images.add(R.drawable.darek);
        images.add(R.drawable.jidlo);
        images.add(R.drawable.list);

    }
}