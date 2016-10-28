package yll.self.testapp.other.annotation;

import android.widget.TextView;

import yll.self.testapp.R;

/**
 * Created by yll on 16/10/28.
 */

public class CustomAnnotationActivity extends BaseActivity {
    @Override
    protected int getLayoutId() {
        return R.layout.activity_custom_annotation;
    }

    @InjectView(id = R.id.tv_1)
    private TextView tv_1;


    @Override
    protected void onResume() {
        super.onResume();
        tv_1.setText("已成功代替findViewById方法");
    }
}
