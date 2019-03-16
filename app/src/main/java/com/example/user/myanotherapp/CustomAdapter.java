package com.example.user.myanotherapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


class CustomAdapter extends ArrayAdapter<String> {
    ExampleDBHelper mydb=new ExampleDBHelper(getContext());;
    List<Bullet> bullet= new ArrayList<Bullet>();
    ArrayList<ArrayAdapter<String>>b=new ArrayList<>();
    public CustomAdapter(DailyLogActivity context, ArrayList<String> days, ArrayList<ArrayAdapter<String>> h) {
        super(context, R.layout.custom_row, days);
        this.b=h;
    }




    ListView e;

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater abdulinflator = LayoutInflater.from(getContext());
        View customView = abdulinflator.inflate(R.layout.custom_row, parent, false);
        final String day = getItem(position);
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
                setListViewHeightBasedOnChildren(e);
                e.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                        Log.i(day, day);
                        bullet = mydb.loadData(day);
                        return onLongListItemClick(view, position, id);
                    }

                    protected boolean onLongListItemClick(View v, final int pos, long id)  {
                        AlertDialog alertDialog=new AlertDialog.Builder(v.getContext()).create();
                        alertDialog.setTitle("Delete...?");
                        alertDialog.setMessage("Are you sure?");
                        alertDialog.setButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String a = bullet.get(pos).getId();
                                mydb.deleteSingleContact(a);
                                Log.i("HELLLLLLLLLLOOOOO","World");
                                b.get(position).remove(b.get(position).getItem(pos));
                                b.get(position).notifyDataSetChanged();
                                e.setAdapter(b.get(position));
                                    }

                        });
                        alertDialog.show();
                        return true;
                    }
                });
            }
            Button gotoaddnewBullet=customView.findViewById(R.id.GOTOADDNEWBULLET);
            gotoaddnewBullet.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                       // DailyLogActivity.class.newInstance().moveToNewBulletActivity();

                        Intent intent=new Intent(getContext(),New_Bullet.class);
                        getContext().startActivity(intent);

                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                }
            });




        return customView;
    }


    public String currentDate()
    {
        Calendar cal = Calendar.getInstance();
        String currentDate = DateFormat.getDateInstance().format(cal.getTime());
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        return currentDate = sdf.format(cal.getTime());
    }



    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = 0;
        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.AT_MOST);
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();
    }







    }




















