package yll.self.testapp.jni;

/**
 * Created by yll on 18/3/16.
 */

public class JniUtil {

    static {
        System.loadLibrary("jniutil");
    }


    public native String test();
}
