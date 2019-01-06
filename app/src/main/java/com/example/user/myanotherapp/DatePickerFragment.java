package com.example.user.myanotherapp;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;
import android.widget.EditText;

import java.util.Calendar;


public class DatePickerFragment extends  DialogFragment implements
        DatePickerDialog.OnDateSetListener {
    EditText DateEdit;
    int year;
    int month;
    int day;
    public String monthV;
    public String yearV;
    public String dayV;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);
        monthV = String.valueOf(month);
        yearV = String.valueOf(year);
        dayV = String.valueOf(day);



        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(), this, year, month, day);


    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int day) {
        // Do something with the date chosen by the user
        DateEdit.setText(day + "/" + (month + 1) + "/" + year);

    }





}
