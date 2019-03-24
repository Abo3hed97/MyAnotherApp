package com.example.user.myanotherapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.myanotherapp.Mysql.Dataimport;


import org.mindrot.jbcrypt.BCrypt;

/**
 * Start Activity (Login page)
 */
public class MainActivity extends AppCompatActivity {
    /**
     * To save the userID and us it in Dataimport class
     */
    public static int userID;
    /**
     * To save the username and us it in Dataimport class
     */
    public static String userName;
    /**
     * used in check method if it's true the username and password are true
     */
    boolean i = false;
    /**
     * new Dataimport object
     */
    Dataimport dataimport= new Dataimport();
    /**
     * used to show an error message if the Username not valid
     */
    private TextInputLayout textInputLayoutEmail;
    /**
     * used to show an error message if the password is wrong
     */
    private TextInputLayout textInputLayoutPassword;
    /**
     * used to get the username from user
     */
    private TextInputEditText textInputEditTextEmail;
    /**
     * used to get the password from user
     */
    private TextInputEditText textInputEditTextPassword;
    /**
     * linke to register page
     */
    private TextView textViewLinkRegister;
    /**
     * New InputValidation object
     */
    private InputValidation inputValidation;
    /**
     * new ExampleDBHelper object
     */
    private ExampleDBHelper databaseHelper;
    /**
     * Login Button
     */
    private Button loginB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /**
         * allowing the app to connect to the internet using JDBC
         */
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        /**
         * Connecting to uni Server
         */
        dataimport.connectToDBServer();
        databaseHelper = new ExampleDBHelper(this);
        inputValidation = new InputValidation(this);
        loginB= findViewById(R.id.BLogin);
        textViewLinkRegister = findViewById(R.id.newAccount);
        /**
         * clicking the Login Button
         */

        loginB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkUser();

            }
        });
        /**
         * clicking the textViewLingRegister
         */
        textViewLinkRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 * new Intent to go to register page
                 */
                Intent intentRegister = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intentRegister);
            }
        });

        textInputLayoutEmail =  findViewById(R.id.textInputLayoutEmail);
        textInputLayoutPassword = findViewById(R.id.textInputLayoutPassword);
        textInputEditTextEmail = findViewById(R.id.textInputEditTextEmail);
        textInputEditTextPassword = findViewById(R.id.textInputEditTextPassword);
    }

    /**
     * Used to check if the username and password is valid from Offline database
     */
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

    /**
     * Used to check if the username and password is valid from online database
     */
    public void checkUser ()
    {
        userName = textInputEditTextEmail.getText().toString();
        if (!inputValidation.isInputEditTextFilled(textInputEditTextEmail, textInputLayoutEmail, "Enter Valid Email")) {
            return;
        }
        if (!inputValidation.isInputEditTextFilled(textInputEditTextPassword, textInputLayoutPassword, "Enter Valid Email")) {
            return;
        }

        try {

                String hash_php = dataimport.importDataUser().getPassword().replaceFirst("2y", "2a");
                boolean b = BCrypt.checkpw(textInputEditTextPassword.getText().toString(), hash_php);
                if (dataimport.importDataUser().getUsername().equals(textInputEditTextEmail.getText().toString())&&b==true) {
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



}