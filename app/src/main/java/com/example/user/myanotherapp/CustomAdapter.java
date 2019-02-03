package com.example.user.myanotherapp;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.List;


class CustomAdapter extends ArrayAdapter<String> {

    ArrayList<ArrayAdapter<String>>b=new ArrayList<>();
    public CustomAdapter(Context context, ArrayList<String> days,ArrayList<ArrayAdapter<String>> h) {
        super(context, R.layout.custom_row, days);
        this.b=h;
    }




    ListView e;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater abdulinflator = LayoutInflater.from(getContext());
        View customView = abdulinflator.inflate(R.layout.custom_row, parent, false);

        String day = getItem(position);
        TextView dayDate = customView.findViewById(R.id.CustomDate);
        if(currentDate().equals(day)) {
            //convert from Hexdecimal Value to Integer Value.
            int valueOfColor = Color.parseColor("#d2842d");
            //set the Orange Color.
            dayDate.setTextColor(valueOfColor);
            dayDate.setText(day);
        }
        else {
            dayDate.setText(day);
        }


        e= customView.findViewById(R.id.lida);
            if(position<b.size()) {
                e.setAdapter(b.get(position));
            }

        return customView;
    }


    public String currentDate()
    {
        Calendar cal = Calendar.getInstance();
        String currentDate = DateFormat.getDateInstance().format(cal.getTime());
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        return currentDate = sdf.format(cal.getTime());
    }








}
