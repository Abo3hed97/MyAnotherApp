package com.example.user.myanotherapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.ContactsContract;
import android.util.Log;
import android.widget.TextView;

import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Year;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Local Database
 */
public class ExampleDBHelper extends SQLiteOpenHelper {


    public static final String DATABASE_NAME = "SQLiteExample.db";
    private static final int DATABASE_VERSION = 1;
    /**
     * Bullet Table
     */
    public static final String INPUT_TABLE_NAME = "input";
    public static final String INPUT_COLUMN_ID = "_id";
    public static final String INPUT_COLUMN_UID = "uid";
    public static final String INPUT_COLUMN_Title = "title";
    public static final String INPUT_COLUMN_Text = "text";
    public static final String INPUT_COLUMN_DateFrom = "dateFrom";
    public static final String INPUT_COLUMN_DateTo = "dateTo";
    public static final String INPUT_COLUMN_TimeFrom = "timeFrom";
    public static final String INPUT_COLUMN_TimeTo = "timeTo";
    public static final String INPUT_COLUMN_Type = "type";
    public static final String INPUT_COLUMN_Months = "months";
    public static final String INPUT_COLUMN_Imp = "imp";
    public static final String INPUT_COLUMN_Vimp = "vimp";
    /**
     * User Table Columns
     */
    private static final String TABLE_USER = "user";
    private static final String COLUMN_USER_ID = "user_id";
    private static final String COLUMN_USER_NAME = "user_name";
    private static final String COLUMN_USER_EMAIL = "user_email";
    private static final String COLUMN_USER_PASSWORD = "user_password";
    /**
     * Creating User Table with sqlite
     */

    private String CREATE_USER_TABLE = "CREATE TABLE " + TABLE_USER + "("
            + COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_USER_NAME + " TEXT,"
            + COLUMN_USER_EMAIL + " TEXT," + COLUMN_USER_PASSWORD + " TEXT" + ")";
    /**
     * Creating Bullet Table with sqlite
     */

    private static final String TABLE_CREATE =
            "CREATE TABLE " + INPUT_TABLE_NAME + "(" +
                    INPUT_COLUMN_ID + " INTEGER PRIMARY KEY, " +
                    INPUT_COLUMN_Title + " TEXT, " +
                    INPUT_COLUMN_DateFrom + " TEXT, " +
                    INPUT_COLUMN_DateTo + " TEXT, " +
                    INPUT_COLUMN_TimeFrom + " TEXT, " +
                    INPUT_COLUMN_TimeTo + " TEXT, " +
                    INPUT_COLUMN_Type + " TEXT, " +
                    INPUT_COLUMN_Months + " TEXT, " +
                    INPUT_COLUMN_Imp + " INTEGER, " +
                    INPUT_COLUMN_Vimp + " INTEGER, " +
                    INPUT_COLUMN_Text + " TEXT, " +
                    INPUT_COLUMN_UID + " INTEGER, " +
                    " FOREIGN KEY (" + INPUT_COLUMN_UID + ") REFERENCES " + TABLE_USER + "(" + COLUMN_USER_ID + "))";

    public ExampleDBHelper(Context context) {
        super(context, DATABASE_NAME , null, DATABASE_VERSION);
    }

    /**
     * Executing the sql statement with sqlite
     * @param db object from SQLITEDATABASE Class
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE);
        db.execSQL(CREATE_USER_TABLE);
    }

    /**
     * On Upgrade droping the Table and create them again
     * @param db object from SQLITEDATABASE Class
     * @param oldVersion old version id
     * @param newVersion new version id
     */


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + INPUT_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_USER);
        onCreate(db);
    }

    /**
     * Inserting data to Bullet Table
     * Used in monthly Task to insert title text and month
     * @param title
     * @param text
     * @param month
     * @return
     */
    public boolean insertPerson(String title,String text,String month) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(INPUT_COLUMN_Title, title);
        contentValues.put(INPUT_COLUMN_Text, text);
        contentValues.put(INPUT_COLUMN_Months, month);
        db.insert(INPUT_TABLE_NAME, null, contentValues);
        return true;
    }


    /**
     * Inserting data to Bullet Table
     * Used In new Bullet to inserd data to all Columns
     * @param title
     * @param text
     * @param dateFrom
     * @param dateTo
     * @param timeFrom
     * @param timeTo
     * @param type
     * @param months
     * @param imp
     * @param vimp
     * @return
     */
    public boolean insertPerson(String title,String text,String dateFrom,String dateTo,
                                String timeFrom,String timeTo,String type,String months,int imp,int vimp)
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
        contentValues.put(INPUT_COLUMN_Months,months);
        contentValues.put(INPUT_COLUMN_Imp,imp);
        contentValues.put(INPUT_COLUMN_Vimp,vimp);
        db.insert(INPUT_TABLE_NAME, null, contentValues);

        return true;

    }

    /**
     * getting data from Bullet Table.
     * @return
     */
    public Cursor getAllPersons() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery( "SELECT * FROM " + INPUT_TABLE_NAME, null );
        return res;
    }


    /**
     * deleting a Bullet from database
     * @param id
     */

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
        // de
        // dlete user record by id
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

        return cursorCount > 0;

    }

    /**
     * checking if the user already exists
     * @param email
     * @param password
     * @return
     */
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
        return cursorCount > 0;

    }
    /**
     * Getting Bullet object compere the date to daysList in dailylog and show the Title in the right date in Dailylog
     * @param d week days
     * @return List with Bullet titles
     */


    public ArrayList<ArrayList<String>> getData(ArrayList<String> d) {
       ArrayList<ArrayList<String>> b=new ArrayList<>();


        for (int i = 0; i < d.size(); i++) {
            ArrayList<String> k=new ArrayList<>();
            Cursor cursor = this.getAllPersons();
            if (cursor.moveToFirst()) {
                while (cursor.isAfterLast() == false) {

                    String check = cursor.getString(cursor.getColumnIndex(INPUT_COLUMN_DateFrom));
                    if (d.get(i).equals(check)) {

                        //b.get(i).add(cursor.getString(cursor.getColumnIndex(INPUT_COLUMN_Title)));
                        k.add(cursor.getString(cursor.getColumnIndex(INPUT_COLUMN_Title)));
                    }

                    cursor.moveToNext();
                }
            }
            b.add(k);

        }

        return b;


    }
    /**
     *Getting Bullet object
     * compere the date to MonthlyList in MonthlyLog
     * checking if it's important Bullet
     * and show the Title in the right date in MonthlLog
     * @param daysofMonths List of Months Days
     * @return List with Bullet title.
     * @throws ParseException
     */

    public ArrayList<ArrayList<String>> getImportantBullets(ArrayList<String> daysofMonths) throws ParseException {
        ArrayList<ArrayList<String>> importantBullets=new ArrayList<>();
        for (int i = 0; i < daysofMonths.size(); i++) {
            ArrayList<String> importantBulletsforDay=new ArrayList<>();
            Cursor cursor = this.getAllPersons();
            if (cursor.moveToFirst()) {
                while (cursor.isAfterLast() == false) {
                    boolean check1=false;
                    boolean check2=false;
                      if(cursor.getString(cursor.getColumnIndex(INPUT_COLUMN_DateFrom))!=null){
                          check1 =cursor.getString(cursor.getColumnIndex(INPUT_COLUMN_DateFrom)).equals(daysofMonths.get(i));}
                      if(cursor.getString(cursor.getColumnIndex(INPUT_COLUMN_DateFrom))!=null){
                       check2=cursor.getString(cursor.getColumnIndex(INPUT_COLUMN_Imp)).equals("1");}
                    if (check1&&check2){
                        importantBulletsforDay.add(cursor.getString(cursor.getColumnIndex(INPUT_COLUMN_Title)));

                 }
                    cursor.moveToNext();
                }
            }
            importantBullets.add(importantBulletsforDay);
        }
        return importantBullets;
    }
    /**
     * Getting Bullet object
     * compere the date to FutureLogList in FutureLog
     * checking if it's Vimportant Bullet
     * and show the Title in the right date in FurureLog
     * @return Arry wit vinprtant Bullets title
     */




    public String[]getVeryImportantBullets(ArrayList<String>months,int year) throws ParseException {
        String veryImportantBullets[]= new String[12];
        String check1;
        int check2;
        for (int i =0; i < months.size(); i++) {
            Cursor cursor = this.getAllPersons();
            if (cursor.moveToFirst()) {
                while (cursor.isAfterLast() == false) {
                    Log.i("Helllo","Hellllllllllllllo");
                    Calendar cal = Calendar.getInstance();
                    SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
                    if(cursor.getString(cursor.getColumnIndex(INPUT_COLUMN_DateFrom))!=null){
                    cal.setTime(sdf.parse(cursor.getString(cursor.getColumnIndex(INPUT_COLUMN_DateFrom))));}
                    // all done}
                    check1 = monthName(cal.get(Calendar.MONTH));
                    Log.i(check1,"Hellllllllllllllo");
                    check2 = cal.get(Calendar.YEAR);
                    Log.i(String.valueOf(check2),"Hellllllllllllllo");
                    String check3="";
                    if(cursor.getString(cursor.getColumnIndex(INPUT_COLUMN_Vimp))!=null){
                    check3 = cursor.getString(cursor.getColumnIndex(INPUT_COLUMN_Vimp));}
                    if (check3.equals("1") &&months.get(i).equals(check1)&&String.valueOf(year).equals(String.valueOf(check2))) {
                        veryImportantBullets[i]=cursor.getString(cursor.getColumnIndex(INPUT_COLUMN_Title));
                    }
                    cursor.moveToNext();
                }
            }
        }
        return veryImportantBullets;
    }



    public String monthName(int i){
        String months1[]=new String[13];
        months1 = new String[]{"","January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
        return months1[i];
    }




    public List<Bullet> loadData(String day) {

        Cursor cursor = this.getAllPersons();
        List<Bullet> bullets=new ArrayList<>();
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast())
                {
                       if(cursor.getString(cursor.getColumnIndex(INPUT_COLUMN_DateFrom))!=null){
                       Log.i("HELLLLLLLLLLLLLLLLO",cursor.getString(cursor.getColumnIndex(INPUT_COLUMN_DateFrom)));}
                    if (cursor.getString(cursor.getColumnIndex(INPUT_COLUMN_DateFrom))!=null&&cursor.getString(cursor.getColumnIndex(INPUT_COLUMN_DateFrom)).equals(day)) {
                        bullets.add(new Bullet(
                                cursor.getString(cursor.getColumnIndex(INPUT_COLUMN_ID)),
                                cursor.getString(cursor.getColumnIndex(INPUT_COLUMN_UID)),
                                cursor.getString(cursor.getColumnIndex(INPUT_COLUMN_Title)),
                                cursor.getString(cursor.getColumnIndex(INPUT_COLUMN_Text)),
                                cursor.getString(cursor.getColumnIndex(INPUT_COLUMN_DateFrom)),
                                cursor.getString(cursor.getColumnIndex(INPUT_COLUMN_DateTo)),
                                cursor.getString(cursor.getColumnIndex(INPUT_COLUMN_TimeFrom)),
                                cursor.getString(cursor.getColumnIndex(INPUT_COLUMN_TimeTo)),
                                cursor.getString(cursor.getColumnIndex(INPUT_COLUMN_Type)),
                                cursor.getString(cursor.getColumnIndex(INPUT_COLUMN_Months)),
                                cursor.getString(cursor.getColumnIndex(INPUT_COLUMN_Imp)),
                                cursor.getString(cursor.getColumnIndex(INPUT_COLUMN_Vimp))

                        ));
                    }


                    cursor.moveToNext();
                }

            }

return bullets;
    }


}

