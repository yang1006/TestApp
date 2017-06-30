package yll.self.testapp.other.tts;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import yll.self.testapp.R;

/**
 * Created by yll on 17/6/30.
 * 文字转语音
 */

public class TTSActivity extends AppCompatActivity {

    private EditText edt_input;
    private TextView tv_speak;
    private TTSUtils ttsUtils;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tts);


        edt_input = (EditText) findViewById(R.id.edt_input);
        tv_speak = (TextView) findViewById(R.id.tv_speak);
        tv_speak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!TextUtils.isEmpty(edt_input.getText())){
                    ttsUtils.addNewMessage(edt_input.getText().toString().trim());
                    edt_input.setText("");
                }
            }
        });
        ttsUtils = new TTSUtils(TTSActivity.this);
        ttsUtils.setListener(new TTSUtils.OnSetLanguageListener() {
            @Override
            public void onGetResult(int result) {
                boolean isSupport = true;
                if (result == TextToSpeech.LANG_NOT_SUPPORTED ){
                    Log.e(TTSUtils.TAG, "LANG_NOT_SUPPORTED " + "语音不支持");
                    isSupport = false;
                }else if (result == TextToSpeech.LANG_MISSING_DATA){
                    Log.e(TTSUtils.TAG, "LANG_MISSING_DATA " + "缺失数据");
                    isSupport = false;
                }
                if (!isSupport){
                    AlertDialog.Builder dialog = new AlertDialog.Builder(TTSActivity.this);
                    dialog.setTitle("提示");
                    dialog.setMessage("您的手机不支持中文语音播放,请下载科大讯飞tts引擎后重试");
                    dialog.setNegativeButton("取消", null);
                    dialog.setPositiveButton("前往下载", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(Intent.ACTION_VIEW);
                            intent.setData(Uri.parse("http://www.coolapk.com/apk/com.iflytek.tts"));
                            startActivity(intent);
                        }
                    });
                    dialog.show();
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (ttsUtils != null){
            ttsUtils.release();
        }
    }
}
