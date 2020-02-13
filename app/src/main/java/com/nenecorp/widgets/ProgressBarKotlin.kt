package com.nenecorp.widgets

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.nenecorp.Widgets.R
import nenecorp.widgets.ProgressBar

class ProgressBarKotlin : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_progress_bar)
    }

    fun startTimer(view: View?) {
        val progressBar = findViewById<ProgressBar>(R.id._progressBar)
        progressBar.newTimer(3000) { Toast.makeText(this@ProgressBarKotlin, "Completed", Toast.LENGTH_LONG).show() }
    }

    fun newPro() {
        val progressBar = findViewById<ProgressBar>(R.id._progressBar)
        progressBar.initializeProgress(100, 0) { Toast.makeText(this@ProgressBarKotlin, "Completed", Toast.LENGTH_LONG).show() }
        progressBar.updateProgess(20)
    }
}