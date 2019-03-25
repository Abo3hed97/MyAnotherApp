package com.example.user.myanotherapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CustomAdapterMonth extends ArrayAdapter<String> {

    ArrayList<ArrayList<String>> importantBullets;
    MonthlyLogClass mlc=new MonthlyLogClass();
    List<Bullet> bullet= new ArrayList<Bullet>();
    public CustomAdapterMonth(Context context, ArrayList<String> daysoMonth,ArrayList<ArrayList<String>> importantBullets) {
        super(context, R.layout.customrowm, daysoMonth);
        this.importantBullets=importantBullets;
    }

   static ListView importantBulletsforDay;
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater abdulinflator = LayoutInflater.from(getContext());
        View customView = abdulinflator.inflate(R.layout.customrowm, parent, false);

           String day = getItem(position);
           TextView dayofMonth = customView.findViewById(R.id.domoy);
            //String day=d.get(position);
            if (currentDate().equals(day)) {
                //convert from Hexdecimal Value to Integer Value.
                int valueOfColor1 = Color.parseColor("#D2842D");
                //set the Orange Color.
                 dayofMonth.setTextColor(valueOfColor1);
                 dayofMonth.setText(day);
                //  }
            }
            else{
                dayofMonth.setText(day);
            }


               /* int valueOfColor2 = Color.parseColor("#1f214b");
                tv.setTextColor(valueOfColor2);
                tv.setText(b[position]);*/
          final TextView firstImp=customView.findViewById(R.id.firstImportantBullet);
          final TextView secondImp=customView.findViewById(R.id.secondImportantBullet);
          final Button delet1=customView.findViewById(R.id.delete1);
          final Button delet2=customView.findViewById(R.id.delete2);
             if(importantBullets.get(position).size()==1)
             {
                 int valueOfColor2 = Color.parseColor("#1f214b");
                 firstImp.setTextColor(valueOfColor2);
                 firstImp.setTextSize(20);
                 firstImp.setText(importantBullets.get(position).get(0));
                 ((ViewGroup) secondImp.getParent()).removeView(secondImp);
                 ((ViewGroup) delet2.getParent()).removeView(delet2);
                 Log.i("Height",String.valueOf(secondImp.getHeight()));
                 Log.i("Width",String.valueOf(secondImp.getWidth()));
                 delet1.setOnClickListener(new View.OnClickListener() {
                     @Override
                     public void onClick(View v) {
                         AlertDialog alertDialog=new AlertDialog.Builder(v.getContext()).create();
                         alertDialog.setTitle("Delete...?");
                         alertDialog.setMessage("Are you sure?");
                         alertDialog.setButton("Ok", new DialogInterface.OnClickListener() {
                             @Override
                             public void onClick(DialogInterface dialog, int which) {
                                 ((ViewGroup) firstImp.getParent()).removeView(firstImp);
                                 ((ViewGroup) delet1.getParent()).removeView(delet1);
                             }
                         });
                         ((ViewGroup) firstImp.getParent()).removeView(firstImp);
                         ((ViewGroup) delet1.getParent()).removeView(delet1);
                     }

                 });
             }
            else if(importantBullets.get(position).size()==2)
             {
                 int valueOfColor2 = Color.parseColor("#1f214b");
                 firstImp.setTextColor(valueOfColor2);
                 firstImp.setTextSize(20);
                 firstImp.setText(importantBullets.get(position).get(0));
                 secondImp.setTextColor(valueOfColor2);
                 secondImp.setTextSize(20);
                 secondImp.setText(importantBullets.get(position).get(1));
                 delet1.setOnClickListener(new View.OnClickListener() {
                     @Override
                     public void onClick(View v) {
                         AlertDialog alertDialog=new AlertDialog.Builder(v.getContext()).create();
                         alertDialog.setTitle("Delete...?");
                         alertDialog.setMessage("Are you sure?");
                         alertDialog.setButton("Ok", new DialogInterface.OnClickListener() {
                             @Override
                             public void onClick(DialogInterface dialog, int which) {
                                 ((ViewGroup) firstImp.getParent()).removeView(firstImp);
                                 ((ViewGroup) delet1.getParent()).removeView(delet1);
                             }
                         });
                         ((ViewGroup) firstImp.getParent()).removeView(firstImp);
                         ((ViewGroup) delet1.getParent()).removeView(delet1);
                     }
                 });

                 delet2.setOnClickListener(new View.OnClickListener() {
                     @Override
                     public void onClick(View v) {
                         AlertDialog alertDialog=new AlertDialog.Builder(v.getContext()).create();
                         alertDialog.setTitle("Delete...?");
                         alertDialog.setMessage("Are you sure?");
                         alertDialog.setButton("Ok", new DialogInterface.OnClickListener() {
                             @Override
                             public void onClick(DialogInterface dialog, int which) {
                                 ((ViewGroup) secondImp.getParent()).removeView(secondImp);
                                 ((ViewGroup) delet2.getParent()).removeView(delet2);
                             }
                         });
                         ((ViewGroup) secondImp.getParent()).removeView(secondImp);
                         ((ViewGroup) delet2.getParent()).removeView(delet2);
                     }

                 });
             }
             else{
                 //firstImp.setHeight(0);
                // firstImp.setWidth(0);
                 Log.i("Height",String.valueOf(firstImp.getHeight()));
                 Log.i("Width",String.valueOf(firstImp.getWidth()));
                 //secondImp.setHeight(0);
                // secondImp.setWidth(0);
                 Log.i("Height",String.valueOf(secondImp.getHeight()));
                 Log.i("Width",String.valueOf(secondImp.getWidth()));
                 ((ViewGroup) firstImp.getParent()).removeView(firstImp);
                 ((ViewGroup) secondImp.getParent()).removeView(secondImp);
                 ((ViewGroup) delet1.getParent()).removeView(delet1);
                 ((ViewGroup) delet2.getParent()).removeView(delet2);

             }









        return customView;
    }




        public String currentDate()
        {
            Calendar c = Calendar.getInstance();
            int year = Calendar.YEAR;
            int month = c.get(Calendar.MONTH) + 1;
            String current;
            int day=c.get(Calendar.DAY_OF_MONTH);
            String dateString = String.format("%d-%d-%d", c.get(Calendar.YEAR), month, day);
            Date date = null;
            try {
                date = new SimpleDateFormat("yyyy-M-d").parse(dateString);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            // Then get the day of week from the Date based on specific locale.
            String dayOfWeek = new SimpleDateFormat("EEEE", Locale.ENGLISH).format(date);
            current = day + "." + dayOfWeek;
            return current;
        }






















    }

