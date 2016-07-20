package yll.self.testapp.compont.broadCast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import yll.self.testapp.utils.UtilsManager;

/**
 * Created by yll on 2016/1/20.
 * 有序广播接收器B
 */
public class MyOrderBroadCastB extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        UtilsManager.log("MyOrderBroadCastB onReceive");
//        if (isOrderedBroadcast()){
//            Bundle bundle = getResultExtras(true);
//            String extra = bundle.getString("extra");
//            UtilsManager.log("额外消息： "+extra);
//        }
    }
}
