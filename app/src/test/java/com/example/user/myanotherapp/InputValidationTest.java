package com.example.user.myanotherapp;

import android.support.design.widget.TextInputLayout;

import org.junit.Test;

import static org.junit.Assert.*;

public class InputValidationTest {
    private TextInputLayout textInputLayoutEmail;

    @Test
    public void isInputEditTextEmail() {
        String email = "m@m.com";
        InputValidation n = new InputValidation();
        assertEquals(true, n.isInputEditTextEmail(email));
    }
}