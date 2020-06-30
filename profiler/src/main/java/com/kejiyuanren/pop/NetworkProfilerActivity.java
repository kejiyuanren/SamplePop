package com.kejiyuanren.pop;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;

public class NetworkProfilerActivity extends AppCompatActivity {
    private static final String TAG = "NetworkProfilerActivity";

    private static final int MESSAGE_TYPE_GET = 1;
    SafeHandler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_network_profiler);
        mHandler = new SafeHandler(this);
    }

    public void onNetworkMonitorGet(View view) {
        Log.d(TAG, "onNetworkMonitorGet: ");
        new Thread(networkGet).start();
    }

    private static class SafeHandler extends Handler {
        private WeakReference<Activity> reference;

        public SafeHandler(Activity activity) {
            super(activity.getMainLooper());
            reference = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(@NonNull Message msg) {
            Log.d(TAG, "SafeHandler handleMessage: " + msg.what);
            Activity activity = reference.get();
            if (null != activity) {
                Log.d(TAG, "SafeHandler handleMessage: do work!");
                switch (msg.what) {
                    case MESSAGE_TYPE_GET:
                        String getInfo = (String) msg.obj;
                        ((TextView) activity.findViewById(R.id.network_get_result)).setText(getInfo);
                        break;
                    default:
                        break;
                }
            }
        }
    }

    Runnable networkGet = new Runnable() {
        @Override
        public void run() {
            String getResult = get();
            Log.d(TAG, "networkGet run: getResult = " + getResult);
            Message message = Message.obtain();
            message.what = MESSAGE_TYPE_GET;
            message.obj = getResult;
            mHandler.sendMessage(message);
        }
    };

    private String get() {
        String message = "";
        try {
            URL url = new URL("https://www.baidu.com");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Accept-Encoding", "identity");
            connection.setConnectTimeout(10 * 1000);
            connection.connect();
            InputStream inputStream = connection.getInputStream();
            byte[] data = new byte[1024];
            StringBuffer sb = new StringBuffer();
            int length = 0;
            while ((length = inputStream.read(data)) != -1) {
                String s = new String(data, Charset.forName("utf-8"));
                sb.append(s);
            }
            message = sb.toString();
            inputStream.close();
            connection.disconnect();
        } catch (Exception e) {
            Log.e(TAG, "get: ", e);
        }
        return message;
    }
}