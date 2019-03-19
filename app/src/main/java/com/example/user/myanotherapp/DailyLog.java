package com.example.user.myanotherapp;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class DailyLog  {


  SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");


  public void getWeekDays()
  {
    Calendar cal = Calendar.getInstance();
    String currentDate="";
    for (int weekDay = Calendar.SUNDAY; weekDay <= Calendar.SATURDAY; weekDay++) {
      cal.set(Calendar.DAY_OF_WEEK, weekDay);
      currentDate = sdf.format(cal.getTime());
      DailyLogActivity.days.add(currentDate);
    }

  }



  /**
   * Help to get the First Day of the New Week
   * @param day
   * @param anotherDay
   * @return the First Day of the Week
   * @throws ParseException
   */
  public Calendar getFirstDay(int day,int anotherDay) throws ParseException {
    Date date= sdf.parse(DailyLogActivity.days.get(day));
    Calendar cal=Calendar.getInstance();
    cal.setTime(date);
    cal.add(Calendar.DATE,anotherDay);
    return cal;
  }










  public void addDays(int day,int anotherDay) throws ParseException {
    Calendar calendar=getFirstDay(day,anotherDay);
    //get the first Day of the week
    String currentDate = sdf.format(calendar.getTime());
    //we need to clear the ArrayList,sothat we can store the new Week in it.
    DailyLogActivity.days.clear();
    DailyLogActivity.days.add(currentDate);
    //to iterate on the days of the new Week and add them in the ArrayList(days)
    for (int i = 1; i < 7; i++) {
      calendar.add(Calendar.DATE, 1);
      String future = sdf.format(calendar.getTime());
      DailyLogActivity.days.add(future);
    }
  }
















}
