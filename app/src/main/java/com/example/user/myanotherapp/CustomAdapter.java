package com.example.user.myanotherapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;

class CustomAdapter extends ArrayAdapter<String> {
    public CustomAdapter(Context context, ArrayList<String> days) {
        super(context, R.layout.custom_row, days);
    }


    @Override
    public View getView(int position,View convertView, ViewGroup parent) {
        LayoutInflater abdulinflator=LayoutInflater.from(getContext());
        View customView=abdulinflator.inflate(R.layout.custom_row,parent,false);

        String day=getItem(position);
        TextView dayDate=(TextView) customView.findViewById(R.id.CustomDate);
        ListView weekdays=(ListView)customView.findViewById(R.id.weekList);

        dayDate.setText(day);

        return customView;
    }
}













