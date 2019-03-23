package com.example.user.myanotherapp;

import android.app.Activity;
import android.content.Intent;
import android.media.Image;
import android.os.StrictMode;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


/*import org.springframework.security.crypto.bcrypt.*;*/

import com.example.user.myanotherapp.Mysql.Dataimport;
import com.example.user.myanotherapp.Mysql.MysqlTest;
import com.example.user.myanotherapp.Mysql.UserOnline;

import org.mindrot.jbcrypt.BCrypt;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    ImageView edlogo;
    Animation anim;

    private TextInputLayout textInputLayoutEmail;
    private TextInputLayout textInputLayoutPassword;

    private TextInputEditText textInputEditTextEmail;
    private TextInputEditText textInputEditTextPassword;

    private TextView textViewLinkRegister;

    private InputValidation inputValidation;
    private ExampleDBHelper databaseHelper;
    private Button loginB;
    boolean i = false;
    Dataimport dataimport= new Dataimport();
    List<UserOnline> user2 = new ArrayList<UserOnline>();
    public static int userID;
    public static String userEmail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        edlogo=(ImageView) findViewById(R.id.edlogo);
        anim= AnimationUtils.loadAnimation(this,R.anim.myanim);
        edlogo.setAnimation(anim);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        dataimport.connectToDBServer();
        Toast tost = new Toast(this);
        Toast.makeText(getApplicationContext(), Dataimport.conError, Toast.LENGTH_LONG).show();
        databaseHelper = new ExampleDBHelper(this);
        inputValidation = new InputValidation(this);
        loginB= findViewById(R.id.BLogin);
        textViewLinkRegister = findViewById(R.id.newAccount);

       // Activity dailyLog=new DailyLogActivity();
        //Onclick(loginB,dailyLog);


        loginB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //verifyFromSQLite();
                checkUser();

            }
        });
        textViewLinkRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentRegister = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intentRegister);
            }
        });

        textInputLayoutEmail =  findViewById(R.id.textInputLayoutEmail);
        textInputLayoutPassword = findViewById(R.id.textInputLayoutPassword);
        textInputEditTextEmail = findViewById(R.id.textInputEditTextEmail);
        textInputEditTextPassword = findViewById(R.id.textInputEditTextPassword);
        /*test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                opentest();

            }
        });*/

    }

   /* public void Onclick(Button b, final Activity activity)
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
    }*/

   /* public void onClick(View v) {
        switch (v.getId()) {
            case R.id.BLogin:
                verifyFromSQLite();
                break;
            case R.id.newAccount:
                // Navigate to RegisterActivity
                Intent intentRegister = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intentRegister);
                break;
        }
    }*/





    private void verifyFromSQLite() {
        if (!inputValidation.isInputEditTextFilled(textInputEditTextEmail, textInputLayoutEmail, "Enter Valid Email")) {
            return;
        }
        if (!inputValidation.isInputEditTextEmail(textInputEditTextEmail, textInputLayoutEmail, "Enter Valid Email")) {
            return;
        }
        if (!inputValidation.isInputEditTextFilled(textInputEditTextPassword, textInputLayoutPassword, "Enter Valid Email")) {
            return;
        }

        if (databaseHelper.checkUser(textInputEditTextEmail.getText().toString().trim()
                , textInputEditTextPassword.getText().toString().trim())){


            Intent accountsIntent = new Intent(this, DailyLogActivity.class);
            accountsIntent.putExtra("EMAIL", textInputEditTextEmail.getText().toString().trim());
            emptyInputEditText();
            startActivity(accountsIntent);


        } else {

            Toast tost = new Toast(this);
            Toast.makeText(getApplicationContext(), "Wrong Email or Password", Toast.LENGTH_LONG).show();
        }
    }
    public void checkUser ()
    {
        userEmail = textInputEditTextEmail.getText().toString();
        if (!inputValidation.isInputEditTextFilled(textInputEditTextEmail, textInputLayoutEmail, "Enter Valid Email")) {
            return;
        }
        if (!inputValidation.isInputEditTextEmail(textInputEditTextEmail, textInputLayoutEmail, "Enter Valid Email")) {
            return;
        }
        if (!inputValidation.isInputEditTextFilled(textInputEditTextPassword, textInputLayoutPassword, "Enter Valid Email")) {
            return;
        }

        try {

                String hash_php = dataimport.importDataUser().getPassword().replaceFirst("2y", "2a");
                boolean b = BCrypt.checkpw(textInputEditTextPassword.getText().toString(), hash_php);
                if (dataimport.importDataUser().getEmail().equals(textInputEditTextEmail.getText().toString())&&b==true) {
                    //current.getPassword().equals(textInputEditTextPassword.getText().toString())
                    userID=dataimport.importDataUser().getuId();
                    Intent accountsIntent = new Intent(this, DailyLogActivity.class);
                    emptyInputEditText();
                    startActivity(accountsIntent);
                     i = true;
                }

        } catch (Exception e) {
            e.printStackTrace();
        }
        if(i==false)
        {
            Toast tost = new Toast(this);
            tost.makeText(getApplicationContext(), "Wrong Email or Password", Toast.LENGTH_LONG).show();
        }



    }

    /**
     * This method is to empty all input edit text
     */
    private void emptyInputEditText() {
        textInputEditTextEmail.setText(null);
        textInputEditTextPassword.setText(null);
    }


    public void goTo(View view) {
        Intent intent = new Intent(this, MysqlTest.class);
        startActivity(intent);
    }
}