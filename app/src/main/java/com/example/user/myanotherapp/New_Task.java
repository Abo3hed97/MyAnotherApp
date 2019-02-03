package com.example.user.myanotherapp;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class New_Task extends AppCompatActivity {
    ExampleDBHelper db;
    EditText n_title;
    EditText n_text;
    Spinner months;
    String title,text;
    String monthS;
    public static SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_task);

        db=new ExampleDBHelper(getApplicationContext());
        pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        months = findViewById(R.id.spinner);
        n_title= findViewById(R.id.title);
        n_text= findViewById(R.id.text);
        ArrayAdapter<String> test = new ArrayAdapter<>
                (this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.months));
        test.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        months.setAdapter(test);



        Button clickButton = findViewById(R.id.clickButton);
        clickButton.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                title = n_title.getText().toString();
                text = n_text.getText().toString();
                monthS = months.getSelectedItem().toString();

                if(title.length() == 0){
                    SharedPreferences.Editor editor = pref.edit();

                    int idName = pref.getInt("name", 0);
                    idName++;
                    title="new document "+idName ;
                    editor.putInt("name",idName);
                    editor.apply();

                }

                if( text.length() == 0){
                    Toast.makeText(getApplicationContext(), "title or text box is empty !!!",
                            Toast.LENGTH_SHORT).show();
                }
                else
                {
                    db.insertPerson(title,text,monthS);
                    Toast.makeText(getApplicationContext(), "Done", Toast.LENGTH_LONG).show();
                    finish();}
            }
        });
    }
}
