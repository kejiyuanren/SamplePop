package com.kejiyuanren.pop;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class CpuProfilerActivity extends AppCompatActivity {
    private static final String TAG = "CpuProfilerActivity";

    TextView mCalResultTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: ");
//        Debug.startMethodTracing("kejiyuanren");
        setContentView(R.layout.activity_cpu_profiler);
        mCalResultTv = findViewById(R.id.cal_result_tv);
    }

    @Override
    protected void onDestroy() {
//        Debug.stopMethodTracing();
        Log.d(TAG, "onDestroy: ");
        super.onDestroy();
    }

    public void onCpuCalculateMonitor(View view) {
        Log.d(TAG, "onCpuCalculateMonitor: ");
        cpuCalculate();
    }

    //我们主要通过这个方法来详细解析 cpu profiler的使用
    private void cpuCalculate() {
        int count = 0;
        for(int i = 0; i < 100; i++) {
            for (int j = 0; j < 100; j++) {
                //这个进行加法计
                count += i + j;
                //这个进行log输出
                Log.d(TAG, "cpuCalculate: count = [" + count + "]");
            }
        }
        //这个进行view操作
        mCalResultTv.setText(String.valueOf(count));
    }
}