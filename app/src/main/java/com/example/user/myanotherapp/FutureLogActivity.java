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

import com.example.user.myanotherapp.Mysql.Dataimport;

import java.text.DateFormatSymbols;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;



public class FutureLogActivity extends AppCompatActivity {


    TextView year;
    int currentYear;
    ListView monthsOfYear;
    ArrayAdapter<String> arrayAdapterForMonthsOfYear;
    ArrayList<String> monthsList;
    ArrayAdapter<String> arrayAdapter;
    ExampleDBHelper exampleDBHelper=new ExampleDBHelper(this);
    Button nextYear;
    Button previousYear;

    Dataimport dataimport = new Dataimport();

    String[] VeryImportantData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_future_log);


        //Set up the Current Year
        year=findViewById(R.id.Year);
        currentYear=Calendar.getInstance().get(Calendar.YEAR);
        year.setText(String.valueOf(currentYear));
        //End


        //Set up the Months
        monthsOfYear=findViewById(R.id.listofMonthsinYear);
        monthsList=new ArrayList<String>(Arrays.asList(new DateFormatSymbols().getMonths()));
        VeryImportantData= dataimport.getVeryImportantBullets(monthNumbers(),currentYear);
        arrayAdapter=new CustomAdapterFuture(this,monthsList, VeryImportantData);
        monthsOfYear.setAdapter(arrayAdapter);
        //End


        monthsOfYear.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                goToMonthlyLog(position);
            }
        });

        Button button=findViewById(R.id.goToCalendarFromFutureLog);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToCalendar();
            }
        });




        //go to nextYear
        nextYear=findViewById(R.id.nextYear);
        nextYear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentYear++;
                year.setText(String.valueOf(currentYear));
                Arrays.fill(VeryImportantData,null);
                VeryImportantData= dataimport.getVeryImportantBullets(monthNumbers(),currentYear);
                decleaeadapter();
            }
        });



        //go to Previous Year
        previousYear=findViewById(R.id.previousYear);
        previousYear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentYear--;
                year.setText(String.valueOf(currentYear));
                Arrays.fill(VeryImportantData,null);
                VeryImportantData=dataimport.getVeryImportantBullets(monthNumbers(),currentYear);
                decleaeadapter();
            }
        });
    }


    public void goToMonthlyLog(int selectedMonth){
        Intent intent=new Intent(this,MonthlyLog.class);
        MonthlyLog.year=currentYear;
        MonthlyLog.month=selectedMonth+1;
        startActivity(intent);

    }



    public void  goToCalendar(){
        Intent intent=new Intent(this,CalendarActivity.class);
        startActivity(intent);
    }


    public ArrayList<Integer> monthNumbers(){
       ArrayList<Integer> monthNumbers=new ArrayList<>();
       for(int i=0;i<12;i++){
           monthNumbers.add(i+1);
       }
       return monthNumbers;
    }

    public void decleaeadapter(){
        arrayAdapter=new CustomAdapterFuture(this,monthsList, VeryImportantData);
        monthsOfYear.setAdapter(arrayAdapter);
    }








}
