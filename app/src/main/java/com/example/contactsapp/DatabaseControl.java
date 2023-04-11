package com.example.contactsapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class DatabaseControl {

    SQLiteDatabase database;
    DatabaseHelper helper;

    public DatabaseControl(Context context){
        helper= new DatabaseHelper(context);
    }

    public void open(){
        database=helper.getWritableDatabase();
    }

    public void close(){
        helper.close();
    }

    public boolean insert(String flower, String state, String color){
        ContentValues values = new ContentValues();
        values.put("flower", flower);
        values.put("state", state);
        values.put("color", color);
        return database.insert("flowers", null, values)>0;
    }

    public void delete(String flower){
        database.delete("flowers", "flower=\""+flower+"\"", null);
    }

    public String getFlower(String flower){
        String query= "select state, color from flowers where flower=\""+flower+"\"";
        Cursor cursor= database.rawQuery(query,null);
        cursor.moveToFirst();
        String state = cursor.getString(0);
        String color = cursor.getString(1);
        cursor.close();
        return state +" "+ color;
    }

    public String[] getAllNamesArray(){
        String query= "select flower from flowers";
        Cursor cursor = database.rawQuery(query,null);
        cursor.moveToFirst();
        ArrayList<String> list = new ArrayList<String>();
        //while not at the end of the table
        while(!cursor.isAfterLast()){
            String flower= cursor.getString(0);
            list.add(flower);
            cursor.moveToNext();
        }
        cursor.close();
        String[] array= new String[list.size()];
        return list.toArray(array);
    }
}
