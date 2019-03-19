package com.example.user.myanotherapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.myanotherapp.Mysql.Bullet;
import com.example.user.myanotherapp.Mysql.Dataexporter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;

public class New_Bullet extends FragmentActivity {
    static EditText DateEdit_From;
    static EditText TimeEdit_From;
    static EditText DateEdit_To;
    static EditText TimeEdit_To;
    Spinner type;
    Spinner months;
    TextView displayTextView;
    String spinnerType;
    DialogFragment TimeFragment = new TimePickerFragment();
    DialogFragment DateFragment = new DatePickerFragment();
    ListView mobile_list;
    ArrayList<HashMap<String, String>> dataList = new ArrayList<HashMap<String, String>>();
    ExampleDBHelper db;
    EditText n_title;
    EditText n_text;
    RadioGroup radioGroup;
    RadioButton nImp;
    RadioButton Imp;
    RadioButton vImp;
    String title, text, dateFrom, dateTo, timeFrom, timeTo, typ,monthS;
    int imp=0;
    int vimp=0;
    public static SharedPreferences pref;
    Dataexporter dataexporter = new Dataexporter();
    Bullet bullet = new Bullet();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_bullet);
        DateEdit_From = findViewById(R.id.Date_From);
        TimeEdit_From = findViewById(R.id.Time_From);
        DateEdit_To = findViewById(R.id.Date_To);
        TimeEdit_To = findViewById(R.id.Time_To);
        n_title = findViewById(R.id.title);
        n_text = findViewById(R.id.text);
        nImp = findViewById(R.id.nimp);
        Imp = findViewById(R.id.imp);
        vImp = findViewById(R.id.vimp);
        type = findViewById(R.id.Type);
        months = findViewById(R.id.months);
        DateFragment = new DatePickerFragment();
        months.setVisibility(View.INVISIBLE);


        DateEdit_From.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showTruitonDatePickerDialog(v, DateEdit_From);

            }
        });
        TimeEdit_From.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTruitonTimePickerDialog(v, TimeEdit_From);

            }
        });

        DateEdit_To.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showTruitonDatePickerDialog(v, DateEdit_To);

            }
        });
        TimeEdit_To.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTruitonTimePickerDialog(v, TimeEdit_To);

            }
        });




        ArrayAdapter<String> types = new ArrayAdapter<String>(this,
                R.layout.spinner_item, getResources().getStringArray(R.array.type));
        types.setDropDownViewResource(R.layout.spinner_item);
        type.setAdapter(types);


        ArrayAdapter<String> month = new ArrayAdapter<>
                (this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.months));
        month.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        months.setAdapter(month);
        // EditText

        db = new ExampleDBHelper(getApplicationContext());
        pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);

        Button clickButton = findViewById(R.id.save);
        clickButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                title = n_title.getText().toString();
                text = n_text.getText().toString();
                dateFrom = DateEdit_From.getText().toString();
                dateTo = DateEdit_To.getText().toString();
                timeFrom = TimeEdit_From.getText().toString();
                timeTo = TimeEdit_To.getText().toString();
                typ = type.getSelectedItem().toString();
                monthS = months.getSelectedItem().toString();


                if (title.length() == 0 || text.length() == 0) {
                    Toast.makeText(getApplicationContext(), "title or text box is empty !!!",
                            Toast.LENGTH_SHORT).show();
                } else {
                    /*bullet.setTitle(title);
                    bullet.setContent(text);
                    bullet.setDateFrom(dateFrom);
                    bullet.setDateTo(dateTo);
                    bullet.setTimeFrom(timeFrom);
                    bullet.setTimeTo(timeTo);
                    bullet.setBulletType(typ);
                    bullet.setImportance(imp);
                    bullet.setUserID(MainActivity.userID);
                    dataexporter.addBullet(bullet);*/
                    db.insertPerson(title, text, dateFrom, dateTo, timeFrom, timeTo, typ,monthS,imp,vimp);
                    Toast.makeText(getApplicationContext(), "Done", Toast.LENGTH_LONG).show();
                      Intent intentRegister = new Intent(getApplicationContext(), DailyLogActivity.class);
                      startActivity(intentRegister);

                }


            }
        });

    }



    public void showTruitonDatePickerDialog(View v, EditText Date) {


        ((DatePickerFragment) DateFragment).DateEdit = Date;
        DateFragment.show(getSupportFragmentManager(), "datePicker");

    }


    public void showTruitonTimePickerDialog(View v, EditText Time) {


        ((TimePickerFragment) TimeFragment).DateEdit = Time;
        TimeFragment.show(getSupportFragmentManager(), "timePicker");
    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch (view.getId()) {
            case R.id.nimp:
                if (checked)
                {
                    DateEdit_From.setVisibility(View.VISIBLE);
                    TimeEdit_From.setVisibility(View.VISIBLE);
                    DateEdit_To.setVisibility(View.VISIBLE);
                    TimeEdit_To.setVisibility(View.VISIBLE);
                    n_title.setVisibility(View.VISIBLE);
                    n_text.setVisibility(View.VISIBLE);
                    type.setVisibility(View.VISIBLE);
                    months.setVisibility(View.INVISIBLE);
                    imp = 0;

                }
                    break;
            case R.id.imp:
                if (checked)
                {
                    DateEdit_From.setVisibility(View.VISIBLE);
                    TimeEdit_From.setVisibility(View.VISIBLE);
                    DateEdit_To.setVisibility(View.VISIBLE);
                    TimeEdit_To.setVisibility(View.VISIBLE);
                    type.setVisibility(View.VISIBLE);
                    n_title.setVisibility(View.VISIBLE);
                    n_text.setVisibility(View.VISIBLE);
                    months.setVisibility(View.VISIBLE);
                    imp = 1;

                }
                break;
            case R.id.vimp:
                if (checked)
                {
                    DateEdit_From.setVisibility(View.VISIBLE);
                    TimeEdit_From.setVisibility(View.VISIBLE);
                    DateEdit_To.setVisibility(View.VISIBLE);
                    TimeEdit_To.setVisibility(View.VISIBLE);
                    type.setVisibility(View.VISIBLE);
                    n_title.setVisibility(View.VISIBLE);
                    n_text.setVisibility(View.VISIBLE);
                    months.setVisibility(View.VISIBLE);
                    imp = 2;
                }
                    break;


        }
    /*public void BokaBoka(View view) {
        spinnerType = type.getSelectedItem().toString();
        Toast toast = Toast.makeText(getApplicationContext(),
                DateEdit_From.getText(),
                Toast.LENGTH_LONG);

        toast.show();

    }*/

    }
}
