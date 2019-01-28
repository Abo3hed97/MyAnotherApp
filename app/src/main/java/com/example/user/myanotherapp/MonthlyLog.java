package com.example.user.myanotherapp;

import android.app.Activity;
import android.content.Intent;
import android.icu.util.LocaleData;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
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

public class MonthlyLog extends AppCompatActivity implements navigate {


    /**
     * With this Listview we can print out the days of the specified Month
     */
    ListView monthdaysListView;
    ArrayAdapter aad;

    ArrayList<String> daysofM;
    /**
     * we reference on the ArrayList of the days
     */
    ArrayList<String> days = DailyLogActivity.days;
    /**
     * Three Instances of the Class Claendar
     */
    Calendar calendar1,calendar2,calendar3;
    /**
     * a Button,when it get clicked, the User will navigated to the next Month.
     */
    Button nextMonthButton;

    /**
     * a Button,when it get clicked, the User will be navigated to the prevoius Month.
     */
    Button previuosMonthButton;

    int month;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monthly_log);

        monthdaysListView=(ListView)findViewById(R.id.listofdays);
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
        aad=new CustomAdapterMonth(this,dom);
        monthdaysListView.setAdapter(aad);
        monthdaysListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                try {
                    getSpecifiedWeek(position);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                changeToDailyLog();

            }
        });



        nextMonthButton=(Button)findViewById(R.id.nextMonth);
        nextMonthButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    forwards();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });
        previuosMonthButton=(Button)findViewById(R.id.prevoiusMonth);
        previuosMonthButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Backwards();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });








    }


    /**
     * With this Function we have the Ability to get all the current month's Days
     * this would be achived,when we get the First Day of the Current Month
     * and Then with help of forLoop we can get the other Days.
     * @return a List with all the Days of the Current Month pouplted in it.
     * @throws ParseException if anything gets Wrong during the Parsing Proess.
     */
    private ArrayList<String> getAllMonthdays() throws ParseException {
        Calendar c=Calendar.getInstance();
        //to save the Days of current Month
         daysofM = new ArrayList<>();
        // Assuming that you already have this.
        int year=Calendar.YEAR;
        month =c.get(Calendar.MONTH)+1;
        for (int i = 1; i < getNumberofdaysinMonth()+1; i++) {
            // First convert to Date. This is one of the many ways.
            String dateString = String.format("%d-%d-%d", c.get(Calendar.YEAR), month, i);
            Date date = new SimpleDateFormat("yyyy-M-d").parse(dateString);
            // Then get the day of week from the Date based on specific locale.
            String dayOfWeek = new SimpleDateFormat("EEEE", Locale.ENGLISH).format(date);
            daysofM.add(i+"."+dayOfWeek);
        }
        return daysofM;
    }


    /**
     * With Help of this function,we can get the Number of days of current Month
     * and for achiving that we need use the Function (getActualMaximum)
     * @return number of Days of Current Month.
     */
    public int getNumberofdaysinMonth()
    {
        // calendar's instance.
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int date = 1;
        calendar.set(year, month-1, date);
        //get the maximum Number of days in a Month.
        int days = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        return days;
    }


    /**
     * With Help of this function we can get the Name of the Current Month
     * and we use the Class (DateFormatSymbols) to achive that.
     * @param num this is the Number of the Current Day.
     * @return the Name of Current Month.
     */
    String getMonthForInt(int num) {
        String month = "";
        //An Onbject with it's Help we get an Array of Months of the Year.
        DateFormatSymbols dfs = new DateFormatSymbols();
        // Array to Store all the Monhts of the Year.
        String[] months = dfs.getMonths();
        //chack if the Number of Month greater or equel than 0 and lesser equel than 11
        if (num >= 0 && num <= 11 ) {
            month = months[num];
        }

        //return the asked Month.
        return month;
    }


    /**
     * With help of this function,we can get the Daily Log of the selected Day in monthly Log
     * and this can be achivied with Help of three Days.
     * @param selectedDay is the Selected Day of MonthlyLog.
     * @throws ParseException when anything gets wrong during the Parsing Proecss.
     */
    public void getSpecifiedWeek(int selectedDay) throws ParseException {
        DateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
        Date date = (Date) formatter.parse(days.get(0));
        assignToInstance();
        calendar1.set(Calendar.DATE, selectedDay);
        calendar2.setTime(date);
        calendar3.setTime(date);
        if(calendar1.before(calendar2)) {
           backwordUnitlfindWeek();
        }
        if(calendar1.after(calendar2)) {
           forwardUnitlFindWeek();
        }

    }

    /**
     * With help of the function we clear the ArrayListr (days) and then
     * assign to it the days of
     * the Week of the Selected Day
     * @param calendar the first Day of the specified Daily Log.
     */
    public void getSpecifiedDailLog(Calendar calendar){
        String currentDate=convertFromCalToStr(calendar);
        DailyLogActivity.days.clear();
        DailyLogActivity.days.add(currentDate);
        for(int i=1;i<7;i++)
        {
            calendar.add(Calendar.DATE,1);
            currentDate= convertFromCalToStr(calendar);
            DailyLogActivity.days.add(currentDate);
        }
    }

    /**
     * with Help of this Function we can navigate to the DailyLog.
     */
    public void changeToDailyLog(){
        Intent intent=new Intent(this,DailyLogActivity.class);
        startActivity(intent);
    }


    /**
     * The Calendar1 is the Day before calendar2,such that the calendar 2 is the First Days of
     * The ArrayList(days) and the Calendar1 is the Day before Calendar 2.
     * we keep searching backword until the selected Day is between the Calendar2 and Calendar3,such that Calendar2 is after
     * Calendar1 and calendar3 is before Calendar1.
     */
    public void backwordUnitlfindWeek(){

        calendar3.add(Calendar.DATE, -7);
        while (calendar1.before(calendar2)) {
            if (calendar1.after(calendar3)) {
                getSpecifiedDailLog(calendar3);
                break;
            } else {
                    calendar2.add(Calendar.DATE, -7);
                    calendar3.add(Calendar.DATE, -7);
                }
            }
    }


    /**
     * The Calendar1 is the Day after calendar2,such that the calendar 2 is the First Days of
     * The ArrayList(days) and the Calendar1 is the Day after Calendar 2.
     * we keep searching forward until the selected Day is between the Calendar2 and Calendar3,such that Calendar2 is before
     * Calendar1 and calendar3 is after Calendar1.
     */
    public void forwardUnitlFindWeek(){

        calendar3.add(Calendar.DATE, 7);
        while (calendar1.after(calendar2)) {
            if (calendar1.before(calendar3)) {
                getSpecifiedDailLog(calendar2);
                break;
            } else {
                calendar2.add(Calendar.DATE, 7);
                calendar3.add(Calendar.DATE, 7);
            }
        }

    }

    /**
     * With Help of this Function,we can convert from Calendar to String
     * @param day the day,wich needs to be converted to String
     * @return the day after it got converted to String.
     */
    public String convertFromCalToStr(Calendar day)
    {
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        return sdf.format(day.getTime());

    }

    /**
     * We assign the Calendar object to Calendar instance.
     */
    public void assignToInstance()
    {
        calendar1=Calendar.getInstance();
        calendar2=Calendar.getInstance();
        calendar3=Calendar.getInstance();
    }


    @Override
    public void forwards() throws ParseException {
            addDays(1);
    }

    @Override
    public void Backwards() throws ParseException {
        addDays(-1);
    }






    public void addDays(int j) throws ParseException {
        daysofM.clear();
        Calendar c=Calendar.getInstance();
        int year =c.get(Calendar.YEAR);
        month += j;




        for (int i = 1; i < getNumberofdaysinMonth()+1; i++) {
            // First convert to Date. This is one of the many ways.
            String dateString = String.format("%d-%d-%d", year, month, i);
            Date date = new SimpleDateFormat("yyyy-M-d").parse(dateString);
            // Then get the day of week from the Date based on specific locale.
            String dayOfWeek = new SimpleDateFormat("EEEE", Locale.ENGLISH).format(date);
            daysofM.add(i+"."+dayOfWeek);
        }

        monthdaysListView.setAdapter(aad);
    }


















}// End of the Class

