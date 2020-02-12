package com.nenecorp.widgets;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.nenecorp.Widgets.R;

import nenecorp.widgets.TextPad;

public class TextPadActivity extends AppCompatActivity {
    private TextView logView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_pad);
        logView = findViewById(R.id.ATP_logView);
        TextPad textPad = findViewById(R.id.ATP_textpad);
        textPad.setInputListener(new TextPad.InputListener() {
            @Override
            public void onInputChanged(String s) {
                logView.setText(s);
            }
        });
    }


}
