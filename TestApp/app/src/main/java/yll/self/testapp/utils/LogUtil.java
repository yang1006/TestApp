package yll.self.testapp.utils;

import android.util.Log;

/**
 * Created by yll on 17/9/26.
 * 打印管理
 */

public class LogUtil {

    private static final String APP_TAG = "TestApp ";

    public static void println(String msg){
        System.out.println(msg);
    }

    public static void yll(String msg){
        Log.e("yll", msg);
    }

    public static void d(String msg){
        Log.d("yll", msg);
    }

    public static void d(String tag, String msg) {
        Log.d(APP_TAG + tag, msg);
    }

    public static void e(String tag, String msg) {
        Log.e(APP_TAG + tag, msg);
    }
}
