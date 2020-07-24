package com.kejiyuanren.strictmode;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //代码启用全部的ThreadPolicy和VmPolicy违例检测
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectAll().penaltyLog().build());
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectAll().penaltyLog().build());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onLeakActivityStart(View view) {
        Log.d(TAG, "onLeakActivityStart: ");
        startActivity(new Intent(this, LeakActivity.class));
    }

    public void onFastRunTask(View view) {
        Log.d(TAG, "onFastRunTask: ");
        FastRunTask fastRunTask = new FastRunTask();
        fastRunTask.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void onWriteFile(View view) {
        Log.d(TAG, "onWriteFile: ");
        //执行模拟测试
        writeToFileInSubThread();
    }

    /**
     * 主线程写文件
     */
    public void writeToFileInMainThread() {
        File destFile = new File(Environment.getExternalStoragePublicDirectory
                (Environment.DIRECTORY_DOWNLOADS).getPath(), "StrictModeTest.txt");
        try {
            destFile.createNewFile();
            destFile.setWritable(true);
            OutputStream output = new FileOutputStream(destFile, true);
            output.write("IO operation".getBytes());
            output.flush();
            output.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 子线程写文件
     */
    public void writeToFileInSubThread() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                writeToFileInMainThread();
            }
        }).start();
    }
}
