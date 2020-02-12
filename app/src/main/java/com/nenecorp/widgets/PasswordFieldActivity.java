package com.nenecorp.widgets;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.nenecorp.Widgets.R;

import nenecorp.widgets.PasswordField;

public class PasswordFieldActivity extends AppCompatActivity {
    private PasswordField mPasswordField, bPasswordField, cPasswordField, currentPasswordField;
    private String password = "";
    private String userId = "072882992";
    private TextView logView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_field);
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
        );
        mPasswordField = findViewById(R.id.APF_mPasF);
        bPasswordField = findViewById(R.id.APF_bPasF);
        cPasswordField = findViewById(R.id.APF_cPasF);
        currentPasswordField = mPasswordField;
        logView = findViewById(R.id.APF_logView);
        addMessage("Initialized...\nEncryption key is user id = " + userId + "\n");
    }

    private void addMessage(String s) {
        String message = logView.getText().toString() + s;
        logView.setText(message);
        ScrollView scrollView = findViewById(R.id.APF_log);
        scrollView.scrollTo(0, scrollView.getBottom());
    }

    public void materialField(View view) {
        bPasswordField.setVisibility(View.GONE);
        cPasswordField.setVisibility(View.GONE);
        mPasswordField.setVisibility(View.VISIBLE);
        mPasswordField.clearText();
        currentPasswordField = mPasswordField;
    }

    public void borderedField(View view) {
        mPasswordField.setVisibility(View.GONE);
        cPasswordField.setVisibility(View.GONE);
        bPasswordField.setVisibility(View.VISIBLE);
        bPasswordField.clearText();
        currentPasswordField = bPasswordField;
    }

    public void customField(View view) {
        cPasswordField.setVisibility(View.VISIBLE);
        mPasswordField.setVisibility(View.GONE);
        bPasswordField.setVisibility(View.GONE);
        cPasswordField.clearText();
        currentPasswordField = cPasswordField;
    }

    public void showError(View view) {
        currentPasswordField.setError("Error message goes here.");
    }

    public void savePassword(View view) {
        password = currentPasswordField.getEncryptedPassword(userId);
        addMessage("Password successfully saved. PasswordKey = " + password + "\n");
    }

    public void checkPassword(View view) {
        boolean passwordValid = currentPasswordField.isPasswordValid(userId, password);
        if (!passwordValid) {
            addMessage("Password invalid.\n");
        } else {
            addMessage("Password valid.\n");
        }
    }
}
