package yll.self.testapp.utils;

import android.content.Context;
import android.graphics.Paint;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;

/**
 * Created by yll on 2016/1/19.
 */
public class UtilsManager {

    public static final String FILE_PATH = Environment.getExternalStorageDirectory() + "/testApp";

    public static void log(String s){
        Log.e("yll", s);
    }

    public static void log(String tag, String msg){
        if (TextUtils.isEmpty(tag)){
            Log.e("yll", msg);
        }else {
            Log.e(tag, msg);
        }
    }

    /**dp 转 px*/
    public static int dip2px(Context context, float dp){
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    /** 计算指定画笔下指定字符串需要的宽度int[0]*/
    public static int getTheTextNeedWidth(Paint thepaint, String text) {
        float[] widths = new float[text.length()];
        thepaint.getTextWidths(text, widths);
        int length = widths.length, nowLength = 0, a;
        for (a = 0; a < length; a++) {
            nowLength += widths[a];
        }
        return nowLength;
    }
}
