package yll.self.testapp.hook.testone;

import android.util.Log;

/**
 * Created by yll on 16/8/16.
 * 实现Greet的一个代理类
 */
public class SimpleProxy implements Greet {

    private Greet greet;

    public SimpleProxy(Greet greet){
        this.greet = greet;
    }

    @Override
    public void sayHello(String name) {
        Log.e("yll", "--before method sayHello");
        if (greet != null){
            greet.sayHello(name);
        }
        Log.e("yll", "--after method sayHello");
    }

    @Override
    public void goodBye() {
        Log.e("yll", "--before method goodBye");
        if (greet != null){
            greet.goodBye();
        }
        Log.e("yll", "--after method goodBye");
    }
}
