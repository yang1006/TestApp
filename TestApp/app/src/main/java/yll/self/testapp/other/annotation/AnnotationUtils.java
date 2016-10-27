package yll.self.testapp.other.annotation;

import android.util.Log;

/**
 * Created by yll on 16/10/27.
 */

public class AnnotationUtils {

    @TestAnnotation("yll")
    public void showName(){
        Log.e("yll", "this is function showName!");
    }
}
