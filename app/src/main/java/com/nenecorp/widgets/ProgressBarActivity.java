package com.nenecorp.widgets;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.nenecorp.Widgets.R;

import nenecorp.widgets.PasswordField;
import nenecorp.widgets.ProgressBar;

public class ProgressBarActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress_bar);
    }

    public void startTimer(View view) {
        ProgressBar progressBar = findViewById(R.id.APS_progressBar);
        progressBar.newTimer(3000, new ProgressBar.OnComplete() {
            @Override
            public void onComplete() {
                Toast.makeText(ProgressBarActivity.this, "Completed", Toast.LENGTH_LONG).show();
            }
        });
    }
}
