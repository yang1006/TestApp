package yll.self.testapp.other.annotation;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Receiver;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.res.StringRes;
import org.androidannotations.annotations.sharedpreferences.Pref;

import java.lang.reflect.Method;

import yll.self.testapp.R;

/**
 * Created by yll on 16/10/27.
 * 测试各种AndroidAnnotation的注解用法
 * 全部用法 https://github.com/androidannotations/androidannotations/wiki/AvailableAnnotations
 *
 * 想要在普通的类中也用上注解，只需在类名加上@EBean
     @EBean
     public class MyClass {
     @UiThread
     void updateUI() {
     }

     使用时，声明：
     @EActivity
     public class MyActivity extends Activity {
     @Bean
     MyClass myClass;
     }

     @EBean注解的类，只能有一个构造方法，且这个构造方法必须无参数或者只有context参数。
     在activity等组件内声明了后，不用再去new这个类，否则会出错。
 */

@EActivity(R.layout.activity_annotation_test)
public class AnnotationActivity extends Activity {

    @StringRes(R.string.hello_world)
    String hello_world;

    @ViewById
    TextView tv_1;

//    @Extra
//    TextView tv_2;

    /**sharePreference用法*/
    @Pref
    MyPrefs_ myPrefs;

    /**绑定点击事件*/
    @Click
    void tv_1(){
        toast(hello_world);
    }

    @Click
    void tv_2(){
        toast(myPrefs.shockLevel().get()+"");
        myPrefs.shockLevel().put(myPrefs.shockLevel().get() + 1);
        analysisAnnotation();
    }

    @Click
    void tv_3(){
        startActivity(new Intent(this, CustomAnnotationActivity.class));
    }

    @AfterViews
    /**当View相关的成员变量初始化完毕后，会调用拥有@AfterViews注解的方法，可以在里面初始化一些界面控件等。*/
    void on1AfterViews(){
        if (myPrefs.isFirstIn().get()) {
            tv_1.setText(hello_world);
            myPrefs.isFirstIn().put(false);
        }
//        tv_2.setText("2222");
    }

    /**直接在异步线程执行的方法*/
    @Background
    void doSomeThing(){

    }

    /**在UI线程执行的方法 与异步线程 的交互就是方法直接的相互调用，不用再使用Handler去发送接收Message了。*/
    @UiThread
    void toast(String s){
        Toast.makeText(this, s, Toast.LENGTH_LONG).show();
    }

    /**广播接收*/
    @Receiver(actions = "XXXXXX")
    public void updateSomething(){
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /** 自定义注解 2:执行被TestAnnotation 注解的方法,取出value并打印*/
    private void analysisAnnotation(){

        AnnotationUtils annotationUtils = new AnnotationUtils();
        Class<AnnotationUtils> clazz = AnnotationUtils.class;
        try {
            Method showName = clazz.getDeclaredMethod("showName");
            if (showName.isAnnotationPresent(TestAnnotation.class)){
                showName.invoke(annotationUtils);
                TestAnnotation testAnnotation = showName.getAnnotation(TestAnnotation.class);
                String value = testAnnotation.value();
                Log.e("yll", "this is TestAnnotation's value->"+ value);
            }
        }catch (Exception e){

        }
    }
}
