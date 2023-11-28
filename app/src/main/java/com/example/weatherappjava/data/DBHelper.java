package com.example.weatherappjava.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper {

    public DBHelper(Context context) {
        super(context, "myDB", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("create table mytable ("
                + "id integer primary key autoincrement,"
                + "city text,"
                + "temperMax,"
                + "temperMin,"
                + "temper text,"
                + "time text,"
                + "azimuth text,"
                + "speed text" + ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
