package yll.self.testapp.hook;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import yll.self.testapp.R;
import yll.self.testapp.hook.testone.Greet;
import yll.self.testapp.hook.testone.GreetImpl;
import yll.self.testapp.hook.testone.MyInvocationHandler;
import yll.self.testapp.hook.testone.SimpleProxy;

/**
 * Created by yll on 16/8/16.
 *
 */
public class HookTestActivity extends Activity implements View.OnClickListener {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hook_test);
        getWindow().addFlags(Window.FEATURE_NO_TITLE);
        init();
    }

    private void init(){
        findViewById(R.id.tv_test1).setOnClickListener(this);
        findViewById(R.id.tv_test2).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_test1:
                /**第一种方式,使用代理类,可以对GreetImpl的方法增加附加操作,
                 * 这种方式一个类就要写一个代理类,大型项目不太现实*/
                Greet greet = new SimpleProxy(new GreetImpl());
                greet.sayHello("Jack");
                greet.goodBye();
                break;
            case R.id.tv_test2:
                /**使用InvocationHandler实现动态代理*/
                Greet temp = new GreetImpl();
                Greet greet1 = (Greet) MyInvocationHandler.newInstance(temp);
                greet1.sayHello("Rose");
                greet1.goodBye();
                break;
        }
    }



}
