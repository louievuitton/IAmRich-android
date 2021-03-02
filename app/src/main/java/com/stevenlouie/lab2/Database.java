package com.stevenlouie.lab2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class Database extends SQLiteOpenHelper {

    public static final String USER_TABLE = "USER_TABLE";
    public static final String FIRST_NAME = "FIRST_NAME";
    public static final String LAST_NAME = "LAST_NAME";
    public static final String EMAIL = "EMAIL";
    public static final String PASSWORD = "PASSWORD";
    public static final String USER_ID = "ID";


    public Database(@Nullable Context context) {
        super(context, "user_db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + USER_TABLE + " (" + USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + FIRST_NAME + " TEXT, " + LAST_NAME + " TEXT, " + EMAIL + " TEXT, " + PASSWORD + " TEXT)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public boolean signup(User userData) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(FIRST_NAME, userData.getFirstName());
        cv.put(LAST_NAME, userData.getLastName());
        cv.put(EMAIL, userData.getEmail());
        cv.put(PASSWORD, userData.getPassword());

        long insert = db.insert(USER_TABLE, null, cv);
//        db.close();
        if (insert == -1) {
            return false;
        }
        else {
            return true;
        }
    }

    public String login(String email, String password) {
        String queryString = "SELECT * FROM " + USER_TABLE + " WHERE " + EMAIL + " = ? AND " + PASSWORD + " = ?";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, new String[]{ email, password});
//        db.close();
        if (cursor.moveToFirst()) {
            String name = cursor.getString(1);
//            cursor.close();
            return name;
        }
        else {
            cursor.close();
            return "";
        }
    }
}
