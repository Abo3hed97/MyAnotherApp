package com.example.user.myanotherapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import static com.example.user.myanotherapp.listOfNotes.INPUT_COLUMN_Title;

public class ExampleDBHelper extends SQLiteOpenHelper {


    public static final String DATABASE_NAME = "SQLiteExample.db";
    private static final int DATABASE_VERSION = 1;
    //New Bullet Table
    public static final String INPUT_TABLE_NAME = "input";
    public static final String INPUT_COLUMN_ID = "_id";
    public static final String INPUT_COLUMN_Title = "title";
    public static final String INPUT_COLUMN_Text = "text";
    public static final String INPUT_COLUMN_DateFrom = "dateFrom";
    public static final String INPUT_COLUMN_DateTo = "dateTo";
    public static final String INPUT_COLUMN_TimeFrom = "timeFrom";
    public static final String INPUT_COLUMN_TimeTo = "timeTo";
    public static final String INPUT_COLUMN_Type = "type";

    //Login Table
    private static final String TABLE_USER = "user";
    private static final String COLUMN_USER_ID = "user_id";
    private static final String COLUMN_USER_NAME = "user_name";
    private static final String COLUMN_USER_EMAIL = "user_email";
    private static final String COLUMN_USER_PASSWORD = "user_password";

    private String CREATE_USER_TABLE = "CREATE TABLE " + TABLE_USER + "("
            + COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_USER_NAME + " TEXT,"
            + COLUMN_USER_EMAIL + " TEXT," + COLUMN_USER_PASSWORD + " TEXT" + ")";

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
        db.execSQL(CREATE_USER_TABLE);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + INPUT_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_USER);
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

    public void addUser(User user)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues userValies = new ContentValues();
        userValies.put(COLUMN_USER_NAME, user.getName());
        userValies.put(COLUMN_USER_EMAIL, user.getEmail());
        userValies.put(COLUMN_USER_PASSWORD, user.getPassword());
        db.insert(TABLE_USER, null,userValies);
        db.close();
    }

    public List<User> getAllUser()
    {
        String[] columns = {COLUMN_USER_ID,
                COLUMN_USER_EMAIL,
                COLUMN_USER_NAME,
                COLUMN_USER_PASSWORD };
        String sortOrder = COLUMN_USER_NAME + "ASC";
        List<User> userList = new ArrayList<User>();
        SQLiteDatabase db = this.getReadableDatabase();
         //* SELECT user_id,user_name,user_email,user_password FROM user ORDER BY user_name;


        Cursor cursor = db.query(TABLE_USER,
                columns,
                null,
                null,
                null,
                null,
                sortOrder);

        if (cursor.moveToFirst()) {
            do {
                User user = new User();
                user.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_USER_ID))));
                user.setName(cursor.getString(cursor.getColumnIndex(COLUMN_USER_NAME)));
                user.setEmail(cursor.getString(cursor.getColumnIndex(COLUMN_USER_EMAIL)));
                user.setPassword(cursor.getString(cursor.getColumnIndex(COLUMN_USER_PASSWORD)));
                // Adding user record to list
                userList.add(user);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        // return user list
        return userList;
    }
    public void updateUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_NAME, user.getName());
        values.put(COLUMN_USER_EMAIL, user.getEmail());
        values.put(COLUMN_USER_PASSWORD, user.getPassword());

        // updating row
        db.update(TABLE_USER, values, COLUMN_USER_ID + " = ?",
                new String[]{String.valueOf(user.getId())});
        db.close();
    }
    public void deleteUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        // delete user record by id
        db.delete(TABLE_USER, COLUMN_USER_ID + " = ?",
                new String[]{String.valueOf(user.getId())});
        db.close();
    }
    public boolean checkUser(String email) {

        // array of columns to fetch
        String[] columns = {
                COLUMN_USER_ID
        };
        SQLiteDatabase db = this.getReadableDatabase();

        // selection criteria
        String selection = COLUMN_USER_EMAIL + " = ?";

        // selection argument
        String[] selectionArgs = {email};

        // query user table with condition
        /**

         * SELECT user_id FROM user WHERE user_email = 'ex@gmail.com';
         */
        Cursor cursor = db.query(TABLE_USER,
                columns,
                selection,
                selectionArgs,
                null,
                null,
                null);
        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();

        if (cursorCount > 0) {
            return true;
        }

        return false;
    }
    //Test
    public boolean checkUser(String email, String password) {

        // array of columns to fetch
        String[] columns = {
                COLUMN_USER_ID
        };
        SQLiteDatabase db = this.getReadableDatabase();
        // selection criteria
        String selection = COLUMN_USER_EMAIL + " = ?" + " AND " + COLUMN_USER_PASSWORD + " = ?";

        // selection arguments
        String[] selectionArgs = {email, password};


        /*
         * SELECT user_id FROM user WHERE user_email = 'jack@androidtutorialshub.com' AND user_password = 'qwerty';
         */
        Cursor cursor = db.query(TABLE_USER,
                columns,
                selection,
                selectionArgs,
                null,
                null,
                null);

        int cursorCount = cursor.getCount();

        cursor.close();
        db.close();
        if (cursorCount > 0) {
            return true;
        }

        return false;
    }


    public String[] getData(ArrayList<String> d) {
        String[] a = new String[7];
        int count=0;
        for (int i = 0; i < d.size(); i++) {
            Cursor cursor = this.getAllPersons();
            if (cursor.moveToFirst()) {
                while (cursor.isAfterLast() == false) {

                  /*  HashMap<String, String> map = new HashMap<String, String>();
                    map.put(INPUT_COLUMN_ID, cursor.getString(cursor.getColumnIndex(INPUT_COLUMN_ID)));
                    map.put(INPUT_COLUMN_Title, cursor.getString(cursor.getColumnIndex(INPUT_COLUMN_Title)));
                    map.put(INPUT_COLUMN_Text, cursor.getString(cursor.getColumnIndex(INPUT_COLUMN_Text)));
                    map.put(INPUT_COLUMN_DateFrom,cursor.getString(cursor.getColumnIndex(INPUT_COLUMN_DateFrom)));*/

                    String check = cursor.getString(cursor.getColumnIndex(INPUT_COLUMN_DateFrom));
                    if (d.get(i).equals(check)) {
                        count++;
                        a[i]+=count+".";
                        a[i] += cursor.getString(cursor.getColumnIndex(INPUT_COLUMN_Title));
                        a[i]+="\n";
                        a[i]=delete(a[i]);
                    }

                    cursor.moveToNext();
                }
                count=0;



            }
        }
        return a;

    }


    public String delete(String s)
    {

        return s.replace("null","");
    }




}
