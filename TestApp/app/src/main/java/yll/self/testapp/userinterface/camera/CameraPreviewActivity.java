package yll.self.testapp.userinterface.camera;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.RelativeLayout;

import yll.self.testapp.R;

/*
* https://blog.csdn.net/qq_32175491/article/details/79755424
* */
public class CameraPreviewActivity extends AppCompatActivity {

    public static final String SurfaceView = "SurfaceView";
    public static final String GlSurfaceView = "GlSurfaceView";
    public static final String TextureView = "TextureView";

    public static final String KeyType = "type";

    CameraPreviewFragment fragment;

    public static void open(Context activity, String type){
        Intent intent = new Intent(activity, CameraPreviewActivity.class);
        intent.putExtra(KeyType, type);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_preview);
        RelativeLayout rl_container = findViewById(R.id.rl_container);

        String type = getIntent().getStringExtra(KeyType);


        switch (type){
            case SurfaceView:
                fragment = new CameraSurfaceViewFragment(this);
                break;
            case TextureView:
                fragment = new CameraTextureViewFragment(this);
                break;
            case GlSurfaceView:
                fragment = new CameraGLSurfaceViewFragment(this);
                break;
            default:
                fragment = new CameraSurfaceViewFragment(this);
                break;
        }

        rl_container.addView(fragment.getRootView());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (fragment != null){
            fragment.onDestroy();
        }
    }
}
