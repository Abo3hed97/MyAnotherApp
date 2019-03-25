package com.example.user.myanotherapp;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.myanotherapp.Mysql.Dataimport;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

public class MonthlyLog extends AppCompatActivity implements navigate {
    //Animation button monthly log
    Button button3;
    Animation anim;
    /**
     * With this ListView we can print out the days of the specified Month
     */
    ListView monthdaysListView;


    ArrayAdapter adapter;


    /**
     * contains the Days of Month
     */
    static ArrayList<String> daysofMonth=new ArrayList<>();
    /**
     * reference on the ArrayList(days)
     */
    ArrayList<String> days = DailyLogActivity.days;
    /**
     * Three Instances of the Class Calendar
     */
    Calendar calendar1,calendar2,calendar3;
    /**
     * navigate the User to the next Month
     */
    Button nextMonthButton;

    /**
     * navigate the User to the previous Month
     */
    Button previuosMonthButton;

    /**
     * is an Object of the ExampleDBHelper Class
     */
    ExampleDBHelper  dBHelper=new ExampleDBHelper(this);


    static int month;


    /**
     * Name of the Current Month
     */
   static TextView mothName;

    /**
     * Number of the Current Month
     */
    int  monthNumber;


    public static ArrayList<String> dBMonthDays=new ArrayList<>();

    MonthlyLogClass mlc=new MonthlyLogClass();


    Button futureLog;
    static TextView firstImp;


    Dataimport dataimport = new Dataimport();

   static int year=Calendar.getInstance().get(Calendar.YEAR);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monthly_log);
        button3=(Button) findViewById(R.id.button3);
        anim= AnimationUtils.loadAnimation(this,R.anim.tran3);
        button3.setAnimation(anim);

         if(month==0){
             month=Calendar.getInstance().get(Calendar.MONTH) + 1;
         }

        //print the Current Month's Name
        monthdaysListView= findViewById(R.id.listofdays);
        mothName= findViewById(R.id.DLMN);

       // monthNumber=Calendar.getInstance().get(Calendar.MONTH);
        String currentMonth;
        currentMonth = monthName(month).concat("-").concat(String.valueOf(year));
        mothName.setText(currentMonth);
        //End








        //print out the Days of the Current Month and their important Bullets
        try {
            mlc.addDaysofNavigatedMonth(0);
            getImportantBullets();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        //End








        //Navigate to the searched Daily Log
            monthdaysListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                try {
                    mlc.navigateToSearchedDailyLog(position,getMonthNumberBasedOnMonthName(monthName(month)),year);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                navigateToDailyLog();
            }
        });
        //End













        //Navigate to next Month
        nextMonthButton= findViewById(R.id.nextMonth);
        nextMonthButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    forwards();
                    getImportantBullets();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });
        //End





        //Navigate to previous Month
        previuosMonthButton= findViewById(R.id.prevoiusMonth);
        previuosMonthButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Backwards();
                    getImportantBullets();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });
        //End



        //navigate to Future Log
        futureLog=findViewById(R.id.goToFutureLogFromMonthlyLog);
        futureLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToFutureLog();
                month=0;
            }
        });






    }//Ed












    /**
     * print out the next Month's Days and
     * it's Name
     * @throws ParseException
     */
    @Override
    public void forwards() throws ParseException {
        mlc.addDaysofNavigatedMonth(1);
            if(month==13){
                year++;
                month=1;
                String cuurentMonth=monthName(month).concat("-").concat(String.valueOf(year));
                mothName.setText(cuurentMonth);
            }
          else{
                String cuurentMonth=monthName(month).concat("-").concat(String.valueOf(year));
                mothName.setText(cuurentMonth);
            }

    }



    /**
     * print out the previous Month's Days and
     * it's Name
     * @throws ParseException
     */
    @Override
    public void Backwards() throws ParseException {
        mlc.addDaysofNavigatedMonth(-1);
        if(month==0){
            year--;
            month=12;
            Log.i(String.valueOf(month),String.valueOf(year));
            String currentMonth=monthName(month).concat("-").concat(String.valueOf(year));
            mothName.setText(currentMonth);
        }
        else{
            String currentMonth=monthName(month).concat("-").concat(String.valueOf(year));
            mothName.setText(currentMonth);
        }


        /* if(month-1==0){
             year--;
             String currentMonth=mlc.getMonthForInt(monthNumber).concat("-").concat(String.valueOf(year));
        }*/
      //  String currentMonth=mlc.getMonthForInt(monthNumber).concat("-").concat(String.valueOf(year));
       // mothName.setText(currentMonth);
    }






    /**
     * navigate to Monthly Task
     * @param view
     */
    public void navigateToMonthlyTask(View view) {
        Intent intent = new Intent(this, MonthlyTasks.class);
        startActivity(intent);
    }





    /**
     * navigate to the DailyLog.
     */
    public void navigateToDailyLog(){
        Intent intent=new Intent(this,DailyLogActivity.class);
        startActivity(intent);
    }



    /**
     * navigate to the Future Log.
     */
    public void navigateToFutureLog(){
        Intent intent=new Intent(this,FutureLogActivity.class);
        startActivity(intent);
    }



    public void getImportantBullets() throws ParseException {

        ArrayList<ArrayList<String>> importantBullets;
        ArrayAdapter<String>AdapterforImportantBullets;
        if(!dBMonthDays.isEmpty()){dBMonthDays.clear();}
        mlc.getDaysInInteger();
        importantBullets=dataimport.getImportantBullets(dBMonthDays);
        AdapterforImportantBullets=new CustomAdapterMonth(this,daysofMonth,importantBullets);
        monthdaysListView.setAdapter(AdapterforImportantBullets);

    }


    public String monthName(int i){
    String months1[]=new String[13];
    months1 = new String[]{"","January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
    return months1[i];
    }








    public void monthlyTask(View view) {
        Intent intent=new Intent(this,MonthlyTasks.class);
        startActivity(intent);

    }



    public int getMonthNumberBasedOnMonthName(String monthName){
            String months1[]=new String[13];
            months1 = new String[]{"","January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
            return Arrays.asList(months1).indexOf(monthName);
    }
}// End of the Class

