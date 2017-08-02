package yll.self.testapp.compont.wallpaper;

import android.hardware.Camera;
import android.service.wallpaper.WallpaperService;
import android.view.MotionEvent;
import android.view.SurfaceHolder;

import java.io.IOException;

/**
 * Created by yll on 17/8/2.
 */

public class CameraLiveWallpaper extends WallpaperService {

    private Camera camera;

    @Override
    public Engine onCreateEngine() {
        return new CameraEngine();
    }


    class CameraEngine extends Engine implements Camera.PreviewCallback{
        @Override
        public void onPreviewFrame(byte[] data, Camera camera) {
            camera.addCallbackBuffer(data);
        }

        @Override
        public void onCreate(SurfaceHolder surfaceHolder) {
            super.onCreate(surfaceHolder);
            startPreview();
            setTouchEventsEnabled(true);
        }

        @Override
        public void onTouchEvent(MotionEvent event) {
            super.onTouchEvent(event);
        }

        @Override
        public void onDestroy() {
            super.onDestroy();
            stopPreview();
        }

        @Override
        public void onVisibilityChanged(boolean visible) {
            super.onVisibilityChanged(visible);
            if (visible){
                startPreview();
            }else {
                stopPreview();
            }
        }

        /**开始预览*/
        public void startPreview(){
            camera = Camera.open();
            camera.setDisplayOrientation(90);

            try {
                camera.setPreviewDisplay(getSurfaceHolder());
            }catch (IOException e) {
                e.printStackTrace();
            }
            camera.startPreview();
        }

        public void stopPreview(){
            if (camera != null){
                try {
                    camera.stopPreview();
                    camera.setPreviewCallback(null);
                    camera.release();
                }catch (Exception e){

                }
                camera = null;
            }
        }
    }

}
