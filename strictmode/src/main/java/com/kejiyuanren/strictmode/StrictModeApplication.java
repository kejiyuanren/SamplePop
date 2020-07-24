package com.kejiyuanren.strictmode;

import android.app.Application;
import android.os.StrictMode;

public class StrictModeApplication extends Application {
    @Override
    public void onCreate() {
//        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectAll().penaltyLog().build());
//        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectAll().penaltyLog().build());
        super.onCreate();
    }
}
