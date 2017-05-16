package com.example.adrian.todolist;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;


/**
 * Created by Adrian on 15.05.2017.
 */

public class Database extends SQLiteOpenHelper {

    private static final String SQL_CREATE_USERS_TABLE = "CREATE TABLE users ( id INTEGER PRIMARY KEY, username TEXT, " +
            "password TEXT );";
    private static final String SQL_CREATE_TODOS_TABLE = "CREATE TABLE todos ( id_user INTEGER, info TEXT );";
    private static final String DB_NAME = "Login";
    private static final int DB_VERSION = 3;

    public Database(Context context){
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_USERS_TABLE);
        db.execSQL(SQL_CREATE_TODOS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        try {
            db.rawQuery("Select * from users", null);
        } catch (SQLiteException e) {
            db.execSQL(SQL_CREATE_USERS_TABLE);
        }

    }
}
