package com.example.user.myanotherapp;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private Button loginB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loginB=(Button) findViewById(R.id.BLogin);
        Activity dailyLog=new DailyLogActivity();
        Onclick(loginB,dailyLog);
    }

    public void Onclick(Button b, final Activity activity)
    {
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDailyLog(activity);
            }
        });
    }

    public void openDailyLog(Activity activity)
    {
        Intent intent=new Intent(this,activity.getClass());
        startActivity(intent);
    }
}
