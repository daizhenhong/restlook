package com.example.restlook;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.restlook.service.RestLookService;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private String TAG = this.getClass().getSimpleName();
    private Button mBtnStart;
    private Button mBtnBind;
    private Button mBtnUnbind;
    private Button mBtnStop;

    private RestLookService mService;
    private Intent mIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mIntent = new Intent(this, RestLookService.class);
        initView();
    }

    private void initView() {
        mBtnStart = (Button) findViewById(R.id.id_btn_start);
        mBtnBind = (Button) findViewById(R.id.id_btn_bind);
        mBtnUnbind = (Button) findViewById(R.id.id_btn_unbind);
        mBtnStop = (Button) findViewById(R.id.id_btn_stop);

        mBtnStart.setOnClickListener(this);
        mBtnBind.setOnClickListener(this);
        mBtnUnbind.setOnClickListener(this);
        mBtnStop.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.id_btn_start:
                startService(mIntent);
                break;

            case R.id.id_btn_bind:
                bindService();
                break;

            case R.id.id_btn_unbind:
                unbindService();
                break;

            case R.id.id_btn_stop:
                stopService(mIntent);
                break;
            default:
                break;
        }
    }

    private void unbindService() {
        Log.e(TAG, "MainActivity unbindService!");
        if (null != mService)
            unbindService(mConnection);

    }

    private void bindService() {
        Log.e(TAG, "bindService!");
        Intent intent = new Intent(this, RestLookService.class);
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
    }

    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder service) {
            Log.e(TAG, "MainActivity onServiceConnected!");
            mService = ((RestLookService.MyBind) service).getService();


        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            Log.e(TAG, "MainActivity onServiceDisconnected!");

            mService = null;
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();

        unbindService();
    }

    /**
     * Created by dzh on 2016-7-21.
     */
    public class UserPresentReceiver extends BroadcastReceiver {
        private String TAG= this.getClass().getSimpleName();

        @Override
        public void onReceive(Context context, Intent intent) {
            Log.e(TAG,"receive broadcast");
        }
    }
}
