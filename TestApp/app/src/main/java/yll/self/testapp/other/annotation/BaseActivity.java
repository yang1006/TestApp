package yll.self.testapp.other.annotation;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import java.lang.reflect.Field;

/**
 * Created by yll on 16/10/27.
 * 自定义annotation 代替findViewById
 */

public abstract class BaseActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        analyseInjectView();
    }

    /**返回布局id*/
    protected abstract int getLayoutId();

    /**根据注解自动解析控件*/
    private void analyseInjectView(){
        try {
            Class clazz = this.getClass();
            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields){
                InjectView injectView = field.getAnnotation(InjectView.class);
                if (injectView != null){
                    int id = injectView.id();
                    Log.e("yll", "id->"+id);
                    if (id > 0){
                        field.setAccessible(true);
                        field.set(this, findViewById(id));
                    }
                }
            }
        }catch (Exception e){}
    }
}
