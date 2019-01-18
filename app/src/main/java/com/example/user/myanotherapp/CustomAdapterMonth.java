package com.example.user.myanotherapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomAdapterMonth extends ArrayAdapter<String> {

    ArrayList<String> d=new ArrayList<>();
    public CustomAdapterMonth(Context context, ArrayList<String> daysoMonth) {
        super(context, R.layout.customrowm, daysoMonth);
        this.d=daysoMonth;
    }


    @Override
    public View getView(int position, View convertView,  ViewGroup parent) {
        LayoutInflater abdulinflator = LayoutInflater.from(getContext());
        View customView = abdulinflator.inflate(R.layout.customrowm, parent, false);

       // String day = getItem(position);
        TextView dayofMonth = (TextView)customView.findViewById(R.id.domoy);
        dayofMonth.setText(d.get(position));

        TextView tv=(TextView)customView.findViewById(R.id.listoft);

        return customView;
    }
}
