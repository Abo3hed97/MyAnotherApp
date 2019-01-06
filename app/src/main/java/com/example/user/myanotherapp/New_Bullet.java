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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;

public class New_Bullet extends FragmentActivity {
    static EditText DateEdit_From;
    static EditText TimeEdit_From;
    static EditText DateEdit_To;
    static EditText TimeEdit_To;
    Spinner type;
    TextView displayTextView;
    String spinnerType;
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    DialogFragment TimeFragment = new TimePickerFragment();
    DialogFragment DateFragment = new DatePickerFragment();
    ListView mobile_list;
    ArrayList<HashMap<String, String>> dataList = new ArrayList<HashMap<String, String>>();

    ExampleDBHelper db;
    EditText n_title;
    EditText n_text;
    String title,text;
    public static SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_bullet);
        DateEdit_From =  findViewById(R.id.Date_From);
        TimeEdit_From = findViewById(R.id.Time_From);
        DateEdit_To = findViewById(R.id.Date_To);
        TimeEdit_To = findViewById(R.id.Time_To);
        DateFragment = new DatePickerFragment();
        DateEdit_From.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showTruitonDatePickerDialog(v,DateEdit_From);

            }
        });
        TimeEdit_From.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTruitonTimePickerDialog(v,TimeEdit_From);

            }
        });

        DateEdit_To.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showTruitonDatePickerDialog(v,DateEdit_To);

            }
        });
        TimeEdit_To.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTruitonTimePickerDialog(v,TimeEdit_To);

            }
        });


        type = findViewById(R.id.Type);

        ArrayAdapter<String> types = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.type));
        types.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        type.setAdapter(types);
        // EditText

        db=new ExampleDBHelper(getApplicationContext());
        pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);


        n_title=(EditText) findViewById(R.id.title);
        n_text=(EditText) findViewById(R.id.text);



        Button clickButton = (Button) findViewById(R.id.save);
        clickButton.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                title = n_title.getText().toString();
                text = n_text.getText().toString();



                if( title.length() == 0||text.length() == 0){
                    Toast.makeText(getApplicationContext(), "title or text box is empty !!!",
                            Toast.LENGTH_SHORT).show();
                }
                else
                {
                    db.insertPerson(title,text);
                    Toast.makeText(getApplicationContext(), "Done", Toast.LENGTH_LONG).show();
                    opentestListView();
                }
            }
        });

    }
    public void opentestListView()
    {
        Intent intent = new Intent(this, listOfNotes.class);
        startActivity(intent);
    }

    public void showTruitonDatePickerDialog(View v,EditText Date) {


        ((DatePickerFragment) DateFragment).DateEdit = Date;
        DateFragment.show(getSupportFragmentManager(), "datePicker");

    }


    public void showTruitonTimePickerDialog(View v,EditText Time) {


        ((TimePickerFragment) TimeFragment).DateEdit = Time;
        TimeFragment.show(getSupportFragmentManager(), "timePicker");
    }
    /*public void BokaBoka(View view) {
        spinnerType = type.getSelectedItem().toString();
        Toast toast = Toast.makeText(getApplicationContext(),
                DateEdit_From.getText(),
                Toast.LENGTH_LONG);

        toast.show();

    }*/

}
