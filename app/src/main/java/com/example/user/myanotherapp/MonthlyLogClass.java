package com.example.user.myanotherapp;

import android.widget.ArrayAdapter;

import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MonthlyLogClass {


    /**
     * Three Instances of the Class Calendar
     */
    Calendar calendar1, calendar2, calendar3;



    /**
     *delete the Current Content of ArrayList(daysofMonth) and
     * then add the Days of the navigated Month
     * @param j
     * @throws ParseException
     */
    public void addDaysofNavigatedMonth(int j) throws ParseException {
        if(!MonthlyLog.daysofMonth.isEmpty()) {
            MonthlyLog.daysofMonth.clear();
        }
        int year =Calendar.getInstance().get(Calendar.YEAR);
        MonthlyLog.month += j;

        for (int i = 1; i <getNumberofdaysinMonth()+1; i++) {
            // First convert to Date. This is one of the many ways.
            String dateString = String.format("%d-%d-%d", year, MonthlyLog.month, i);
            Date date = new SimpleDateFormat("yyyy-M-d").parse(dateString);
            // Then get the day of week from the Date based on specific locale.
            String dayOfWeek = new SimpleDateFormat("EEEE", Locale.ENGLISH).format(date);
            MonthlyLog.daysofMonth.add(i+"."+dayOfWeek);
        }

    }


    /**
     * With Help of this function,we can get the Number of days of current Month
     * and for achiving that we need use the Function (getActualMaximum)
     *
     * @return number of Days of Current Month.
     */
    public int getNumberofdaysinMonth() {
        // calendar's instance.
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int date = 1;
        calendar.set(year, MonthlyLog.month - 1, date);
        //get the maximum Number of days in a Month.
        int days = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        return days;
    }




    /**
     * With help of this function,we can get the Daily Log of the selected Day in monthly Log
     * and this can be achivied with Help of three Days.
     *
     * @param selectedDay is the Selected Day of MonthlyLog.
     * @throws ParseException when anything gets wrong during the Parsing Proecss.
     */
    public void navigateToSearchedDailyLog(int selectedDay) throws ParseException {
        DateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
        Date date1 = formatter.parse(DailyLogActivity.days.get(0));
        assignToInstance();
        calendar1.set(Calendar.DATE, selectedDay);
        Date date2 = new SimpleDateFormat("MMMM").parse(MonthlyLog.mothName.getText().toString());
        Calendar cal = Calendar.getInstance();
        cal.setTime(date2);
        calendar1.set(Calendar.MONTH, cal.get(Calendar.MONTH));
        calendar2.setTime(date1);
        calendar3.setTime(date1);
        if (calendar1.before(calendar2)) {
            backwordUnitlfindWeek();
            return;
        }
        if (calendar1.after(calendar2)) {
            forwardUnitlFindWeek();
        }

    }


    /**
     * We assign the Calendar object to Calendar instance.
     */
    public void assignToInstance() {
        calendar1 = Calendar.getInstance();
        calendar2 = Calendar.getInstance();
        calendar3 = Calendar.getInstance();
    }


    /**
     * The Calendar1 is the Day before calendar2,such that the calendar 2 is the First Days of
     * The ArrayList(days) and the Calendar1 is the Day before Calendar 2.
     * we keep searching backword until the selected Day is between the Calendar2 and Calendar3,such that Calendar2 is after
     * Calendar1 and calendar3 is before Calendar1.
     */
    public void backwordUnitlfindWeek() {

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
    public void forwardUnitlFindWeek() {

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
     * With help of the function we clear the ArrayListr (days) and then
     * assign to it the days of
     * the Week of the Selected Day
     *
     * @param calendar the first Day of the specified Daily Log.
     */
    public void getSpecifiedDailLog(Calendar calendar) {
        String currentDate = convertFromCalToStr(calendar);
        DailyLogActivity.days.clear();
        DailyLogActivity.days.add(currentDate);
        for (int i = 1; i < 7; i++) {
            calendar.add(Calendar.DATE, 1);
            currentDate = convertFromCalToStr(calendar);
            DailyLogActivity.days.add(currentDate);
        }
    }


    /**
     * With Help of this Function,we can convert from Calendar to String
     *
     * @param day the day,wich needs to be converted to String
     * @return the day after it got converted to String.
     */
    public String convertFromCalToStr(Calendar day) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        return sdf.format(day.getTime());

    }







    /**
     * With Help of this function we can get the Name of the Current Month
     * and we use the Class (DateFormatSymbols) to achive that.
     *
     * @param num this is the Number of the Current Day.
     * @return the Name of Current Month.
     */
    public String getMonthForInt(int num) {
        String month = "";
        //An Onbject with it's Help we get an Array of Months of the Year.
        DateFormatSymbols dfs = new DateFormatSymbols();
        // Array to Store all the Monhts of the Year.
        String[] months = dfs.getMonths();
        //chack if the Number of Month greater or equel than 0 and lesser equel than 11
        if (num >= 0 && num <= 11) {
            month = months[num];
        }

        //return the asked Month.
        return month;
    }



    /**
     * add the Days in numeric Form
     */
    public void getDaysInInteger(int month) {

        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        Calendar cal=Calendar.getInstance();
        int n=-cal.get(Calendar.DAY_OF_MONTH);
        cal.add(Calendar.DATE, n+1);
        cal.add(Calendar.MONTH,month);
        String currentDate = sdf.format(cal.getTime());
        MonthlyLog.dBMonthDays.add(currentDate);
        for (int i = 1; i < getNumberofdaysinMonth() + 1; i++) {
            cal.add(Calendar.DATE, 1);
            currentDate = sdf.format(cal.getTime());
            MonthlyLog.dBMonthDays.add(currentDate);
        }


    }



























}