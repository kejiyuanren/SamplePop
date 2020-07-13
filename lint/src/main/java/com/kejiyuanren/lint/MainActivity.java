package com.kejiyuanren.lint;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    private TextView mLintTv;

    //Lint - performance - reference leaks - 性能方向的内存泄漏
    //Java - code maturity - deprecated api usage 弃用方法
    //java - declaration redundancy - unused declaration 生命冗余,没有使用警告
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mLintTv = findViewById(R.id.lint_text);
    }

    public void onLintTextClick(View view) {
        Log.d(TAG, "onLintTextClick: ");
        //lint - Internationalization - Hardcode text 硬编码
        mLintTv.setText("替换lint文本");
    }
}
