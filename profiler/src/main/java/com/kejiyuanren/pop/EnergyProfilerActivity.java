package com.kejiyuanren.pop;

import android.os.Bundle;
import android.os.PowerManager;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class EnergyProfilerActivity extends AppCompatActivity {
    private static final String TAG = "EnergyProfilerActivity";

    private PowerManager.WakeLock mWakeLock;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_energy_profiler);
    }

    public void onEnergyWakeLockAcquire(View view) {
        Log.d(TAG, "onEnergyWakeLockTest: ");
        if (mWakeLock != null) {
            return;
        }
        createWakeLock();
        mWakeLock.acquire();
    }

    public void onEnergyWakeLockRelease(View view) {
        Log.d(TAG, "onEnergyWakeLockRelease: ");
        if (mWakeLock != null) {
            mWakeLock.release();
            mWakeLock = null;
        }
    }

    private void createWakeLock() {
        Log.d(TAG, "createWakeLock: ");
        PowerManager pm = (PowerManager) getBaseContext().getSystemService(POWER_SERVICE);
        mWakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, getClass().getSimpleName());
    }
}