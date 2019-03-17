package com.example.user.myanotherapp;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class CustomAdapterFuture extends ArrayAdapter<String> {
    String veryImportnatBulletsList[];
    ArrayList<String> monthsList;
    public CustomAdapterFuture(FutureLogActivity context, ArrayList<String> months, String[] veryImpBullets) {
        super(context, R.layout.customrowf, months);
        this.veryImportnatBulletsList=veryImpBullets;
        this.monthsList=months;
    }


    @Override
    public View getView(int position, View convertView,ViewGroup parent) {
        LayoutInflater abdulinflator = LayoutInflater.from(getContext());
        View customView = abdulinflator.inflate(R.layout.customrowf, parent, false);
        TextView veryImportantView=customView.findViewById(R.id.veryImportantBullet);;
        String month = monthsList.get(position);
        TextView monthOfYear = customView.findViewById(R.id.monthName);
        //convert from Hexdecimal Value to Integer Value.
        int valueOfColor = Color.parseColor("#d2842d");
        monthOfYear.setTextColor(valueOfColor);
        monthOfYear.setText(month);

        if(position<veryImportnatBulletsList.length&&veryImportnatBulletsList[position]!=null) {
            String veryImportantBullet = veryImportnatBulletsList[position];
            int valueOfColor1 = Color.parseColor("#1f214b");
            //set the Orange Color.
            veryImportantView.setTextColor(valueOfColor1);
            veryImportantView.setTextSize(20);
            veryImportantView.setText(veryImportantBullet);
        }
        else{((ViewGroup) veryImportantView.getParent()).removeView(veryImportantView);}
        return customView;
    }
}
