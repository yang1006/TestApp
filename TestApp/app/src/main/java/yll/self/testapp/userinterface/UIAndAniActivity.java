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
        }
    }
}
