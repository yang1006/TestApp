package yll.self.testapp.userinterface.camera;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RelativeLayout;

import yll.self.testapp.R;

public class CameraPreviewActivity extends AppCompatActivity {

    public static final String SurfaceView = "SurfaceView";
    public static final String GlSurfaceView = "GlSurfaceView";
    public static final String SurfaceTexture = "SurfaceTexture";
    public static final String TextureView = "TextureView";

    public static final String KeyType = "type";

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
        CameraPreviewFragment fragment;

        switch (type){
            case SurfaceView:
                fragment = new CameraSurfaceViewFragment(this);
                break;
            case TextureView:
                fragment = new CameraTextureViewFragment(this);
                break;

            default:
                fragment = new CameraSurfaceViewFragment(this);
                break;
        }

        rl_container.addView(fragment.getRootView());
    }
}
