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
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.user.myanotherapp.Mysql.Bullet;
import com.example.user.myanotherapp.Mysql.Dataexporter;

/**
 * Used to make a new Bullet
 */
public class New_Bullet extends FragmentActivity {
    /**
     * EditText to get Date from user
     */
    static EditText DateEdit_From;
    static EditText DateEdit_To;
    /**
     * EditText to get Time from user
     */
    static EditText TimeEdit_From;
    static EditText TimeEdit_To;
    /**
     * Type of Bullet
     */
    Spinner type;
    /**
     * new TimePickerFragment object
     */
    DialogFragment TimeFragment = new TimePickerFragment();
    /**
     * new DatePickerFragment object
     */
    DialogFragment DateFragment = new DatePickerFragment();
    ExampleDBHelper db;
    /**
     * EditText to get Title from user
     */
    EditText n_title;
    /**
     * EditText to get text from user
     */
    EditText n_text;
    /**
     * RadioButton to get the importance
     */
    RadioButton nImp;
    RadioButton Imp;
    RadioButton vImp;
    /**
     * Saving the user input in Strings
     */
    String title, text, dateFrom, dateTo, timeFrom, timeTo, typ;
    int imp=0;
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
        DateFragment = new DatePickerFragment();
        /**
         * On click showing DatePickerDialog
         */


        DateEdit_From.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showTruitonDatePickerDialog(v, DateEdit_From);

            }
        });
        /**
         * on click showing TimePickerDialog
         */
        TimeEdit_From.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTruitonTimePickerDialog(v, TimeEdit_From);

            }
        });
        /**
         * On click showing DatePickerDialog
         */

        DateEdit_To.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showTruitonDatePickerDialog(v, DateEdit_To);

            }
        });
        /**
         * on click showing TimePickerDialog
         */
        TimeEdit_To.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTruitonTimePickerDialog(v, TimeEdit_To);

            }
        });
        /**
         * Setting the Sinner item
         */
        ArrayAdapter<String> types = new ArrayAdapter<String>(this,
                R.layout.spinner_item, getResources().getStringArray(R.array.type));
        types.setDropDownViewResource(R.layout.spinner_item);
        type.setAdapter(types);


        // EditText

        db = new ExampleDBHelper(getApplicationContext());
        pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);

        Button clickButton = findViewById(R.id.save);
        /**
         * On click get the user input and save it to the database
         */
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
                if (dateTo.length() == 0)
                {
                    dateTo = dateFrom;

                }
                if (timeFrom.length() == 0 || timeTo.length() == 0)
                {
                    timeFrom = "00:00:00";
                    timeTo = "00:00:00";
                }


                if (title.length() == 0 || text.length() == 0) {
                    Toast.makeText(getApplicationContext(), "title or text box is empty !!!",
                            Toast.LENGTH_SHORT).show();
                }
                else if(dateFrom.length()==0)
                {
                    Toast.makeText(getApplicationContext(), "Date From can't be empty",
                            Toast.LENGTH_SHORT).show();
                }

                else {
                    bullet.setTitle(title);
                    bullet.setContent(text);
                    bullet.setDateFrom(dateFrom);
                    bullet.setDateTo(dateTo);
                    bullet.setTimeFrom(timeFrom);
                    bullet.setTimeTo(timeTo);
                    bullet.setBulletType(typ);
                    bullet.setImportance(imp);
                    bullet.setUserID(MainActivity.userID);
                    dataexporter.addBullet(bullet);
                    /*db.insertPerson(title, text, dateFrom, dateTo, timeFrom, timeTo, typ,monthS,imp,vimp);*/
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
        boolean checked = ((RadioButton) view).isChecked();

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
                    imp = 2;
                }
                    break;


        }

    }
}
