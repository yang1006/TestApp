package yll.self.testapp.other;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import yll.self.testapp.utils.UtilsManager;

/**
 * Created by yll on 17/8/23.
 */

public class SurfaceViewTestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LinearLayout ll = new LinearLayout(this);
        ll.addView(new MyView(this), new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        setContentView(ll);
    }

    class MyView extends SurfaceView implements SurfaceHolder.Callback{

        private MyThread myThread;

        public MyView(Context context) {
            super(context);
            getHolder().addCallback(this);
        }

        @Override
        public void surfaceCreated(SurfaceHolder holder) {
            myThread = new MyThread(holder);
            myThread.isRun = true;
            myThread.start();
        }

        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

        }

        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {
            myThread.isRun = false;
        }


        /**内部线程*/
        class MyThread extends Thread{

            private SurfaceHolder holder;
            public boolean isRun ;
            private Paint paint;
            private Rect rect;

            MyThread(SurfaceHolder holder){
                this.holder = holder;
                paint = new Paint();
                paint.setColor(Color.WHITE);
                paint.setAntiAlias(true);
                paint.setTextSize(UtilsManager.dip2px(SurfaceViewTestActivity.this, 15));
                rect = new Rect(100, 50, 300, 250);
            }

            @Override
            public void run() {
                int count = 0;
                while (isRun){
                    Canvas c = null;
                    try {
                        synchronized (holder){
                            c = holder.lockCanvas();
                            c.drawColor(Color.BLACK);
                            c.drawRect(rect, paint);
                            c.drawText("第" + count++ + "秒", 100, 310, paint);
                            Thread.sleep(1000);
                        }
                    }catch (Exception e){

                    }finally {
                        if (c != null){
                            holder.unlockCanvasAndPost(c);
                        }
                    }
                }
            }
        }
    }
}
