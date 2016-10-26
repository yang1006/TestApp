package yll.self.testapp.hook;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import java.lang.reflect.Method;

/**
 * Created by yll on 16/8/15.
 *
 * 通过代理ActivityThread类的mInstrumentation变量,
 * 实现每次启动一个Activity都答应一句话
 * hook要尽量早,所以直接放在了Application初始化的时候(HookApplication)
 */
public class InstrumentationProxy extends Instrumentation {
    private Instrumentation m_base;

    public InstrumentationProxy(Instrumentation m_base){
        this.m_base = m_base;
    }

    /**去hook Instrumentation的execStartActivity方法*/
    public ActivityResult execStartActivity(
            Context who, IBinder contextThread, IBinder token, Activity target,
            Intent intent, int requestCode, Bundle options){

        /**在所有activity启动前都会打印该语句*/
        Log.e("yll", "InstrumentationProxy.execStartActivity");

        // 开始调用原始的方法, 调不调用随你,但是不调用的话, 所有的startActivity都失效了.
        // 由于这个方法是隐藏的,因此需要使用反射调用;首先找到这个方法
        try {
            Method execStartActivity = Instrumentation.class.getDeclaredMethod(
                    "execStartActivity",
                    Context.class, IBinder.class, IBinder.class, Activity.class,
                    Intent.class, int.class, Bundle.class);
            execStartActivity.setAccessible(true);
            return (ActivityResult) execStartActivity.invoke(m_base, who,
                    contextThread, token, target, intent, requestCode, options);
        } catch (Exception e) {
            //需要手动适配
            throw new RuntimeException("do not support!!! pls adapt it");
        }
    }
}
