package com.example.restlook.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

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
