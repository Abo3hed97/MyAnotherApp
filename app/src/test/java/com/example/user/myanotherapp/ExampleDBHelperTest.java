package com.example.user.myanotherapp;

import org.junit.Test;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.content.Context;
import com.example.user.myanotherapp.ExampleDBHelper;
import com.example.user.myanotherapp.New_Bullet;

import android.util.Log;

import static org.junit.Assert.*;

public class ExampleDBHelperTest  {
    ExampleDBHelper db;
    Context context;
    @Test
    public void testDrop()
    {
        assertTrue(context.deleteDatabase(ExampleDBHelper.DATABASE_NAME));
    }

   @Test
    public void testCreateDB()
   {
       db = new ExampleDBHelper(context);

       assertTrue(db.insertPerson("Title","Text","03"));
   }
}