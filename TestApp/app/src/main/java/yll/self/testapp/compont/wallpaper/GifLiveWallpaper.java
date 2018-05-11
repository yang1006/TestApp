package yll.self.testapp.compont.wallpaper;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Handler;
import android.service.wallpaper.WallpaperService;
import android.util.Log;
import android.view.SurfaceHolder;

import java.io.IOException;

import pl.droidsonroids.gif.GifDrawable;
import yll.self.testapp.R;

public class GifLiveWallpaper extends WallpaperService {

    GifDrawable gifDrawable;
    public static int GIF_RES_ID = R.drawable.test;

    @Override
    public Engine onCreateEngine() {
        return new GifEngine();
    }

    class GifEngine extends Engine {

        private Handler mHandler = new Handler();

        private Runnable drawTask = new Runnable() {
            @Override
            public void run() {
                drawFrame();
            }
        };

        @Override
        public void onCreate(SurfaceHolder surfaceHolder) {
            super.onCreate(surfaceHolder);
            Log.e("yll", "GifLiveWallpaper onCreate");
            try {
                gifDrawable = new GifDrawable(getResources(), GIF_RES_ID);
                mHandler.post(drawTask);
            } catch (IOException e) {
                e.printStackTrace();
                gifDrawable = null;
            }
        }

        @Override
        public void onDestroy() {
            super.onDestroy();
            mHandler.removeCallbacks(drawTask);
            if (gifDrawable != null){
                gifDrawable.recycle();
                gifDrawable = null;
            }
        }

        @Override
        public void onVisibilityChanged(boolean visible) {
            super.onVisibilityChanged(visible);
            if (visible){
                mHandler.removeCallbacks(drawTask);
                mHandler.post(drawTask);
            }else {
                mHandler.removeCallbacks(drawTask);
            }
        }

        private void drawFrame(){
            SurfaceHolder holder = getSurfaceHolder();
            Canvas canvas = null;
            try {
                canvas = holder.lockCanvas();
                Bitmap bitmap = gifDrawable.getCurrentFrame();
                if (bitmap == null){
                    Log.e("yll", "Bitmap 为null");
                    return;
                }
                Rect rect = new Rect();
                rect.left = rect.top = 0;
                rect.bottom = canvas.getHeight();
                rect.right = canvas.getWidth();
                canvas.drawBitmap(bitmap, null, rect, null);
                holder.unlockCanvasAndPost(canvas);
                mHandler.postDelayed(drawTask, 50);
            }catch (Exception e){

            }

        }

    }
}
