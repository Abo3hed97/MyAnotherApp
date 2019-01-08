package com.example.user.myanotherapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.user.myanotherapp.R;

import java.util.ArrayList;

public class Settings extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);


        ArrayList<String> sett=new ArrayList<>();
        sett.add("Change Color");
        sett.add("Change Language");

        ListView s=(ListView)findViewById(R.id.ActivitySettings);
        ArrayAdapter arrayAdapter=new ArrayAdapter(this,android.R.layout.simple_list_item_1,sett);
        s.setAdapter(arrayAdapter);



    }
}
