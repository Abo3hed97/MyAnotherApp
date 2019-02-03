package com.example.user.myanotherapp;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class CustomAdapterMonth extends ArrayAdapter<String> {

    ArrayList<String> d = new ArrayList<>();

    public CustomAdapterMonth(Context context, ArrayList<String> daysoMonth) {
        super(context, R.layout.customrowm, daysoMonth);
        this.d = daysoMonth;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater abdulinflator = LayoutInflater.from(getContext());
        View customView = abdulinflator.inflate(R.layout.customrowm, parent, false);

        // String day = getItem(position);
        TextView dayofMonth = customView.findViewById(R.id.domoy);
        if (position != d.size()) {
            //String day=d.get(position);
            if (d.get(position).equals(currentDate())) {
                //convert from Hexdecimal Value to Integer Value.
                int valueOfColor = Color.parseColor("#1f214b");
                //set the Orange Color.
                 dayofMonth.setTextColor(valueOfColor);
                 dayofMonth.setText(d.get(position));
                //  }
            }
            else{
                dayofMonth.setText(d.get(position));
            }
            TextView tv = customView.findViewById(R.id.listoft);


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

