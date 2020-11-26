package com.yifan.funwithwatermark.module.main;

import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.IBinder;
import android.provider.Settings;
import android.util.Log;

import androidx.annotation.Nullable;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * @Description:
 * @Author: ZhengXiang Sun
 * @Data: 2020-11-25
 */
public class TestService extends Service {

    AudioManager am;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        am = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    getpackage();
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            }
        }).start();

        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void getpackage() {
//        String name = Settings.System.getString(this.getContentResolver(), "media_button_receiver");
//        if (name != null) {
//            ComponentName componentName = ComponentName.unflattenFromString(name);
//        }
//        String packageName = componentName.getPackageName();
//        Log.i("TestService", "current use audio app = " + packageName);

        Log.i("TestService", "##################################################");
        Log.i("TestService", "isMusicActive: " + am.isMusicActive());
        Log.i("TestService", "isSpeakerphoneOn: " + am.isSpeakerphoneOn());
        Log.i("TestService", "isBluetoothScoAvailableOffCall: " + am.isBluetoothScoAvailableOffCall());
        Log.i("TestService", "isBluetoothScoOn: " + am.isBluetoothScoOn());
        String pid = am.getParameters("Active_Music_pid");
//        List<String> list = getAllPidInPlayList();
//        for (String str : list) {
            Log.i("TestService", "isPlayPid: " + pid);
//        }
    }

    public List<String> getAllPidInPlayList() {
        ArrayList<String> list = new ArrayList<>();
        Class<?> c = null;
        Object obj = null;
        try {
            c = Class.forName("android.media.AudioSystem");
             obj=c.getDeclaredConstructor().newInstance();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        Method getParameters = null;
        try {
            getParameters = c.getDeclaredMethod("getParameters",String.class);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        String pid = null;
        try {
            pid = (String) (getParameters.invoke(obj,"Active_Music_pid"));
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        if (pid == null || pid.length() <= 0) {
            return list;
        }
        String pidArray[] = pid.split(",");
        for (int i = 0; i < pidArray.length; i++) {
            if (pidArray[i] == null || pidArray[i].length() <= 0) {
                break;
            }
            Log.i(TAG, "pid = " + pidArray[i]);
            list.add(pidArray[i]);
        }
        return list;
    }

}
