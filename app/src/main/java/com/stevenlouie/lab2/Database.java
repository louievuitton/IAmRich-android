package com.stevenlouie.lab2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class Database extends SQLiteOpenHelper {

    public static final String USER_TABLE = "USER_TABLE";
    public static final String FIRST_NAME = "FIRST_NAME";
    public static final String LAST_NAME = "LAST_NAME";
    public static final String EMAIL = "EMAIL";
    public static final String PASSWORD = "PASSWORD";
    public static final String DIAMOND_SIZE = "DIAMOND_SIZE";
    public static final String USER_ID = "ID";


    public Database(@Nullable Context context) {
        super(context, "user_db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + USER_TABLE + " (" + USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + FIRST_NAME + " TEXT, " + LAST_NAME + " TEXT, " + EMAIL + " TEXT, " + PASSWORD + " TEXT, " + DIAMOND_SIZE + " INTEGER)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public boolean signup(User userData) {
        SQLiteDatabase dbRead = this.getReadableDatabase();
        String query = "SELECT count(*) FROM " + USER_TABLE + " WHERE " + EMAIL + " = " + "'" + userData.getEmail() + "'";
        Cursor cursor = dbRead.rawQuery(query, null);
        cursor.moveToFirst();
        int count = cursor.getInt(0);
        if (count > 0) {
            return false;
        }
        else {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues cv = new ContentValues();

            cv.put(FIRST_NAME, userData.getFirstName());
            cv.put(LAST_NAME, userData.getLastName());
            cv.put(EMAIL, userData.getEmail());
            cv.put(PASSWORD, userData.getPassword());
            cv.put(DIAMOND_SIZE, userData.getDiamondSize());

            long insert = db.insert(USER_TABLE, null, cv);
            if (insert == -1) {
                return false;
            }
            else {
                return true;
            }
        }
    }

    public boolean login(String email, String password) {
        String queryString = "SELECT * FROM " + USER_TABLE + " WHERE " + EMAIL + " = ?" + " AND " + PASSWORD + " = ?";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, new String[]{email, password});
        if (cursor.moveToFirst()) {
            return true;
        } else {
            return false;
        }
    }

    public String[] checkIfElonRich(String email) {
        SQLiteDatabase dbRead = this.getReadableDatabase();
        String query = "SELECT * FROM " + USER_TABLE + " WHERE " + EMAIL + " = " + "'" + email + "'";
        Cursor cursor = dbRead.rawQuery(query, null);
        cursor.moveToFirst();
        String[] result = new String[2];
        result[0] = cursor.getString(1);
        result[1] = String.valueOf(cursor.getInt(5));
        return result;
    }

    public void updateDiamondSize(String email, int diamondSize) {
        ContentValues cv = new ContentValues();
        cv.put(DIAMOND_SIZE, diamondSize);
        SQLiteDatabase db = this.getWritableDatabase();
        db.update(USER_TABLE, cv, "EMAIL = ?", new String[] { email });
    }

    public int getDiamondSize(String email) {
        SQLiteDatabase dbRead = this.getReadableDatabase();
        String query = "SELECT * FROM " + USER_TABLE + " WHERE " + EMAIL + " = " + "'" + email + "'";
        Cursor cursor = dbRead.rawQuery(query, null);
        cursor.moveToFirst();
        return cursor.getInt(5);
    }
}
