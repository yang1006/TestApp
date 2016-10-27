package yll.self.testapp.other.annotation;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.lang.reflect.Field;

import yll.self.testapp.R;

/**
 * Created by yll on 16/10/27.
 * 自定义annotation 代替findViewById
 */

public class CustomAnnotationActivity extends Activity {

    @InjectViewYll(id = R.id.tv_1)
    private TextView tv_1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_annotation);
        analyseInjectView();
        tv_1.setText("注解成功替换了findViewById");
    }


    /**根据注解自动解析控件*/
    private void analyseInjectView(){
        try {
            Class clazz = this.getClass();
            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields){
                InjectViewYll injectViewYll = field.getAnnotation(InjectViewYll.class);
                if (injectViewYll != null){
                    int id = injectViewYll.id();
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
