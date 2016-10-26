package yll.self.testapp.hook.testone;

import android.util.Log;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by yll on 16/8/18.
 */
public class MyInvocationHandler implements InvocationHandler {

    private Object object;

    public static Object newInstance(Object o){
        return Proxy.newProxyInstance(o.getClass().getClassLoader(),
                o.getClass().getInterfaces(), new MyInvocationHandler(o));
    }

    private MyInvocationHandler(Object o){
        /**接口的实现：GreetImpl*/
        this.object = o;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object result = null;
        try {
            /**自定义的数据*/
            Log.e("yll", "--before method " + method.getName());
            //调用GreetImpl中方法
            result = method.invoke(object, args);
        }catch (Exception e){
            Log.e("yll", "e->"+e.toString());
        }finally {
            Log.e("yll", "--after method " + method.getName());
        }
        return result;
    }

}
