package com.nenecorp.widgets;

import android.os.Bundle;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.nenecorp.Widgets.R;

import nenecorp.widgets.DayPicker;

public class DayPickerActivity extends AppCompatActivity {
    private TextView logView;
    private DayPicker dayPicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day_picker);
        logView = findViewById(R.id.ADP_logView);
        dayPicker = findViewById(R.id.ANP_timePicker);
        addMessage("Initialized...\nDayPicker\n");
    }

    private void addMessage(String s) {
        String message = logView.getText().toString() + s;
        logView.setText(message);
        ScrollView scrollView = findViewById(R.id.ADP_log);
        scrollView.scrollTo(0, scrollView.getBottom());
    }

    public void getDays(View view) {
        addMessage(dayPicker.getDays()+"\n");
    }
}
