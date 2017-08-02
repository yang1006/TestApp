package yll.self.testapp.userinterface;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import yll.self.testapp.R;
import yll.self.testapp.userinterface.animation.AnimationActivity;
import yll.self.testapp.userinterface.animation.ChangeAniActivity;
import yll.self.testapp.userinterface.animation.ClickBgActivity;
import yll.self.testapp.userinterface.lottie.LottieActivity;
import yll.self.testapp.userinterface.animation.SlidingPaneActivity;

/**
 * Created by yll on 2016/1/20.
 */
public class UIAndAniActivity extends Activity implements View.OnClickListener {
    private Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = UIAndAniActivity.this;
        setContentView(R.layout.activity_ui_ani);
        init();
    }

    private void init(){
        findViewById(R.id.tv_click).setOnClickListener(this);
        findViewById(R.id.tv_animate1).setOnClickListener(this);
        findViewById(R.id.tv_3).setOnClickListener(this);
        findViewById(R.id.tv_4).setOnClickListener(this);
        findViewById(R.id.tv_5).setOnClickListener(this);
        findViewById(R.id.tv_show_all).setOnClickListener(this);
        findViewById(R.id.tv_unlock_view).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_click:
                Intent intent = new Intent(context, ClickBgActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_animate1:
                startActivity(new Intent(context, ChangeAniActivity.class));
                break;
            case R.id.tv_3:
                startActivity(new Intent(context, AnimationActivity.class));
                break;
            case R.id.tv_4:
                startActivity(new Intent(context, SlidingPaneActivity.class));
                break;
            case R.id.tv_5:
                startActivity(new Intent(context, LottieActivity.class));
                break;
            case R.id.tv_show_all:
                startActivity(new Intent(context, ShowAllTextActivity.class));
                break;
            case R.id.tv_unlock_view:
                startActivity(new Intent(context, TestViewActivity.class));
                break;
        }
    }
}
