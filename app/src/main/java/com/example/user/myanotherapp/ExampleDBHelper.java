package com.example.user.myanotherapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ExampleDBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "SQLiteExample.db";
    private static final int DATABASE_VERSION = 1;
    public static final String INPUT_TABLE_NAME = "input";
    public static final String INPUT_COLUMN_ID = "_id";
    public static final String INPUT_COLUMN_Title = "title";
    public static final String INPUT_COLUMN_Text = "text";
    public static final String INPUT_COLUMN_DateFrom = "dateFrom";
    public static final String INPUT_COLUMN_DateTo = "dateTo";
    public static final String INPUT_COLUMN_TimeFrom = "timeFrom";
    public static final String INPUT_COLUMN_TimeTo = "timeTo";
    public static final String INPUT_COLUMN_Type = "type";

   /* public static final String CREATE_TABLE =
   "CREATE TABLE"+INPUT_TABLE_NAME +
            "("+
            INPUT_COLUMN_ID + "INTEGER PRIMARY KEY, "+
            INPUT_COLUMN_Title +"TEXT, "+
            INPUT_COLUMN_Text + "TEXT, "+
            INPUT_COLUMN_DateFrom + "TEXT, "+
            INPUT_COLUMN_DateTo + "TEXT ";
            + ")";*/
    private static final String TABLE_CREATE =
            "CREATE TABLE " + INPUT_TABLE_NAME + "(" +
                    INPUT_COLUMN_ID + " INTEGER PRIMARY KEY, " +
                    INPUT_COLUMN_Title + " TEXT, " +
                    INPUT_COLUMN_DateFrom + " TEXT, " +
                    INPUT_COLUMN_DateTo + " TEXT, " +
                    INPUT_COLUMN_TimeFrom + " TEXT, " +
                    INPUT_COLUMN_TimeTo + " TEXT, " +
                    INPUT_COLUMN_Type + " TEXT, " +
                    INPUT_COLUMN_Text + " TEXT " +
                    ")"
           ;

    public ExampleDBHelper(Context context) {
        super(context, DATABASE_NAME , null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + INPUT_TABLE_NAME);
        onCreate(db);
    }




    public boolean insertPerson(String title,String text,String dateFrom,String dateTo,String timeFrom,String timeTo,String type)
    {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(INPUT_COLUMN_Title,title);
        contentValues.put(INPUT_COLUMN_Text,text);
        contentValues.put(INPUT_COLUMN_DateFrom,dateFrom);
        contentValues.put(INPUT_COLUMN_DateTo,dateTo);
        contentValues.put(INPUT_COLUMN_TimeFrom,timeFrom);
        contentValues.put(INPUT_COLUMN_TimeTo,timeTo);
        contentValues.put(INPUT_COLUMN_Type,type);
        db.insert(INPUT_TABLE_NAME, null, contentValues);

        return true;

    }


    public Cursor getAllPersons() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery( "SELECT * FROM " + INPUT_TABLE_NAME, null );
        return res;
    }


    public Cursor getPerson(String id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery( "SELECT * FROM " + INPUT_TABLE_NAME + " WHERE " +
                INPUT_COLUMN_ID + "=?", new String[] { id } );
        return res;
    }


    public void deleteSingleContact(String id){

        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(INPUT_TABLE_NAME, INPUT_COLUMN_ID + "=?", new String[]{id});
    }


}
