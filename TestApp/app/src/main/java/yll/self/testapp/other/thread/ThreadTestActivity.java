package yll.self.testapp.other.thread;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.widget.TextView;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import yll.self.testapp.R;

/**
 * Created by yll on 17/9/12.
 * 线程和线程池一些测试
 */

public class ThreadTestActivity extends AppCompatActivity {
    private TextView tv_0, tv_1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_thread_test);
        tv_0 = (TextView) findViewById(R.id.tv_0);
        tv_1 = (TextView) findViewById(R.id.tv_1);
        testThread();
        testHandlerThread();
        testIntentService();
    }

    private void testThread(){
        /** 测试handler.handleMessage 运行在哪个线程 */
        tv_0.setText("" + Thread.currentThread());
        Log.e("yll", "thread->"+Thread.currentThread()+ " "+Thread.currentThread().getId());
        new Thread(new LopperThread()).start();
    }


    private class LopperThread extends Thread{
        public Handler handler;
        @Override
        public void run() {
            Looper.prepare();
            handler = new Handler(Looper.myLooper()){
                @Override
                public void handleMessage(Message msg) {
                    tv_0.append("\n" + Thread.currentThread());
                    Log.e("yll", "thread->" + Thread.currentThread() + " " +
                            Thread.currentThread().getId());
                }
            };
            handler.sendEmptyMessage(1);
            //loop在哪个线程执行，这handler.handleMessage就在哪个线程执行
            Looper.loop();
        }
    }

    private final int START_GET_DATA = 1000;
    private HandlerThread handlerThread;  //其实就是一个帮你创建了带Looper的线程
    private Handler handler;              //使用 HandlerThread 的Looper的handler
    /** 测试HandlerThread 使用*/
    private void testHandlerThread(){
        handlerThread = new HandlerThread("testHandlerThread");
        handlerThread.start();
        handler = new Handler(handlerThread.getLooper()){
            @Override
            public void handleMessage(Message msg) {
                //运行在 handlerThread 线程中
                super.handleMessage(msg);
                if (msg.what == START_GET_DATA){
                    checkForUpdate();
                    handler.sendEmptyMessageDelayed(START_GET_DATA, 1000);
                }
            }
        };
        handler.sendEmptyMessage(START_GET_DATA);
    }

    /**
     * 模拟从服务器解析数据
     */
    private void checkForUpdate() {
        try {
            //模拟耗时
            Thread.sleep(1000);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    String result = "实时更新数据->：<font color='red'>%d</font>";
                    result = String.format(result, (int) (Math.random() * 3000 + 1000));
                    tv_1.setText(Html.fromHtml(result));
                }
            });
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (handlerThread != null){
            handlerThread.quit();
        }
    }

    private void testIntentService(){
        Intent intent = new Intent(this, MyIntentService.class);
        intent.putExtra("task_action", "action 1");
        startService(intent);

        intent.putExtra("task_action", "action 2");
        startService(intent);

        intent.putExtra("task_action", "action 3");
        startService(intent);
    }

    /**4种线程池的典型用法*/
    private void test4Executors(){
        Runnable command = new Runnable() {
            @Override
            public void run() {
                SystemClock.sleep(2000);
                Log.e("yll", "command run");
            }
        };

        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(4);
        fixedThreadPool.execute(command);

        ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
        cachedThreadPool.execute(command);


        ScheduledExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(4);
        //2000ms后自行command
        scheduledThreadPool.schedule(command, 2000, TimeUnit.MILLISECONDS);
        //延迟1000ms后，每隔2000ms执行一次command
        scheduledThreadPool.scheduleAtFixedRate(command, 1000, 2000, TimeUnit.MILLISECONDS);

        ExecutorService singleThreadPool = Executors.newSingleThreadExecutor();
        singleThreadPool.execute(command);
    }
}
