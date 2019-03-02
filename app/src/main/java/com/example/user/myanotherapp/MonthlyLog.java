package com.example.user.myanotherapp;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;

public class MonthlyLog extends AppCompatActivity implements navigate {
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monthly_log);

         if(month==0){
             month=Calendar.getInstance().get(Calendar.MONTH) + 1;
         }

        //print the Current Month's Name
        monthdaysListView= findViewById(R.id.listofdays);
        mothName= findViewById(R.id.DLMN);

       // monthNumber=Calendar.getInstance().get(Calendar.MONTH);
        String currentMonth=mlc.getMonthForInt(month-1);
        mothName.setText(currentMonth);
        //End






        //print out the Days of the Current Month and their important Bullets
        try {
            mlc.addDaysofNavigatedMonth(0);
            getImportantBullets(0);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        //End








        //Navigate to the searched Daily Log
            monthdaysListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                try {
                    mlc.navigateToSearchedDailyLog(position);
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
                    getImportantBullets(1);
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
                    getImportantBullets(-1);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });
        //End



        //navigate to Future Log
        futureLog=findViewById(R.id.goToFutureLog);
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
            monthNumber+=1;
            String currentMonth=mlc.getMonthForInt(monthNumber);
            mothName.setText(currentMonth);
    }



    /**
     * print out the previous Month's Days and
     * it's Name
     * @throws ParseException
     */
    @Override
    public void Backwards() throws ParseException {
        mlc.addDaysofNavigatedMonth(-1);
        monthdaysListView.setAdapter(adapter);
        monthNumber-=1;
        String currentMonth=mlc.getMonthForInt(monthNumber);
        mothName.setText(currentMonth);
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



    public void getImportantBullets(int number)
    {

        ArrayList<ArrayList<String>> importantBullets;
        ArrayAdapter<String>AdapterforImportantBullets;
        if(!dBMonthDays.isEmpty()){dBMonthDays.clear();}
        mlc.getDaysInInteger(number);
        importantBullets=dBHelper.getImportantBullets(dBMonthDays,mlc.getNumberofdaysinMonth(),month);
        AdapterforImportantBullets=new CustomAdapterMonth(this,daysofMonth,importantBullets);
        monthdaysListView.setAdapter(AdapterforImportantBullets);

    }














}// End of the Class

