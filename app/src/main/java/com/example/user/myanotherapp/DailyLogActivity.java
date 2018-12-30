package com.example.user.myanotherapp;

import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import static java.text.DateFormat.FULL;

public class DailyLogActivity extends AppCompatActivity {
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

        Calendar calender=Calendar.getInstance();
        //Date today= calender.getTime();
        String currentDate = DateFormat.getDateInstance().format(calender.getTime());
        TextView  textViewDate=findViewById(R.id.date);
        textViewDate.setText(currentDate);

        ListView weekdays=(ListView) findViewById(R.id.weekList);
        ArrayList<String> days=new ArrayList<String>();
        for(int i=1;i<7;i++)
        {
            calender.add(Calendar.DATE,1);
            calender.add(Calendar.MONTH,0);
            calender.add(Calendar.YEAR,0);

            String future=DateFormat.getDateInstance().format(calender.getTime());
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











}
