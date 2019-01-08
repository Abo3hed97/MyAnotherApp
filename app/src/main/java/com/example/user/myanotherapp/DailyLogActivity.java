package com.example.user.myanotherapp;

import android.content.Intent;
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
    ArrayList<String> days = new ArrayList<>();
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




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_log);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);







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











         /*
        calender.add(Calendar.DATE,1);
        calender.add(Calendar.MONTH,0);
        calender.add(Calendar.YEAR,0);
        //Date future =calender.getTime();
        String future=DateFormat.getDateInstance().format(calender.getTime());
        textViewDate.setText("Today: "+currentDate+"\n" + "Future: "+future);*/


    public void forwards() throws ParseException {
        DateFormat formatter ;
        formatter= new SimpleDateFormat("dd.MM.yyyy");
        Date date=(Date)formatter.parse(days.get(6));
        Calendar cal=Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE,1);
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

        ArrayAdapter<String> arrayAdapter = new CustomAdapter(this, days);
        weekdays.setAdapter(arrayAdapter);
    }


    public void Backwards(Calendar lastDay) throws ParseException {
        DateFormat formatter ;
        formatter= new SimpleDateFormat("dd.MM.yyyy");
        Date date=(Date)formatter.parse(days.get(0));
        Calendar cal=Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE,-7);
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

        ArrayAdapter<String> arrayAdapter = new CustomAdapter(this, days);
        weekdays.setAdapter(arrayAdapter);
    }


    public void changeActivity() {

        Intent intent1 = new Intent(this, this.getClass());
        startActivity(intent1);
    }


    public void weeks() {
        Calendar cal = Calendar.getInstance();
        String currentDate = DateFormat.getDateInstance().format(cal.getTime());

        textViewDate = findViewById(R.id.date);
        textViewDate.setText(currentDate);
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        // cal.set(Calendar.WEEK_OF_MONTH,2);
        for (int i = Calendar.SUNDAY; i <= Calendar.SATURDAY; i++) {
            cal.set(Calendar.DAY_OF_WEEK, i);
            currentDate = sdf.format(cal.getTime());
            days.add(currentDate);
        }
        weekdays = (ListView) findViewById(R.id.weekList);
        // ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, days);
        ArrayAdapter<String> arrayAdapter = new CustomAdapter(this, days);
        weekdays.setAdapter(arrayAdapter);

    }




    public void  change(){

        Intent intent=new Intent(this,Settings.class);
        startActivity(intent);
    }




}



