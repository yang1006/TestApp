package yll.self.testapp.compont.broadCast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import yll.self.testapp.utils.UtilsManager;

/**
 * Created by yll on 2016/1/20.
 * 无须广播A
 */
public class MyUnorderedBroadCastA extends BroadcastReceiver{
    @Override
    public void onReceive(Context context, Intent intent) {
        UtilsManager.log("MyUnorderedBroadCastA onReceive");
    }
}
