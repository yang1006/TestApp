package yll.self.testapp.compont.service;

import android.app.ActivityManager;
import android.app.Service;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;

import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * Created by yll on 17/7/18.
 * 每十秒检查一次屏幕的状态 是否全屏 是否横屏
 */

public class CheckActivityService extends Service {

    private MyHandler handler = new MyHandler(CheckActivityService.this);
    private View mView;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        addView();
        handler.removeMessages(CHECK_ACTIVITY_STATUS);
        handler.sendEmptyMessage(CHECK_ACTIVITY_STATUS);
    }

    private void addView(){
        WindowManager windowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        if (mView != null){
            windowManager.removeView(mView);
        }
        mView = new View(CheckActivityService.this.getApplicationContext());
        mView.setBackgroundColor(Color.RED);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.type = WindowManager.LayoutParams.TYPE_SYSTEM_OVERLAY;
        lp.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE;
        lp.gravity = Gravity.TOP | Gravity.START;
        lp.width = 50;
        lp.height = 50;
        try {
            windowManager.addView(mView, lp);
        }catch (Exception e){
            Log.e("yll", e.toString());
        }
    }

    private static class MyHandler extends Handler{

        WeakReference<CheckActivityService> weakReference;

        MyHandler(CheckActivityService service){
            weakReference = new WeakReference<>(service);
        }

        @Override
        public void handleMessage(Message msg) {
            if (weakReference != null && weakReference.get() != null) {
                weakReference.get().handleMessage(msg);
            }
        }
    }

    private final int CHECK_ACTIVITY_STATUS = 100;
    private int counter = 0;
    private void handleMessage(Message msg){
        switch (msg.what){
            case CHECK_ACTIVITY_STATUS:
                showOrientation();
                if (counter < 100){//执行100次即可
                    counter ++;
                    handler.sendEmptyMessageDelayed(CHECK_ACTIVITY_STATUS, 10000);
                }
                break;
        }
    }

    private void showOrientation(){
        int orientation = getApplicationContext().getResources().getConfiguration().orientation;
        if (orientation == 1){
            Log.e("yll", "竖屏");
        }else if (orientation == 2){
            Log.e("yll", "横屏");
        }
        addView();
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                if (mView != null){
                    int[] top = new int[2];
                    mView.getLocationOnScreen(top);
                    Log.e("yll", "x->" + top[0] + "  y->" + top[1]);
//            if (top[1] == 0){
//                Log.e("yll", "全屏");
//            }else {
//                Log.e("yll", "非全屏");
//            }
                }
            }
        });
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1){

            try {
                UsageStatsManager mUsageStatsManager = (UsageStatsManager)getSystemService(Context.USAGE_STATS_SERVICE);
                long time = System.currentTimeMillis();
                // We get usage stats for the last 10 seconds
                List<UsageStats> stats = mUsageStatsManager.queryUsageStats(UsageStatsManager.INTERVAL_DAILY, time - 1000*10, time);
                // Sort the stats by the last time used
                if(stats != null) {
                    SortedMap<Long,UsageStats> mySortedMap = new TreeMap<Long,UsageStats>();
                    for (UsageStats usageStats : stats) {
                        mySortedMap.put(usageStats.getLastTimeUsed(),usageStats);
                    }
                    if(!mySortedMap.isEmpty()) {
                        String topPackageName =  mySortedMap.get(mySortedMap.lastKey()).getPackageName();
                        Log.e("yll", "topActivity M-> " +topPackageName);
                    }
                }
            }catch (Exception e){

            }
        }else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            final int PROCESS_STATE_TOP = 2;
            String temp = "";
            try {
                /** getRunningAppProcesses 在api 22以上就只返回自己 app 的信息了*/
                ActivityManager activityManager = (ActivityManager) getApplicationContext().getSystemService(Context.ACTIVITY_SERVICE);
                List<ActivityManager.RunningAppProcessInfo> processes = activityManager.getRunningAppProcesses();
                Field processStateField = ActivityManager.RunningAppProcessInfo.class.getDeclaredField("processState");

                for (ActivityManager.RunningAppProcessInfo process : processes) {
                    if (process.importance <= ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND
                                    &&process.importanceReasonCode == 0) {
                        int state = processStateField.getInt(process);
                        if (state == PROCESS_STATE_TOP){
                            temp = "";
                            for (String s : process.pkgList) {
                                temp += s + "  ";
                            }
                            Log.e("yll", "pkgList->" + temp);
                        }
                    }
                }
            } catch (Exception e) {}

        }else {
            /**5.0 以前能拿到 , 之后只能拿到自己app的信息*/
            ActivityManager activityManager = (ActivityManager) getApplicationContext().getSystemService(Context.ACTIVITY_SERVICE);
            List<ActivityManager.RunningTaskInfo> runningTaskInfos = activityManager.getRunningTasks(1);
            if (runningTaskInfos != null){
                ActivityManager.RunningTaskInfo runningTaskInfo = runningTaskInfos.get(0);
                Log.e("yll", " topActivity 5.0以下->" + runningTaskInfo.topActivity);
            }
        }
    }


}
