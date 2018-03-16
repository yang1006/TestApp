package yll.self.testapp.jni;

/**
 * Created by yll on 18/3/16.
 */

public class MyJni {

    static {
        System.loadLibrary("myjni");
    }

    public native void set(String s);
    public native String get();
}
