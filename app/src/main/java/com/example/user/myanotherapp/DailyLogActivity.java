package com.example.user.myanotherapp;

import android.content.Intent;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static java.text.DateFormat.FULL;

public class DailyLogActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    /**
     * is the button on i press on it i go to week forther
     */
    Button ForwardB;
    /**
     * is the button on i press on it i go to week before
     */
    Button BackwardB;
    /**
     * is an Calendar instance
     */
    Calendar calendar;
    /**
     * is an ArrayList contains the days of every Week
     */
   public static ArrayList<String> days = new ArrayList<>();
    /**
     * is an istance of the Class (ListView), which references on the list of the weeks
     */
    ListView weekdays;
    /**
     * is an TextView instance, which refernces on the Current Day
     */
    TextView textViewDate;
    /**
     * is an DrawerLayout instance,which references on the Drawer
     */
    private DrawerLayout mDrawerLayout;
    /**
     * is a Toggle ,on i press on it the DraweLayout will appear
     */
    private ActionBarDrawerToggle mToggle;

    /**
     * is a Button on we click on it we get taken to the
     * addBullet Activity
     */
    private Button toGoAddB;

    /**
     * is an Single CustomAdapter Object
     */
    CustomAdapter ca;


    /**
     * is an ArrayList of Strings to store all the data of all Days
     */
    ArrayList<ArrayList<String>>retrieve=new ArrayList<>();

    /**
     * is an Object of the ExampleDBHelper Class
     */
    ExampleDBHelper edbh;



    Button gotoMonthly;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_log);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toGoAddB=(Button)findViewById(R.id.btogotoAddBullet);
        toGoAddB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeActoNewB();
            }
        });

        edbh=new ExampleDBHelper(getApplicationContext());







        NavigationView navigationView=(NavigationView)findViewById(R.id.navView);
        navigationView.setNavigationItemSelectedListener(this);





        weekDays();


        ForwardB = (Button) findViewById(R.id.ForwardButton);
        ForwardB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                try {
                    forwards();
                } catch (ParseException e) {
                    e.printStackTrace();
                }


            }
        });


        BackwardB = (Button) findViewById(R.id.BackwordButton);
        BackwardB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Backwards(calendar);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

            }
        });


        gotoMonthly=(Button)findViewById(R.id.goToMothlyLog);
        gotoMonthly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeToMonthlyLog();
            }
        });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId())
        {
            case R.id.settings:
                change();
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


    public void weekDays() {


        weeks();

    }


    /**
     * when the User click on the forwardsButton it will be lead
     * to the next week from the current week
     * @throws ParseException
     */
    public void forwards() throws ParseException {
      addDays(6,1);
    }


    /**
     * when the User click on the backword button it
     * will be lead to the prevoius week from the current week
     * @param lastDay
     * @throws ParseException
     *
     */
    public void Backwards(Calendar lastDay) throws ParseException {
       addDays(0,-7);
    }




    /**
     * with help of this method we can retrieve the days of the current week
     * and then convert them to string, so we can print them out
     * and for every day we get the data from the database and for
     * every day we create an arrayadapter and add it to the arrayList of arrayadapter
     * and then we pass the ArrayList to the class CustomAdapter,so we can
     * print out the information for every day.
     */
    public void weeks() {
        Calendar cal = Calendar.getInstance();
        String currentDate = DateFormat.getDateInstance().format(cal.getTime());

        textViewDate = findViewById(R.id.date);
        textViewDate.setText(currentDate);
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        for (int i = Calendar.SUNDAY; i <= Calendar.SATURDAY; i++) {
            cal.set(Calendar.DAY_OF_WEEK, i);
            currentDate = sdf.format(cal.getTime());
            days.add(currentDate);
        }
        weekdays = (ListView) findViewById(R.id.weekList);
        setArrayAdapter();
    }


    /**
     * is a method to take us to the Settings Activity on we click on it.
     */
    public void  change(){

        Intent intent=new Intent(this,Settings.class);
        startActivity(intent);
    }

    /**
     * is an is a method to take us to the NewBullet Activity when we call it.
     */
    public void changeActoNewB()
    {
        Intent intent=new Intent(this,New_Bullet.class);
        startActivity(intent);
    }



   /**
    * with help of this method we create an ArrayList of arrayadapter and we add for every
    * single day an Arrayadapter and in that way we make sure that all the Data
    * for all days will be printed out.
    */
    public ArrayList<ArrayAdapter<String>> getArrayAd(ArrayList<ArrayList<String>> d)
    {
        ArrayList<ArrayAdapter<String>> listOfArrayAdapter=new ArrayList<>();
        for(int i=0;i<7;i++) {
            listOfArrayAdapter.add(new ArrayAdapter(this, android.R.layout.simple_list_item_1, d.get(i)));
        }
        return listOfArrayAdapter;
    }


    /**
     * this method will assign the ArrayList(retrieve) all the data for the days,which are found in
     * the (days) ArrayList.
     * and then will create an instance of the Class(CustomAdapter)
     * and at the End will call the method(setAdapter).
     */
    public void setArrayAdapter()
    {
        retrieve=edbh.getData(days);
        ArrayList<ArrayAdapter<String>> h= getArrayAd(retrieve);
        ArrayAdapter<String> arrayAdapter = new CustomAdapter(this, days,h);
        weekdays.setAdapter(arrayAdapter);

    }





    public void addDays(int day,int anotherDay) throws ParseException {
        DateFormat formatter ;
        formatter= new SimpleDateFormat("dd.MM.yyyy");
        Date date=(Date)formatter.parse(days.get(day));
        Calendar cal=Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE,anotherDay);
        cal.add(Calendar.MONTH, 0);
        cal.add(Calendar.YEAR, 0);
        String currentDate = formatter.format(cal.getTime());
        days.clear();
        days.add(currentDate);


        for (int i = 1; i < 7; i++) {
            cal.add(Calendar.DATE, 1);
            cal.add(Calendar.MONTH, 0);
            cal.add(Calendar.YEAR, 0);
            String future = formatter.format(cal.getTime());
            days.add(future);
        }


        setArrayAdapter();

    }



    private void changeToMonthlyLog()
    {
        Intent intent=new Intent(this,MonthlyLog.class);
        startActivity(intent);
    }





}//End of the Class


