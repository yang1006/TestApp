package yll.self.testapp.other.tts;

import android.app.Activity;
import android.speech.tts.TextToSpeech;
import android.util.Log;

import java.util.HashMap;
import java.util.Locale;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by yll on 17/6/30.
 * tts 工具类
 */

public class TTSUtils implements TextToSpeech.OnInitListener {

    public static final String TAG = "TTSUtils";

    private final TextToSpeech mTextToSpeech;
    private final ConcurrentLinkedQueue<String> mBufferedMsgs;
    private Activity mActivity;
    private boolean mIsReady;
    private OnSetLanguageListener listener;

    public TTSUtils(Activity activity){
        this.mActivity = activity;
        mBufferedMsgs = new ConcurrentLinkedQueue<>();
        mTextToSpeech = new TextToSpeech(this.mActivity, this);
    }


    @Override
    public void onInit(int status) {
        Log.e(TAG, "onInit->" + status);
        if (status == TextToSpeech.SUCCESS){
            int result = mTextToSpeech.setLanguage(Locale.CHINA);
            if (listener != null){
                listener.onGetResult(result);
            }
            if (result == TextToSpeech.LANG_NOT_SUPPORTED || result == TextToSpeech.LANG_MISSING_DATA){
                return;
            }
            synchronized (this){
                mIsReady = true;
                for (String msg : mBufferedMsgs){
                    speakText(msg);
                }
                mBufferedMsgs.clear();
            }
        }
    }

    /**使用完释放资源*/
    public void release(){
        synchronized (this){
            mTextToSpeech.shutdown();
            mIsReady = false;
        }
    }

    /**添加一个文字消息*/
    public void addNewMessage(String msg){
        synchronized (this){
            if (mIsReady){
                speakText(msg);
            }else {
                mBufferedMsgs.add(msg);
            }
        }
    }

    private void speakText(String msg){
        Log.e(TAG, " speakText->"+msg);
        HashMap<String, String> params = new HashMap<>();
        params.put(TextToSpeech.Engine.KEY_PARAM_STREAM, "STREAM_NOTIFICATION");
        mTextToSpeech.speak(msg, TextToSpeech.QUEUE_ADD, params);
        mTextToSpeech.playSilence(100, TextToSpeech.QUEUE_ADD, params);
    }

    public void setListener(OnSetLanguageListener listener) {
        this.listener = listener;
    }

    public interface OnSetLanguageListener{
        void onGetResult(int result);
    }
}
