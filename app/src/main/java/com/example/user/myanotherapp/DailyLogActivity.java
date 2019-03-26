package com.example.user.myanotherapp;

import android.content.Intent;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.user.myanotherapp.Mysql.Dataimport;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class DailyLogActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    /**
     * to go to next Week and previous Week
     */
    Button ForwardB,BackwardB;
    /**
     * contains the days of every Week
     */
   public static ArrayList<String> days = new ArrayList<>();
    /**
     * references on the list of the weeks
     */
    ListView weekdays;
    /**
     * refernces on the Current Day
     */
    TextView todayDate;
    /**
     * references on the Drawer
     */
    private DrawerLayout mDrawerLayout;
    /**
     * is a Toggle ,on i press on it the DraweLayout will appear
     */
    private ActionBarDrawerToggle mToggle;

    /**
     * to Add a new Bullet
     */

    /**
     * is an Object of the ExampleDBHelper Class
     */
    ExampleDBHelper data=new ExampleDBHelper(this);
    /**
     * to go to Monthly Log
     */
    Button monthlyLog;
    /**
     * to create the Form of the Days'date
     */
    SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
    /**
     * an object of dailyLogClass
     */
    DailyLog dailyLogClass=new DailyLog();

    Dataimport dataimport = new Dataimport();
    Intent intent = getIntent();

   // String user_id = intent.getStringExtra("userid");



   static ArrayList<ArrayList<String>> bullets;


   static HashMap<String,ArrayList<String>> map=new HashMap<>();

   static ArrayAdapter<String> arrayAdapter;

   Button button;
   static int num;
   static String day11;

   static ArrayList<String>zusatz;

   Button futureLog;
   Button calendar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_log);



        //to navigate in the Drawer
        mDrawerLayout = findViewById(R.id.drawer);
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        NavigationView navigationView= findViewById(R.id.navView);
        navigationView.setNavigationItemSelectedListener(this);
        //End



        //call the current Week and add its Days to ArrayList(Days)
        weekdays=findViewById(R.id.weekList);
        weeks();
        //End



        //to get the Data from DataBase
        data=new ExampleDBHelper(getApplicationContext());
        //End



        //to go to The Next Week
        ForwardB=findViewById(R.id.ForwardButton);
        ForwardB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    dailyLogClass.addDays(6,1);
                    getBullets();
                } catch (ParseException e) {

                    e.printStackTrace();
                }
            }
        });
        //End



        //to go to the Prevoius Week
        BackwardB=findViewById(R.id.BackwordButton);
        BackwardB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    dailyLogClass.addDays(0,-7);
                    getBullets();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });
        //End






        //to go to the Monthly Log
        monthlyLog=findViewById(R.id.goToMonthlyLogFromDailyLog);
        monthlyLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveToMonthlyLogActivity();
            }
        });
        //End



        futureLog=findViewById(R.id.goToFutureLogFromDailyLog);
        futureLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToFutureLog();
            }
        });

        calendar=findViewById(R.id.goToCalendarFromDailyLog);
        calendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToCalendar();
            }
        });

    }//End of the Method



    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId())
        {
            case R.id.settings:
                moveToSettingsActivity();
        }
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    /**
     * retrieve the days of the current week
     * and then convert them to string, so we can print them out
     * and for every day we get the data from the database and for
     * every day we create an arrayadapter and add it to the arrayList of arrayadapter
     * and then we pass the ArrayList to the class CustomAdapter,so we can
     * print out the information for every day.
     */
    public void weeks() {
        Calendar cal = Calendar.getInstance();
        String currentDate = DateFormat.getDateInstance().format(cal.getTime());
        todayDate = findViewById(R.id.date);
        todayDate.setText(currentDate);
        //to avoid appearing the days of the cureent week when this method get called from the Monthly Log
        if(days.isEmpty()) {
           dailyLogClass.getWeekDays();
        }
        getBullets();
    }



    /**
     * assign the ArrayList(bullets) all the data for the days,which are found in
     * the (days) ArrayList.
     * and then will create an instance of the Class(CustomAdapter)
     * and at the End will call the method(setAdapter).
     */
    public void getBullets()
    {
        //Decleartion
        ArrayList<ArrayAdapter<String>> arrayAdapterToEveryDay;


        //Assign
        bullets = dataimport.getData(days);
        arrayAdapterToEveryDay = createArrayAdapterToEveryDay(bullets);
        arrayAdapter = new CustomAdapter(this, days, arrayAdapterToEveryDay);
        weekdays.setAdapter(arrayAdapter);

    }


    /**
     * create an ArrayList of arrayadapter and we add for every
     * single day an Arrayadapter and in that way we make sure that all the Data
     * for all days will be printed out.
     */
    public ArrayList<ArrayAdapter<String>> createArrayAdapterToEveryDay(ArrayList<ArrayList<String>> bullets)
    {
        ArrayList<ArrayAdapter<String>> listOfArrayAdapters=new ArrayList<>();
        for(int i=0;i<7;i++) {
            listOfArrayAdapters.add(new ArrayAdapter(this, android.R.layout.simple_list_item_1, bullets.get(i)));}
        return listOfArrayAdapters;
    }


    /**
     * Take us to newBullet Activity
     */
    public  void moveToNewBulletActivity()
    {

        Intent intent=new Intent(this,New_Bullet.class);
        startActivity(intent);
    }

    /**
     * Take us to the MonthlyLog Activity
     */
    private void moveToMonthlyLogActivity()
    {
        Intent intent=new Intent(this,MonthlyLog.class);
        startActivity(intent);
    }


    /**
     * Take us to the Settings Activity
     */
    public void  moveToSettingsActivity(){

        Intent intent=new Intent(this,Settings.class);
        startActivity(intent);
    }



    public void goToFutureLog(){
        Intent intent=new Intent(this,FutureLogActivity.class);
        startActivity(intent);
    }


    public void goToCalendar(){
        Intent intent=new Intent(this,CalendarActivity.class);
        startActivity(intent);
    }

















}//End of the Class


