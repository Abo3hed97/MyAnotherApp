package com.example.user.myanotherapp;

import android.app.Activity;
import android.icu.util.LocaleData;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

public class MonthlyLog extends AppCompatActivity {



    ListView l;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monthly_log);

        l=(ListView)findViewById(R.id.listofdays);
        TextView mothName=(TextView) findViewById(R.id.DLMN);

        int monthNumber=Calendar.getInstance().get(Calendar.MONTH);
        String currentMonth=getMonthForInt(monthNumber);
        mothName.setText(currentMonth);

        ArrayList<String>dom=new ArrayList<>();
        try {
            dom=getAllMonthdays();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        ArrayList<String> f=new ArrayList<>();
        ArrayAdapter aad=new CustomAdapterMonth(this,dom);
        l.setAdapter(aad);

    }









    private ArrayList<String> getAllMonthdays() throws ParseException {



        ArrayList<String> daysofM = new ArrayList<>();
        Calendar now = Calendar.getInstance();
        // Assuming that you already have this.
        int year =Calendar.YEAR;
        int month =(Calendar.MONTH)-1;
        int day = 1;


        for (int i = 1; i < getNumberofdaysinMonth()+1; i++) {
            // First convert to Date. This is one of the many ways.
            String dateString = String.format("%d-%d-%d", year, month, i);
            Date date = new SimpleDateFormat("yyyy-M-d").parse(dateString);
            // Then get the day of week from the Date based on specific locale.
            String dayOfWeek = new SimpleDateFormat("EEEE", Locale.ENGLISH).format(date);
            daysofM.add(i+"."+dayOfWeek);
        }
        return daysofM;
    }




    public int getNumberofdaysinMonth()
    {

        Calendar calendar = Calendar.getInstance();
        int year = Calendar.YEAR;
        int month =(Calendar.MONTH)-1;
        int date = 1;
        calendar.set(year, month, date);
        int days = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        return days;
    }



    String getMonthForInt(int num) {
        String month = "";
        DateFormatSymbols dfs = new DateFormatSymbols();
        String[] months = dfs.getMonths();
        if (num >= 0 && num <= 11 ) {
            month = months[num];
        }
        return month;
    }

















}
