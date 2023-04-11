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
    Spinner colorSpinner;
    Button deleteButton;

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
        colorSpinner=findViewById(R.id.colorSpinner);
        deleteButton=findViewById(R.id.deleteButton);

        control= new DatabaseControl(this);

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String flower=nameEdit.getText().toString();
                control.open();
                control.delete(flower);
                control.close();
                onResume();
            }
        });

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
                String flower =control.getFlower(nameEdit.getText().toString());
                control.close();
                resultView.setText(flower);
            }
        });

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String flower= nameEdit.getText().toString();
                String state =((TextView) spinner.getSelectedView()).getText().toString();
                String color=((TextView) colorSpinner.getSelectedView()).getText().toString();
                control.open();
                boolean itWorked =control.insert(flower,state,color);
                control.close();
                if(itWorked) //come back may need to add color here
                    Toast.makeText(getApplicationContext(),"added "+flower+" "+state+color,Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(getApplicationContext(),"failed "+flower+" "+state+color,Toast.LENGTH_SHORT).show();

                onResume();

            }
        });

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.states, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this, R.array.colors, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        colorSpinner.setAdapter(adapter2);
    }
//    @Override
//    public void onResume(){
//        super.onResume();
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        control.open();
//        String[] list=control.getAllNamesArray();
//        control.close();
//        recyclerView.setAdapter(new Adapter(list));
//    }
}