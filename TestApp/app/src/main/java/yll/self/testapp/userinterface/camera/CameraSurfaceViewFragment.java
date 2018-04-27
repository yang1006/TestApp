package yll.self.testapp.userinterface.camera;


import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Context;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import yll.self.testapp.R;

public class CameraSurfaceViewFragment extends CameraPreviewFragment implements SurfaceHolder.Callback {


    private SurfaceView mSurfaceView;

    public SurfaceHolder mHolder;
    private Camera mCamera;
    private Camera.Parameters mParameters;

    public CameraSurfaceViewFragment(Context context){
        super(context);
    }

    @Override
    public void init() {
        rootView  = LayoutInflater.from(context).inflate(R.layout.fragment_camera_surfaceview, null);
        mSurfaceView = rootView.findViewById(R.id.mSurface);
        rootView.findViewById(R.id.btn_change).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PropertyValuesHolder valuesHolder = PropertyValuesHolder.ofFloat("scaleX", 1f, 0.5f);
                PropertyValuesHolder valuesHolder2 = PropertyValuesHolder.ofFloat("scaleY", 1f, 0.5f);
                ObjectAnimator objectAnimator = ObjectAnimator.ofPropertyValuesHolder(mSurfaceView, valuesHolder, valuesHolder2);
                objectAnimator.setDuration(3000).start();
            }
        });

        mHolder = mSurfaceView.getHolder();
        mHolder.addCallback(this);
        mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
    }

    @Override
    public View getRootView() {
        return rootView;
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        try {
            mCamera = Camera.open(0);
            mCamera.setDisplayOrientation(90);
            mCamera.setPreviewDisplay(holder);
            mCamera.startPreview();
        }catch (Exception e){
            Log.e("yll", "surfaceCreated " + e.getMessage());
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        try {
            mCamera.autoFocus(new Camera.AutoFocusCallback() {
                @Override
                public void onAutoFocus(boolean success, Camera camera) {
                    if (success) {
                        mParameters = mCamera.getParameters();
                        mParameters.setPictureFormat(PixelFormat.JPEG); //图片输出格式
//                    mParameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);//预览持续发光
                        mParameters.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);//持续对焦模式
                        mCamera.setParameters(mParameters);
                        mCamera.startPreview();
                        mCamera.cancelAutoFocus();
                    }
                }
            });
        }catch (Exception e){
            Log.e("yll", "surfaceChanged " + e.getMessage());
        }

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        if (mCamera != null){
            mCamera.stopPreview();
            mCamera.release();
            mCamera = null;
        }
    }
}
