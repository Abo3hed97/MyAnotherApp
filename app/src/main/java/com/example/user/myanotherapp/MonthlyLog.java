package com.example.user.myanotherapp;

import android.app.Activity;
import android.content.Intent;
import android.icu.util.LocaleData;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.text.DateFormat;
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
        l.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                try {
                    getWeek(position);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                changeToDailyLog();

            }
        });

    }









    private ArrayList<String> getAllMonthdays() throws ParseException {



        ArrayList<String> daysofM = new ArrayList<>();
        Calendar now = Calendar.getInstance();
        // Assuming that you already have this.
        int year =Calendar.YEAR;
        int month =(Calendar.MONTH);
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
        int month =(Calendar.MONTH);
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




    public void getWeek(int dayNumber) throws ParseException {
        ArrayList<String> days = DailyLogActivity.days;
        DateFormat formatter;
        formatter = new SimpleDateFormat("dd.MM.yyyy");
        Date date = (Date) formatter.parse(days.get(0));
        Calendar calendar1 = Calendar.getInstance();
        calendar1.set(Calendar.DATE, dayNumber);
        Calendar calendar2 = Calendar.getInstance();
        calendar2.setTime(date);
        Calendar calendar3 = Calendar.getInstance();
        calendar3.setTime(date);
        if(calendar1.before(calendar2)) {
            calendar3.add(Calendar.DATE, -7);
            while (calendar1.before(calendar2)) {

                if (calendar1.after(calendar3)) {
                    getData(calendar3);
                    break;
                } else {
                    calendar2.add(Calendar.DATE, -7);
                    calendar3.add(Calendar.DATE, -7);
                }
            }
        }
        if(calendar1.after(calendar2)) {
            calendar3.add(Calendar.DATE, 7);
            while (calendar1.after(calendar2)) {
                if (calendar1.before(calendar3)) {
                    getData(calendar2);
                    break;
                } else {
                    calendar2.add(Calendar.DATE, 7);
                    calendar3.add(Calendar.DATE, 7);
                }
            }
        }
    }


    public void getData(Calendar calendar2){
        String currentDate;
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        currentDate= sdf.format(calendar2.getTime());
        DailyLogActivity.days.clear();
        DailyLogActivity.days.add(currentDate);
        for(int i=1;i<7;i++)
        {
            calendar2.add(Calendar.DATE,1);
            currentDate= sdf.format(calendar2.getTime());
            DailyLogActivity.days.add(currentDate);
        }
    }

    public void changeToDailyLog(){
        Intent intent=new Intent(this,DailyLogActivity.class);
        startActivity(intent);
    }






}
