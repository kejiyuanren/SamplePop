package com.kejiyuanren.pop;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.lang.ref.WeakReference;
import java.util.concurrent.TimeUnit;

public class MemoryProfilerActivity extends AppCompatActivity {
    private static final String TAG = "MemoryProfilerActivity";

    //模拟订阅/反订阅缺失泄漏
    @SuppressLint("MissingPermission")
    private void monitorRegister() {
        Log.d(TAG, "monitorRegister: ");
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
                TimeUnit.MINUTES.toMillis(5), 100, new LocationListener() {
                    @Override
                    public void onLocationChanged(@NonNull Location location) {
                        Log.d(TAG, "onLocationChanged: ");
                    }
                });
    }

    //模拟非静态内部类
    private static Object inner;
    private void monitorNoStaticInnerClass() {
        Log.d(TAG, "monitorNoStaticInnerClass: ");
        class InnerClass {

        }
        inner = new InnerClass();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: ");
        setContentView(R.layout.activity_memory_profiler);
        mResultTv = findViewById(R.id.memory_type_result);
        ((RadioGroup) findViewById(R.id.memory_handler_type)).
                setOnCheckedChangeListener(mOnCheckedChangeListener);
    }

    private RadioGroup.OnCheckedChangeListener mOnCheckedChangeListener =
            new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup radioGroup, int i) {
            switch (i) {
                case R.id.handler_no_static:
                    memoryTestType = MEMORY_ERROR_NO_STATIC;
                    break;
                case R.id.handler_inner_class:
                    memoryTestType = MEMORY_ERROR_INNER_CLASS;
                    break;
                case R.id.handler_static_weak:
                    memoryTestType = MEMORY_NORMAL_STATIC_WEAK;
                    break;
                case R.id.handler_no_static_clear_message:
                    memoryTestType = MEMORY_NORMAL_MESSAGE_CLEAR;
                    break;
                default:
                    break;
            }
        }
    };

    public void onMemoryHandlerMonitor(View view) {
        Log.d(TAG, "onMemoryHandlerMonitor: ");
        monitorHandler();
    }

    //模拟Handler泄漏（memory profiler以这个来演示实操）
    private static final int MESSAGE_DELAY = 5 * 1000;

    private static final int MEMORY_ERROR_NO_STATIC = 1;
    private static final int MEMORY_ERROR_INNER_CLASS = 2;
    private static final int MEMORY_NORMAL_STATIC_WEAK = 3;
    private static final int MEMORY_NORMAL_MESSAGE_CLEAR = 4;

    //只要动态修改此参数就行
    private int memoryTestType = MEMORY_ERROR_NO_STATIC;
    TextView mResultTv;
    private Handler mHandler;

    @Override
    protected void onDestroy() {
        Log.d(TAG, "onDestroy: ");
        if (MEMORY_NORMAL_MESSAGE_CLEAR == memoryTestType) {
            mHandler.removeCallbacksAndMessages(null);
        }
        super.onDestroy();
    }

    //Button click 触发模拟操作
    private void monitorHandler() {
        Log.d(TAG, "monitorHandler: ");
        switch (memoryTestType) {
            case MEMORY_ERROR_NO_STATIC:
                //泄漏1 : 使用no-static内部类
                mHandler = new NoStaticHandler();
                break;
            case MEMORY_ERROR_INNER_CLASS:
                //泄漏2：使用匿名内部类
                mHandler = new Handler() {
                    @Override
                    public void handleMessage(@NonNull Message msg) {
                        Log.d(TAG, "inner class handler handleMessage: " + msg.what);
                        mResultTv.setText("MEMORY_ERROR_INNER_CLASS");
                    }
                };
                break;
            case MEMORY_NORMAL_STATIC_WEAK:
                //不泄露3：静态+弱引用（为了保证Handler中消息队列中的所有消息都能被执行，
                //推荐使用 静态内部类 + 弱引用的方式）
                mHandler = new StaticHandler(this);
                break;
            case MEMORY_NORMAL_MESSAGE_CLEAR:
                //不泄露4：destroy()方法对handler进行消息队列清空，结束Handler生命周期
                mHandler = new NoStaticHandler();
                break;
            default:
                Log.e(TAG, "monitorHandler: default no handle");
                return;
        }

        //执行事件延迟队列发送
        mHandler.sendEmptyMessageDelayed(1, MESSAGE_DELAY);
    }

    //no-static内部类：自定义Handler子类
    class NoStaticHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            Log.d(TAG, "NoStaticHandler handleMessage: " + msg.what);
            mResultTv.setText(memoryTestType == MEMORY_ERROR_NO_STATIC ?
                    "MEMORY_ERROR_NO_STATIC" : "MEMORY_NORMAL_MESSAGE_CLEAR");
        }
    }

    private static class StaticHandler extends Handler {
        private WeakReference<Activity> reference;

        public StaticHandler(Activity activity) {
            super(activity.getMainLooper());
            reference = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(@NonNull Message msg) {
            Log.d(TAG, "StaticHandler handleMessage: " + msg.what);
            Activity activity = reference.get();
            if (null != activity) {
                Log.d(TAG, "StaticHandler handleMessage: ui update");
                ((TextView) activity.findViewById(R.id.memory_type_result)).
                        setText("MEMORY_NORMAL_STATIC_WEAK");
            }
        }
    }
}