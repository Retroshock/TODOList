package com.example.adrian.todolist;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Adrian on 15.05.2017.
 */

public class DatabaseObject {
    private static Database dbHelper ;
    private static SQLiteDatabase db ;

    public DatabaseObject (Context context){
        openDatabase(context);
    }

    public static void openDatabase (Context context){
        dbHelper = new Database(context);
        dbHelper.getWritableDatabase();
        db = dbHelper.getReadableDatabase();
    }

    public static Database getDbHelper() {
        return dbHelper;
    }

    public static SQLiteDatabase getDb() {
        return db;
    }

    public static void closeDatabase(){
        dbHelper.close();
        db.close();
    }

}
