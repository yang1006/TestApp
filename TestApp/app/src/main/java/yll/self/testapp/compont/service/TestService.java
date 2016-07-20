package yll.self.testapp.compont.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import yll.self.testapp.utils.UtilsManager;

/**
 * Created by yll on 2016/1/19.
 * 测试service生命周期
 */
public class TestService extends Service{

    @Override
    public IBinder onBind(Intent intent) {
        UtilsManager.log("TestService onBind");
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        UtilsManager.log("TestService onCreate");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        UtilsManager.log("TestService onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        UtilsManager.log("TestService onDestroy");
    }

    @Override
    public boolean onUnbind(Intent intent) {
        UtilsManager.log("TestService onUnbind");
        return super.onUnbind(intent);
    }

    @Override
    public void onRebind(Intent intent) {
        super.onRebind(intent);
        UtilsManager.log("TestService onRebind");
    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
        UtilsManager.log("TestService onStart");
    }
}
