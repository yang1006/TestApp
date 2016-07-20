package yll.self.testapp.compont.broadCast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import yll.self.testapp.utils.UtilsManager;

/**
 * Created by yll on 2016/1/20.
 * 无序广播B
 */
public class MyUnorderedBroadCastB extends BroadcastReceiver{
    @Override
    public void onReceive(Context context, Intent intent) {
        UtilsManager.log("MyUnorderedBroadCastB onReceive");
    }
}
