package com.kejiyuanren.systrace;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Trace;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    private View mMonitorBgView;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mMonitorBgView = findViewById(R.id.monitor_bg);
        Log.d(TAG, "onCreate: ");
    }

    @SuppressLint("NewApi")
    public void onDebugApiMonitor(View view) {
        Log.d(TAG, "onDebugApiMonitor: ");
        Trace.beginSection("monitor log 100 time");
        for (int i = 0; i < 100; i++) {
            Log.d(TAG, "onDebugApiMonitor: i = " + i);
        }
        Trace.endSection();
    }

    public void onFramesLossMonitor(View view) {
        Log.d(TAG, "onFramesLossMonitor: ");
        startAnimator();
    }

    private void startAnimator() {
        Log.d(TAG, "startAnimator: ");
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, 1f);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mMonitorBgView.setAlpha((float) animation.getAnimatedValue());
                monitorDoSomeThings();
            }
        });
        valueAnimator.setDuration(500);
        valueAnimator.start();
    }

    private void monitorDoSomeThings() {
        Log.d(TAG, "monitorDoSomeThings: ");
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            Log.d(TAG, "writeSomething: ");
        }
    }
}