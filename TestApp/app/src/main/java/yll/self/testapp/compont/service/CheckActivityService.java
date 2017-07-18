package yll.self.testapp.compont.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;

import java.lang.ref.WeakReference;

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
        mView = new View(CheckActivityService.this.getApplicationContext());
        mView.setBackgroundColor(Color.RED);
        WindowManager windowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.type = WindowManager.LayoutParams.TYPE_SYSTEM_OVERLAY;
        lp.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE;
        lp.gravity = Gravity.TOP | Gravity.START;
        lp.width = 100;
        lp.height = 100;
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
        Log.e("yll", "1竖2横 orientation->" + getApplicationContext().getResources().getConfiguration().orientation);
        if (mView != null){
            int[] top = new int[2];
            mView.getLocationOnScreen(top);
            if (top[1] == 0){
                Log.e("yll", "全屏");
            }else {
                Log.e("yll", "非全屏");
            }
        }
    }

}
