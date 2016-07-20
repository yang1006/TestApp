package yll.self.testapp.compont.broadCast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import yll.self.testapp.utils.UtilsManager;

/**
 * Created by yll on 2016/1/20.
 * 有序广播接收器A
 */
public class MyOrderBroadCastA extends BroadcastReceiver{
    @Override
    public void onReceive(Context context, Intent intent) {
        UtilsManager.log("MyOrderBroadCastA onReceive");
//        if (isOrderedBroadcast()){//接收到有序广播可以终止
//            abortBroadcast();
//            UtilsManager.log("终止广播继续传递");
//        }
        if (isOrderedBroadcast()){//添加数据继续传递
            Bundle bundle = new Bundle();
            bundle.putString("extra", "来自MyOrderBroadCastA的消息");
            setResultExtras(bundle);
        }
    }
}
