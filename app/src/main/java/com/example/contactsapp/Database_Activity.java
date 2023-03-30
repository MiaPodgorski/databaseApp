package com.example.contactsapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

public class Database_Activity extends AppCompatActivity {

    RecyclerView recyclerView;
    DatabaseControl control;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_database);

        recyclerView= findViewById(R.id.recyclerView);

        control= new DatabaseControl(this);

    }

    @Override
    public void onResume(){
        super.onResume();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        control.open();
        String[] list=control.getAllNamesArray();
        control.close();
        recyclerView.setAdapter(new Adapter(list));
    }
}