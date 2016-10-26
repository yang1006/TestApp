package yll.self.testapp.hook.testone;

import android.util.Log;

/**
 * Created by yll on 16/8/16.
 * Greet实现类
 */
public class GreetImpl implements Greet {
    @Override
    public void sayHello(String name) {
        Log.e("yll", "sayHello " + name);
    }

    @Override
    public void goodBye() {
        Log.e("yll", "Good bye.");
    }
}
