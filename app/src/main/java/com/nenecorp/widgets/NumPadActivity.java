package com.nenecorp.widgets;

import android.os.Bundle;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.nenecorp.Widgets.R;

import nenecorp.widgets.NumPad;

public class NumPadActivity extends AppCompatActivity {
    private TextView logView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_num_pad);
        logView = findViewById(R.id.ANP_logView);
        NumPad numPad = findViewById(R.id.ANP_numpad);
        numPad.setInputListener(new NumPad.InputListener() {
            @Override
            public void onInputChanged(String number) {
                addMessage(number);
            }
        });
        addMessage("Initialized...\nNumPad\n");
    }

    private void addMessage(String s) {
        String message = logView.getText().toString() + s;
        if (!s.equals("")) {
            logView.setText(message+ "\n");
        }
        ScrollView scrollView = findViewById(R.id.ANP_log);
        scrollView.scrollTo(0, scrollView.getBottom());
    }
}
