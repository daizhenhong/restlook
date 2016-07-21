package com.example.restlook.service;

import android.app.Service;
import android.content.Intent;
import android.content.ServiceConnection;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Binder;
import android.os.IBinder;
import android.os.Vibrator;
import android.util.Log;

/**
 * Created by daizhenhong on 2016-7-19.
 */
public class RestLookService extends Service {
    private final String TAG = this.getClass().getSimpleName();
    private SensorManager mSensorManager;
    private Sensor mGravitySensor;
    private long time = 0;
    /**
     * 手机震动
     */
    private Vibrator mVibrator;

    @Override
    public void onCreate() {
        super.onCreate();
//        mVibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        Log.e(TAG, "Service onCreate");
    }

    public class MyBind extends Binder {
        public RestLookService getService() {
            return RestLookService.this;
        }

    }


    @Override
    public IBinder onBind(Intent intent) {
        Log.e(TAG, "Service onBind");

        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        mGravitySensor = mSensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY);

        mSensorManager.registerListener(mGravitySensorListener,
                mGravitySensor,
                SensorManager.SENSOR_DELAY_NORMAL);

//        return new Binder();
        return new MyBind();
    }

    @Override
    public void unbindService(ServiceConnection conn) {
        Log.e(TAG, "Service unbindService");
        super.unbindService(conn);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e(TAG, "onStartCommand");

        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        mGravitySensor = mSensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY);

        mSensorManager.registerListener(mGravitySensorListener,
                mGravitySensor,
                SensorManager.SENSOR_DELAY_NORMAL);


//        return Service.START_REDELIVER_INTENT;
//        return Service.START_NOT_STICKY;
        return super.onStartCommand(intent,flags,startId);
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.e(TAG, "Service onUnbind");
//        mSensorManager.unregisterListener(mGravitySensorListener);
        return super.onUnbind(intent);
    }

    private SensorEventListener mGravitySensorListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {
            if (sensorEvent.sensor.getType() == Sensor.TYPE_GRAVITY) {
                float x = sensorEvent.values[0];
                float y = sensorEvent.values[1];
                float z = sensorEvent.values[2];

                if (time % 3 == 0) {
                    Log.e(TAG, "x=" + x + ";y=" + y + ";z=" + z);
                    time=0;
                }time++;
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {

        }
    };


    @Override
    public void onRebind(Intent intent) {
        Log.e(TAG, "Service onRebind");
        super.onRebind(intent);
    }
    @Override
    public void onDestroy() {
        Log.e(TAG, "Service onDestroy");
        mSensorManager.unregisterListener(mGravitySensorListener);
        super.onDestroy();
    }
}
