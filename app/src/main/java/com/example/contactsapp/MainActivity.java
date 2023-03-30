package com.example.contactsapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText nameEdit;
    Spinner spinner;
    DatabaseControl control;
    Button addButton;
    Button getButton;
    TextView resultView;
    Button dataBase;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nameEdit= findViewById(R.id.nameEdit);
        spinner=findViewById(R.id.spinner);
        addButton= findViewById(R.id.addButton);
        getButton=findViewById(R.id.getButton);
        resultView = findViewById(R.id.resultView);
        dataBase=findViewById(R.id.dataBase);
        recyclerView=findViewById(R.id.recyclerView);

        control= new DatabaseControl(this);

        dataBase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),Database_Activity.class);
                startActivity(i);
            }
        });

        getButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                control.open();
                String state =control.getState(nameEdit.getText().toString());
                control.close();
                resultView.setText(state);
            }
        });

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name= nameEdit.getText().toString();
                String state =((TextView) spinner.getSelectedView()).getText().toString();
                control.open();
                boolean itWorked =control.insert(name,state);
                control.close();
                if(itWorked)
                    Toast.makeText(getApplicationContext(),"added "+name+" "+state,Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(getApplicationContext(),"failed "+name+" "+state,Toast.LENGTH_SHORT).show();

            }
        });

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.states, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

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