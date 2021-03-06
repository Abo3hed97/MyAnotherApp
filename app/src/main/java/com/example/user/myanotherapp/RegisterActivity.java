package com.example.user.myanotherapp;

import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.myanotherapp.Mysql.Dataexporter;
import com.example.user.myanotherapp.Mysql.UserOnline;

import org.mindrot.jbcrypt.BCrypt;

/**
 * The register activity is called when user clicks on register button in the Login activity.
 * It contains the registration form.
 * It provides methods for creating a user, creating an SQLite database and putting user data in this database.
 * After succesfull registration we return to the login activity.
 */
public class RegisterActivity extends AppCompatActivity {

    Animation anim;

    /**
     * ?
     */
    private final AppCompatActivity activity = RegisterActivity.this;

    /**
     * ?
     */
    private TextInputLayout textInputLayoutName;
    /**
     * ?
     */
    private TextInputLayout textInputLayoutEmail;
    /**
     * ?
     */
    private TextInputLayout textInputLayoutPassword;
    /**
     * ?
     */
    private TextInputLayout textInputLayoutConfirmPassword;

    /**
     * A text field where the user can put in their name
     */
    private TextInputEditText textInputEditTextName;
    /**
     * A text field where the user can put in their email
     */
    private TextInputEditText textInputEditTextEmail;
    /**
     * A text field where the user can put in their password
     */
    private TextInputEditText textInputEditTextPassword;
    /**
     * A text field where the user can confirm their password
     */
    private TextInputEditText textInputEditTextConfirmPassword;

    /**
     * the register button
     */
    private Button appCompatButtonRegister;
    /**
     * ?
     */
    private TextView appCompatTextViewLoginLink;

    /**
     * we need an InputValidationObject to check if user input is nonempty and has the expected format
     */
    private InputValidation inputValidation;
    /**
     * we need an ExampleDBHelperObject to have a SQLite database to put in user data
     */
    private ExampleDBHelper databaseHelper;
    /**
     * If a user registers a new User Object is created
     */
    private User user;

    private UserOnline userOnline;

    private Dataexporter dataexporter;


    TextInputEditText password;
    /**
     * ?
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        //animation textinput
        textInputEditTextName=(TextInputEditText) findViewById(R.id.textInputEditTextName);
        anim= AnimationUtils.loadAnimation(this,R.anim.tran1);
        textInputEditTextName.setAnimation(anim);

        textInputLayoutName=(TextInputLayout) findViewById(R.id.textInputLayoutName);
        anim= AnimationUtils.loadAnimation(this,R.anim.tran1);
        textInputLayoutName.setAnimation(anim);

        textInputEditTextEmail=(TextInputEditText) findViewById(R.id.textInputEditTextEmail);
        anim= AnimationUtils.loadAnimation(this,R.anim.tran1);
        textInputEditTextEmail.setAnimation(anim);

        textInputLayoutEmail=(TextInputLayout) findViewById(R.id.textInputLayoutEmail);
        anim= AnimationUtils.loadAnimation(this,R.anim.tran1);
        textInputLayoutEmail.setAnimation(anim);

        textInputEditTextPassword=(TextInputEditText) findViewById(R.id.textInputEditTextPassword);
        anim= AnimationUtils.loadAnimation(this,R.anim.tran1);
        textInputEditTextPassword.setAnimation(anim);

        textInputLayoutPassword=(TextInputLayout) findViewById(R.id.textInputLayoutPassword);
        anim= AnimationUtils.loadAnimation(this,R.anim.tran3);
        textInputLayoutPassword.setAnimation(anim);

        textInputEditTextConfirmPassword=(TextInputEditText) findViewById(R.id.textInputEditTextConfirmPassword);
        anim= AnimationUtils.loadAnimation(this,R.anim.tran1);
        textInputEditTextConfirmPassword.setAnimation(anim);

        textInputLayoutConfirmPassword=(TextInputLayout) findViewById(R.id.textInputLayoutConfirmPassword);
        anim= AnimationUtils.loadAnimation(this,R.anim.tran3);
        textInputLayoutConfirmPassword.setAnimation(anim);
        initViews();
        initObjects();
        appCompatButtonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postDataToSQLite();
            }
        });
        appCompatTextViewLoginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }


    /**
     * Creates the text fields to input name, email and password (twice).
     * Creates the register button
     * ? appCompatTextViewLoginLink ?
     */
    private void initViews() {

        textInputLayoutName = findViewById(R.id.textInputLayoutName);
        textInputLayoutEmail = findViewById(R.id.textInputLayoutEmail);
        textInputLayoutPassword = findViewById(R.id.textInputLayoutPassword);
        textInputLayoutConfirmPassword = findViewById(R.id.textInputLayoutConfirmPassword);

        textInputEditTextName = findViewById(R.id.textInputEditTextName);
        textInputEditTextEmail = findViewById(R.id.textInputEditTextEmail);
        textInputEditTextPassword = findViewById(R.id.textInputEditTextPassword);
        textInputEditTextConfirmPassword = findViewById(R.id.textInputEditTextConfirmPassword);

        appCompatButtonRegister = findViewById(R.id.appCompatButtonRegister);

        appCompatTextViewLoginLink = findViewById(R.id.appCompatTextViewLoginLink);


    }


    /**
     * sets inputValidation, databaseHelper and user on new instances of these objects
     */
    private void initObjects() {
        inputValidation = new InputValidation(activity);
        databaseHelper = new ExampleDBHelper(activity);
        user = new User();
        userOnline = new UserOnline();
        dataexporter = new Dataexporter();

    }
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.appCompatButtonRegister:
                postDataToSQLite();
                break;

            case R.id.appCompatTextViewLoginLink:
                finish();
                break;
        }
    }

    /**
     * checks if user input is valid.
     * If yes: passes the information to databaseHelper and returns to login activity
     * If no: asks the user to put in valid data
     */
    private void postDataToSQLite() {
        if (!inputValidation.isInputEditTextFilled(textInputEditTextName, textInputLayoutName, "Enter full name")) {
            return;
        }
        if (!inputValidation.isInputEditTextFilled(textInputEditTextEmail, textInputLayoutEmail, "Enter valid email")) {
            return;
        }
        if (!inputValidation.isInputEditTextEmail(textInputEditTextEmail, textInputLayoutEmail, "Enter valid email")) {
            return;
        }
        if (!inputValidation.isInputEditTextFilled(textInputEditTextPassword, textInputLayoutPassword, "Enter password")) {
            return;
        }
        if (!inputValidation.isInputEditTextMatches(textInputEditTextPassword, textInputEditTextConfirmPassword,
                textInputLayoutConfirmPassword, "Password does not match")) {
            return;
        }


            String hashedPw=  BCrypt.hashpw(textInputEditTextPassword.getText().toString(),BCrypt.gensalt());


            userOnline.setUsername(textInputEditTextName.getText().toString().trim());
            userOnline.setEmail(textInputEditTextEmail.getText().toString().trim());
            userOnline.setPassword(hashedPw);


            //databaseHelper.addUser(user);
            dataexporter.addUser(userOnline);


            Toast tost = new Toast(this);
            Toast.makeText(getApplicationContext(), "Registration successful", Toast.LENGTH_LONG).show();
            emptyInputEditText();
            finish();





    }

    /**
     * This method is to empty all input edit text
     */
    private void emptyInputEditText() {
        textInputEditTextName.setText(null);
        textInputEditTextEmail.setText(null);
        textInputEditTextPassword.setText(null);
        textInputEditTextConfirmPassword.setText(null);
    }



}