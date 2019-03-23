package com.example.user.myanotherapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.user.myanotherapp.Mysql.Dataimport;

import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;


public class FutureLogActivity extends AppCompatActivity {

    Button gotocalender;
    Animation anim;
    TextView year;
    int currentYear;
    ListView monthsOfYear;
    ArrayAdapter<String> arrayAdapterForMonthsOfYear;
    ArrayList<String> monthsList;
    ArrayAdapter<String> arrayAdapter;
    ExampleDBHelper exampleDBHelper=new ExampleDBHelper(this);

    Dataimport dataimport = new Dataimport();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_future_log);

        //translat the calender button (animation)
        gotocalender=(Button) findViewById(R.id.goToCalendar);
        anim= AnimationUtils.loadAnimation(this,R.anim.myanim);
        gotocalender.setAnimation(anim);

        //Set up the Current Year
        year=findViewById(R.id.Year);
        currentYear=Calendar.getInstance().get(Calendar.YEAR);
        year.setText(String.valueOf(currentYear));
        //End


        //Set up the Months
        monthsOfYear=findViewById(R.id.listofMonthsinYear);
        monthsList=new ArrayList<String>(Arrays.asList(new DateFormatSymbols().getMonths()));
        String veryImportantData[];
        veryImportantData = dataimport.getVeryImportantBullets(monthNumbers(),currentYear);
        arrayAdapter=new CustomAdapterFuture(this,monthsList,veryImportantData);
        monthsOfYear.setAdapter(arrayAdapter);
        //End




        monthsOfYear.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                goToMonthlyLog(position);
            }
        });

        Button button=findViewById(R.id.goToCalendar);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToCalendar();
            }
        });

    }


    public void goToMonthlyLog(int i){
        Intent intent=new Intent(this,MonthlyLog.class);
        MonthlyLog.month=i+1;
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





}
