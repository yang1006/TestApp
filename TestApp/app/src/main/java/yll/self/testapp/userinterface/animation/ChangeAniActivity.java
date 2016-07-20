package yll.self.testapp.userinterface.animation;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import yll.self.testapp.R;

/**
 * Created by yll on 2016/2/19.
 * 在父布局加上 android:animateLayoutChanges="true" 后，如果触发了layout方法
 * （比如它的子View设置为GONE）
 * ，系统就会自动帮你加上布局改变时的动画特效！
 */
public class ChangeAniActivity extends Activity implements View.OnClickListener {

    private LinearLayout ll_child1;
    private boolean isShow = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_ani);
        init();
    }

    private void init(){
        ll_child1 = (LinearLayout) findViewById(R.id.ll_child1);
        findViewById(R.id.btn_hide_show).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_hide_show){
            if (isShow){
                ll_child1.setVisibility(View.GONE);
            }else {
                ll_child1.setVisibility(View.VISIBLE);
            }
            isShow = !isShow;
        }
    }
}
