package com.example.user.myanotherapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;


public class FutureLogActivity extends AppCompatActivity {


    TextView year;
    int currentYear;
    ListView monthsOfYear;
    ArrayAdapter<String> arrayAdapterForMonthsOfYear;
    ArrayList<String> monthsList;
    ArrayAdapter<String> arrayAdapter;
    ExampleDBHelper exampleDBHelper=new ExampleDBHelper(this);
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
        String[] veryImportantData= null;
        try {
            veryImportantData = exampleDBHelper.getVeryImportantBullets(monthsList,currentYear);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        arrayAdapter=new CustomAdapterFuture(this,monthsList,veryImportantData);
        monthsOfYear.setAdapter(arrayAdapter);
        //End




        monthsOfYear.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                goToMonthlyLog(position);
            }
        });

    }


    public void goToMonthlyLog(int i){
        Intent intent=new Intent(this,MonthlyLog.class);
        MonthlyLog.month=i+1;
        startActivity(intent);

    }









}
