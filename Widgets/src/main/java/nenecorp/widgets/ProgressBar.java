package nenecorp.widgets;

import android.content.Context;
import android.graphics.drawable.LayerDrawable;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;


public class ProgressBar extends LinearLayout {
    final android.widget.ProgressBar mProgress;
    private LayerDrawable circularDrawable;
    private int max;
    private OnComplete onComplete;

    public ProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.view_progress_bar, this, true);
        circularDrawable = (LayerDrawable) ContextCompat.getDrawable(getContext(), R.drawable.pb_progress);
        mProgress = findViewById(R.id.VPD_circularProgressbar);
    }

    public void initializeProgress(final int max, int current, final OnComplete onComplete) {
        mProgress.setProgress(current);   // Main Progress
        mProgress.setSecondaryProgress(max); // Secondary Progress
        mProgress.setMax(max); // Maximum Progress
        mProgress.setProgressDrawable(circularDrawable);
        this.max = max;
        this.onComplete = onComplete;
    }

    public void updateProgess(int current) {
        if (current >= max) {
            onComplete.onComplete();
        } else {
            long p = (current * 100) / (max);
            int percent = (int) p;
            mProgress.setProgress(percent);
            ((TextView) findViewById(R.id.VPD_txtPercentage)).setText(percent + "%");
        }
    }

    public void newTimer(final int duration, final OnComplete onComplete) {
        final Handler handler = new Handler();
        final android.widget.ProgressBar mProgress = findViewById(R.id.VPD_circularProgressbar);
        mProgress.setProgress(0);   // Main Progress
        mProgress.setSecondaryProgress(100); // Secondary Progress
        mProgress.setMax(100); // Maximum Progress
        mProgress.setProgressDrawable(circularDrawable);
        final long[] pStatus = {0};
        final long[] delay = {duration / 100};
        Runnable timer = new Runnable() {
            @Override
            public void run() {
                if (pStatus[0] >= duration) {
                    onComplete.onComplete();
                } else {
                    pStatus[0] = pStatus[0] + delay[0];
                    long p = (pStatus[0] * 100) / (duration);
                    int percent = (int) p;
                    mProgress.setProgress(percent);
                    ((TextView) findViewById(R.id.VPD_txtPercentage)).setText(percent + "%");
                    handler.postDelayed(this, delay[0]);
                }
            }
        };
        handler.postDelayed(timer, delay[0]);
    }

    public interface OnComplete {
        void onComplete();
    }
}

