package com.kejiyuanren.pop;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onCpuProfilerTest(View view) {
        Log.d(TAG, "onCpuProfilerTest() called with: view = [" + view + "]");
        startActivity(new Intent(this, CpuProfilerActivity.class));
    }
}