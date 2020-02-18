package yll.self.testapp.other.thread;

import android.app.IntentService;
import android.content.Intent;
import android.os.SystemClock;
import androidx.annotation.Nullable;
import android.util.Log;

/**
 * Created by yll on 17/9/15.
 * 测试IntentService
 * 后台执行耗时的任务，任务执行完后自动停止，因为是Service所以适合执行高优先级的后台任务
 * IntentService 封装了HandlerThread 和 Handler
 */

public class MyIntentService extends IntentService {

    private static final String TAG = "MyIntentService";
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     */
    public MyIntentService() {
        super(TAG);
    }

    /**这里的intent就是startService时传入的intent
     * 由于Looper处理消息时顺序的，所以这里执行也是顺序的
     * 在子线程中运行*/
    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent != null) {
            String action = intent.getStringExtra("task_action");
            Log.e("yll", "action->" + action + " Thread->" + Thread.currentThread());
            SystemClock.sleep(3000);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        /**任务处理完了才会执行*/
        Log.e("yll", "MyIntentService onDestroy");
    }
}
