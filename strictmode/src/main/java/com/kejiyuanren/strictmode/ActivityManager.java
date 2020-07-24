package com.kejiyuanren.strictmode;

import android.app.Activity;

import java.util.ArrayList;

public class ActivityManager {
    private static ActivityManager mInstance = new ActivityManager();
    public ArrayList<Activity> mActivities = new ArrayList<>();

    private ActivityManager(){
    }

    public static ActivityManager getInstance() {
        return mInstance;
    }
}
