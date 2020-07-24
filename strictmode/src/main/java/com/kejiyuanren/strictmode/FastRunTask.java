package com.kejiyuanren.strictmode;

import android.os.StrictMode;
import android.os.SystemClock;

public class FastRunTask {
    private static final int MAX_RUN_VALID_DURATION = 300;

    public void execute(Runnable task) {
        long startTime = SystemClock.uptimeMillis();
        task.run();
        long useTime = SystemClock.uptimeMillis() - startTime;
        if (useTime > MAX_RUN_VALID_DURATION) {
            StrictMode.noteSlowCall("FastRunTask note slow call use time : [" + useTime + "]");
        }
    }
}
