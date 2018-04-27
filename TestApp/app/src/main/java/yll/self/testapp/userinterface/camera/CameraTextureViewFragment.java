package yll.self.testapp.userinterface.camera;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Context;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;

import yll.self.testapp.R;

public class CameraTextureViewFragment extends CameraPreviewFragment implements TextureView.SurfaceTextureListener {

    private TextureView mTexture;
    private Camera mCamera;


    public CameraTextureViewFragment(Context context) {
        super(context);
    }

    @Override
    public void init() {
        rootView = LayoutInflater.from(context).inflate(R.layout.fragment_camera_texture, null);
        mTexture = rootView.findViewById(R.id.mTexture);
        rootView.findViewById(R.id.btn_change).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PropertyValuesHolder valuesHolder = PropertyValuesHolder.ofFloat("translationX", 0.0f, 0.0f);
                PropertyValuesHolder valuesHolder1 = PropertyValuesHolder.ofFloat("scaleX", 1.0f, 0.3f,1.0f);
                PropertyValuesHolder valuesHolder4 = PropertyValuesHolder.ofFloat("scaleY", 1.0f, 0.3f,1.0f);
                PropertyValuesHolder valuesHolder2 = PropertyValuesHolder.ofFloat("rotationX", 0.0f, 2 * 360.0f, 0.0F);
                PropertyValuesHolder valuesHolder5 = PropertyValuesHolder.ofFloat("rotationY", 0.0f, 2 * 360.0f, 0.0F);
                PropertyValuesHolder valuesHolder3 = PropertyValuesHolder.ofFloat("alpha", 1.0f, 0.7f, 1.0F);
                ObjectAnimator objectAnimator = ObjectAnimator.ofPropertyValuesHolder(mTexture, valuesHolder, valuesHolder1, valuesHolder2, valuesHolder3,valuesHolder4,valuesHolder5);
                objectAnimator.setDuration(5000).start();
            }
        });

        mTexture.setSurfaceTextureListener(this);
    }

    @Override
    public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
        try {
            mCamera = Camera.open(0);
            mCamera.setDisplayOrientation(90);
            mCamera.setPreviewTexture(mTexture.getSurfaceTexture());
            mCamera.startPreview();
        }catch (Exception e){}
    }

    @Override
    public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {

    }

    @Override
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
        if (mCamera != null){
            mCamera.stopPreview();
            mCamera.release();
            mCamera = null;
        }
        return false;
    }

    @Override
    public void onSurfaceTextureUpdated(SurfaceTexture surface) {

    }
}
