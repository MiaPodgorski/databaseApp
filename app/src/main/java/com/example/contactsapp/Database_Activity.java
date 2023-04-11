package com.example.contactsapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Database_Activity extends AppCompatActivity {

    RecyclerView recyclerView;
    DatabaseControl control;
    TextView result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_database);

        recyclerView= findViewById(R.id.recyclerView);
        result = findViewById(R.id.result);

        control= new DatabaseControl(this);

    }

    @Override
    public void onResume(){
        super.onResume();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        control.open();
        String[] list=control.getAllNamesArray();
        control.close();
        Adapter adapt =new Adapter(list);
        adapt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Adapter.ViewHolder viewHolder =(Adapter.ViewHolder) view.getTag();
                TextView textView = viewHolder.getTextView();
                String name = textView.getText().toString();
                control.open();
                String data= control.getFlower(name);
                control.close();
                result.setText(name+": "+data);
            }
        });
        recyclerView.setAdapter(adapt);
    }
}