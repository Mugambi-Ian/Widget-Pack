package com.nenecorp.widgets;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.nenecorp.Widgets.R;

import nenecorp.widgets.ProgressBar;

public class ProgressBarJava extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress_bar);
    }

    public void startTimer(View view) {
        ProgressBar progressBar = findViewById(R.id._progressBar);
        progressBar.newTimer(3000, new ProgressBar.OnComplete() {
            @Override
            public void onComplete() {
                Toast.makeText(ProgressBarJava.this, "Completed", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void newPro() {
        ProgressBar progressBar = findViewById(R.id._progressBar);
        progressBar.initializeProgress(100, 0, new ProgressBar.OnComplete() {
            @Override
            public void onComplete() {
                Toast.makeText(ProgressBarJava.this, "Completed", Toast.LENGTH_LONG).show();
            }
        });
        progressBar.updateProgess(20);
    }


}
