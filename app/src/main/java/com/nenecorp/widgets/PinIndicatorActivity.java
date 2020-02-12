package com.nenecorp.widgets;

import android.os.Bundle;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.nenecorp.Widgets.R;

import nenecorp.widgets.NumPad;
import nenecorp.widgets.PinIndicator;

public class PinIndicatorActivity extends AppCompatActivity {
    private TextView logView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pin_indicator);
        logView = findViewById(R.id.API_logView);
        NumPad numPad = findViewById(R.id.API_numpad);
        numPad.setInputListener(new NumPad.InputListener() {
            @Override
            public void onInputChanged(String number) {
                addMessage(number);
            }
        });
        PinIndicator pinIndicator = findViewById(R.id.API_pinIndicator);
        pinIndicator.reallyCoolNumpad(numPad);
        addMessage("Initialized...\nPinIndicator\n");
    }

    private void addMessage(String s) {
        String message = logView.getText().toString() + s;
        if (!s.equals("")) {
            logView.setText(message + "\n");
        }
        ScrollView scrollView = findViewById(R.id.API_log);
        scrollView.scrollTo(0, scrollView.getBottom());
    }
}
