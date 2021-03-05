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

    // creates a user database to store user data if it's not already created
    public Database(@Nullable Context context) {
        super(context, "user_db", null, 1);
    }

    // creates a user table using the following sql query
    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + USER_TABLE + " (" + USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + FIRST_NAME + " TEXT, " + LAST_NAME + " TEXT, " + EMAIL + " TEXT, " + PASSWORD + " TEXT, " + DIAMOND_SIZE + " INTEGER)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    // used to handle signing a user up
    // user data is passed from SignupFragment and is then inputted into SQLite database if it's not there
    public boolean signup(User userData) {
        SQLiteDatabase dbRead = this.getReadableDatabase();
        String query = "SELECT count(*) FROM " + USER_TABLE + " WHERE " + EMAIL + " = " + "'" + userData.getEmail() + "'";
        Cursor cursor = dbRead.rawQuery(query, null);
        cursor.moveToFirst();
        int count = cursor.getInt(0);

        // checks if user has already been registered
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

    // handles login functionality of the application
    // passes user email and password from LoginFragment and checks whether data is valid inside the SQLite database
    public boolean login(String email, String password) {
        String queryString = "SELECT * FROM " + USER_TABLE + " WHERE " + EMAIL + " = ?" + " AND " + PASSWORD + " = ?";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, new String[]{email, password});

        // if such user data entry is valid, then return true otherwise return false
        if (cursor.moveToFirst()) {
            return true;
        } else {
            return false;
        }
    }

    // function use to check whether user has reached maxed net worth status
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

    // function used to update the size of the diamond inside IAmRichActivity whenever user clicks on the "BOOST" button
    public void updateDiamondSize(String email, int diamondSize) {
        ContentValues cv = new ContentValues();
        cv.put(DIAMOND_SIZE, diamondSize);
        SQLiteDatabase db = this.getWritableDatabase();
        db.update(USER_TABLE, cv, "EMAIL = ?", new String[] { email });
    }

    // function used to retrieve the diamond size from the SQLite database
    public int getDiamondSize(String email) {
        SQLiteDatabase dbRead = this.getReadableDatabase();
        String query = "SELECT * FROM " + USER_TABLE + " WHERE " + EMAIL + " = " + "'" + email + "'";
        Cursor cursor = dbRead.rawQuery(query, null);
        cursor.moveToFirst();
        return cursor.getInt(5);
    }
}
