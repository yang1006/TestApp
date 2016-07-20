package yll.self.testapp.normal.mvp2.model;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by yll on 2015/12/22.
 * INetConnect的实现类，判断网络是否可用
 */
public class NetConnect implements INetConnect {

    @Override
    public boolean isNetConnect(Context context) {

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnectedOrConnecting()){
            return true;
        }
        return false;
    }
}
