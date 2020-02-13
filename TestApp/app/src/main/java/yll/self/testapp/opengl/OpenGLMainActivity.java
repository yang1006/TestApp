package yll.self.testapp.opengl;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import yll.self.testapp.BaseActivity;
import yll.self.testapp.R;

public class OpenGLMainActivity extends BaseActivity implements View.OnClickListener {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opengl_main);
        initViews();
    }

    private void initViews(){
        findViewById(R.id.tv_first_triangle).setOnClickListener(this);
        findViewById(R.id.tv_6_3).setOnClickListener(this);
        findViewById(R.id.tv_first_rectangel).setOnClickListener(this);
        findViewById(R.id.tv_draw_texture).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_first_triangle:
                startActivity(new Intent(mActivity, HelloTriangleActivity.class));
                break;
            case R.id.tv_6_3:
                startActivity(new Intent(mActivity, Chapter6Example6_3Activity.class));
                break;
            case R.id.tv_first_rectangel:
                startActivity(new Intent(mActivity, HelloRectangleActivity.class));
                break;
            case R.id.tv_draw_texture:
                startActivity(new Intent(mActivity, HelloTextureActivity.class));
                break;
            default:
                break;
        }
    }
}
