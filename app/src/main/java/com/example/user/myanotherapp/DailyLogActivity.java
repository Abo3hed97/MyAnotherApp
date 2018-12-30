package com.example.user.myanotherapp;

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
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import static java.text.DateFormat.FULL;

public class DailyLogActivity extends AppCompatActivity {
    Button ForwardB;
    Button BackwardB;
    Calendar calendar;
    ArrayList<String>days;
    ListView weekdays;
    TextView textViewDate;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_log);
        mDrawerLayout=(DrawerLayout)findViewById(R.id.drawer);
        mToggle=new ActionBarDrawerToggle(this,mDrawerLayout,R.string.open,R.string.close);
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        weekDays();

        ForwardB=(Button)findViewById(R.id.ForwardButton);
        ForwardB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar.add(Calendar.DATE,1);
                calendar.add(Calendar.MONTH,0);
                calendar.add(Calendar.YEAR,0);
                forwards();
            }
        });


        BackwardB=(Button)findViewById(R.id.BackwordButton);
        BackwardB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              calendar.add(Calendar.DATE,-13);
              calendar.add(Calendar.MONTH,0);
              calendar.add(Calendar.YEAR,0);
              Backwards(calendar);
            }
        });




        }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(mToggle.onOptionsItemSelected(item))
        {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }



    public void weekDays()
    {

        calendar=Calendar.getInstance();
        //Date today= calender.getTime();
        String currentDate = DateFormat.getDateInstance().format(calendar.getTime());
        textViewDate=findViewById(R.id.date);
        textViewDate.setText(currentDate);

         weekdays=(ListView) findViewById(R.id.weekList);
         days=new ArrayList<String>();
         days.add(currentDate);
        for(int i=1;i<7;i++)
        {
            calendar.add(Calendar.DATE,1);
            calendar.add(Calendar.MONTH,0);
            calendar.add(Calendar.YEAR,0);

            String future=DateFormat.getDateInstance().format(calendar.getTime());
            days.add(future);
        }

        ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,days);
        weekdays.setAdapter(arrayAdapter);






         /*
        calender.add(Calendar.DATE,1);
        calender.add(Calendar.MONTH,0);
        calender.add(Calendar.YEAR,0);
        //Date future =calender.getTime();
        String future=DateFormat.getDateInstance().format(calender.getTime());
        textViewDate.setText("Today: "+currentDate+"\n" + "Future: "+future);*/

         }




         public void forwards()
         {
             String current=DateFormat.getDateInstance().format(calendar.getTime());
             textViewDate.setText(current);
             days.clear();
             days.add(current);
             for(int i=1;i<7;i++)
             {
                 calendar.add(Calendar.DATE,1);
                 calendar.add(Calendar.MONTH,0);
                 calendar.add(Calendar.YEAR,0);
                 String future=DateFormat.getDateInstance().format(calendar.getTime());
                 days.add(future);
             }

             ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,days);
             weekdays.setAdapter(arrayAdapter);

         }


         public void Backwards(Calendar lastDay)
         {
             String current=DateFormat.getDateInstance().format(lastDay.getTime());
             calendar=lastDay;
             textViewDate.setText(current);
             days.clear();
             days.add(current);
             for(int i=1;i<7;i++)
             {
                 calendar.add(Calendar.DATE,1);
                 calendar.add(Calendar.MONTH,0);
                 calendar.add(Calendar.YEAR,0);
                 String future=DateFormat.getDateInstance().format(calendar.getTime());
                 days.add(future);
             }
             ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,days);
             weekdays.setAdapter(arrayAdapter);
         }







}
