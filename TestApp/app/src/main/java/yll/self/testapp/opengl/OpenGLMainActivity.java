package yll.self.testapp.opengl;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import yll.self.testapp.R;

public class OpenGLMainActivity extends Activity implements View.OnClickListener {
    private Activity activity;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opengl_main);
        initViews();
        activity = this;
    }

    private void initViews(){
        findViewById(R.id.tv_first_triangle).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_first_triangle:
                startActivity(new Intent(activity, HelloTriangleActivity.class));
                break;
        }
    }
}
