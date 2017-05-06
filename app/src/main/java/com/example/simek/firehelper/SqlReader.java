package com.example.simek.firehelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

/**
 * Created by Simek on 30. 04. 2017.
 */

public class SqlReader extends SQLiteOpenHelper {
    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "FeedReader.db";

    public SqlReader(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
        db.execSQL(SQL_CREATE_ENTRIES1);
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
       // db.execSQL();
        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    public static class FeedEntry implements BaseColumns {
        public static final String Users = "Uporabniki";
        public static final String Name = "Ime";
        public static final String Surname = "Priimek";
        public static final String UserName = "UpIme";
        public static final String Password = "Geslo";
    }


    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + FeedEntry.Users + " (" +
                    FeedEntry._ID + " INTEGER PRIMARY KEY," +
                    FeedEntry.Name + " TEXT," +
                    FeedEntry.Surname + " TEXT)"+
                    FeedEntry.UserName + " TEXT)"+
                    FeedEntry.Password + " TEXT)";

    public static class FeedEntry1 implements BaseColumns {
        public static final String Drustva = "GasilskaDrustva";
        public static final double x = 2;
        public static final double y = 24;
        public static final String NameGD = "ImeGD";

    }


    private static final String SQL_CREATE_ENTRIES1 =
            "CREATE TABLE " + FeedEntry1.Drustva + " (" +
                    FeedEntry1._ID + " INTEGER PRIMARY KEY," +
                    FeedEntry1.x + " TEXT," +
                    FeedEntry1.y + " TEXT)"+
                    FeedEntry1.NameGD + " TEXT)";


}