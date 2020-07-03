package com.kejiyuanren.systrace;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Trace;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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

}