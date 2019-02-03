package com.example.user.myanotherapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class MonthlyTasks extends AppCompatActivity {
    ListView mobile_list;
    ExampleDBHelper mydb;
    List<Bullet> bullet= new ArrayList<Bullet>();
    public static final String INPUT_COLUMN_ID = "_id";
    public static final String INPUT_COLUMN_UID = "uid";
    public static final String INPUT_COLUMN_Title = "title";
    public static final String INPUT_COLUMN_Text = "text";
    public static final String INPUT_COLUMN_DateFrom = "dateFrom";
    public static final String INPUT_COLUMN_DateTo = "dateTo";
    public static final String INPUT_COLUMN_TimeFrom = "timeFrom";
    public static final String INPUT_COLUMN_TimeTo = "timeTo";
    public static final String INPUT_COLUMN_Type = "type";
    public static final String INPUT_COLUMN_Months = "months";
    public static final String INPUT_COLUMN_Imp = "imp";
    public static final String INPUT_COLUMN_Vimp = "vimp";
    TextView mothName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monthly_tasks);
        mydb = new ExampleDBHelper(getApplicationContext());
        mobile_list = findViewById(R.id.mobile_list);
         mothName= findViewById(R.id.monthT);

        int monthNumber=Calendar.getInstance().get(Calendar.MONTH);
        String currentMonth=getMonthForInt(monthNumber);
        mothName.setText(currentMonth);
        loadData();
    }
    @Override
    public void onResume(){
        super.onResume();
        loadData();

    }

    public void addNew(View view) {
        Intent intent = new Intent(this, New_Task.class);
        startActivity(intent);
    }

    public void loadData()
    {
        bullet.clear();
        Cursor cursor = mydb.getAllPersons();
        if (cursor.moveToFirst())
        {
            while (!cursor.isAfterLast()) {
                if (cursor.getString(cursor.getColumnIndex(INPUT_COLUMN_Months)).equals(mothName.getText().toString())) {
                    bullet.add(new Bullet(
                            cursor.getString(cursor.getColumnIndex(INPUT_COLUMN_ID)),
                            cursor.getString(cursor.getColumnIndex(INPUT_COLUMN_UID)),
                            cursor.getString(cursor.getColumnIndex(INPUT_COLUMN_Title)),
                            cursor.getString(cursor.getColumnIndex(INPUT_COLUMN_Text)),
                            cursor.getString(cursor.getColumnIndex(INPUT_COLUMN_DateFrom)),
                            cursor.getString(cursor.getColumnIndex(INPUT_COLUMN_DateTo)),
                            cursor.getString(cursor.getColumnIndex(INPUT_COLUMN_TimeFrom)),
                            cursor.getString(cursor.getColumnIndex(INPUT_COLUMN_TimeTo)),
                            cursor.getString(cursor.getColumnIndex(INPUT_COLUMN_Type)),
                            cursor.getString(cursor.getColumnIndex(INPUT_COLUMN_Months)),
                            cursor.getString(cursor.getColumnIndex(INPUT_COLUMN_Imp)),
                            cursor.getString(cursor.getColumnIndex(INPUT_COLUMN_Vimp))

                    ));
                }

                    cursor.moveToNext();

            }
        }
        BulletAdapter bAdapter = new BulletAdapter(this, bullet);
        mobile_list.setAdapter(bAdapter);
        mobile_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(MonthlyTasks.this, NoteViewActivity.class);
                i.putExtra("id", bullet.get(position).getId());
                i.putExtra("title", bullet.get(position).getTitle());
                i.putExtra("text", bullet.get(position).getText());
                startActivity(i);
            }
        });
        mobile_list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                return onLongListItemClick(view,position,id);
            }
            protected boolean onLongListItemClick(View v, final int pos, long id)
            {
                AlertDialog alertDialog=new AlertDialog.Builder(v.getContext()).create();
                alertDialog.setTitle("Delete...?");
                alertDialog.setMessage("Are you sure?");
                alertDialog.setButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String a = bullet.get(pos).getId();
                        mydb.deleteSingleContact(a);
                        loadData();
                    }
                });
                alertDialog.show();
                return true;
            }
        });

    }
    String getMonthForInt(int num) {
        String month = "";
        //An Onbject with it's Help we get an Array of Months of the Year.
        DateFormatSymbols dfs = new DateFormatSymbols();
        // Array to Store all the Monhts of the Year.
        String[] months = dfs.getMonths();
        //chack if the Number of Month greater or equel than 0 and lesser equel than 11
        if (num >= 0 && num <= 11 ) {
            month = months[num];
        }

        //return the asked Month.
        return month;
    }
}



 class BulletAdapter extends ArrayAdapter<Bullet>
{
    private Context mContext;
    private List<Bullet> bullet = new ArrayList<Bullet>();
    public BulletAdapter(Context context,List<Bullet> list)
    {
        super(context,0,list);
        mContext = context;
        bullet = list;

    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if(listItem==null)
            listItem = LayoutInflater.from(mContext).inflate(R.layout.activity_listview, parent, false);
            Bullet currentBullet = bullet.get(position);
            TextView title = listItem.findViewById(R.id.list_title);
            title.setText(currentBullet.getTitle());

            TextView text = listItem.findViewById(R.id.list_text);
            text.setText(currentBullet.getText());
        return listItem;

    }

}
