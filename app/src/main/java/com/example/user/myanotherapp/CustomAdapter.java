package com.example.user.myanotherapp;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.List;

import static com.example.user.myanotherapp.ExampleDBHelper.INPUT_COLUMN_DateFrom;
import static com.example.user.myanotherapp.ExampleDBHelper.INPUT_COLUMN_ID;
import static com.example.user.myanotherapp.listOfNotes.INPUT_COLUMN_Text;
import static com.example.user.myanotherapp.listOfNotes.INPUT_COLUMN_Title;

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
        TextView dayDate = (TextView) customView.findViewById(R.id.CustomDate);


        dayDate.setText(day);



        e=(ListView) customView.findViewById(R.id.lida);

        e.setAdapter(b.get(position));


        return customView;
    }







}
