package yll.self.testapp.other;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.Window;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.zip.ZipFile;

import yll.self.testapp.R;
import yll.self.testapp.WebviewTestActivity;
import yll.self.testapp.hook.HookTestActivity;
import yll.self.testapp.other.annotation.AnnotationActivity_;
import yll.self.testapp.other.lockscreen.LockScreenService;
import yll.self.testapp.other.tts.TTSActivity;

/**
 * Created by yll on 2016/7/18.
 * 其他
 */
public class OtherActivity extends Activity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other);
        getWindow().addFlags(Window.FEATURE_NO_TITLE);
        init();
    }

    private void init(){
        findViewById(R.id.tv_test).setOnClickListener(this);
        findViewById(R.id.tv_js_test).setOnClickListener(this);
        findViewById(R.id.tv_hook_test).setOnClickListener(this);
        findViewById(R.id.tv_annotation).setOnClickListener(this);
        findViewById(R.id.tv_lock_screen).setOnClickListener(this);
        findViewById(R.id.tv_tts).setOnClickListener(this);
        findViewById(R.id.tv_surfaceView).setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_test:
                File file = new File(Environment.getExternalStorageDirectory().getPath() + "/test.apk");
                writeApk(file, "111112312312");
                break;
            case R.id.tv_js_test:
                startActivity(new Intent(this, WebviewTestActivity.class));
                break;
            case R.id.tv_hook_test:
                startActivity(new Intent(this, HookTestActivity.class));
                break;
            case R.id.tv_annotation:
//                startActivity(new Intent(this, AnnotationActivity_.class));
                AnnotationActivity_.intent(this).start();
                break;
            case R.id.tv_lock_screen:
                startService(new Intent(this, LockScreenService.class));
                break;
            case R.id.tv_tts:
                startActivity(new Intent(this, TTSActivity.class));
                break;
            case R.id.tv_surfaceView:
                startActivity(new Intent(this, SurfaceViewTestActivity.class));
                break;
        }
    }


    @TargetApi(Build.VERSION_CODES.KITKAT)
    /**向apk包末尾写入数据*/
    public void writeApk(File file, String comment) {
        ZipFile zipFile = null;
        ByteArrayOutputStream outputStream = null;
        RandomAccessFile accessFile = null;
        try {
            zipFile = new ZipFile(file);
            String zipComment = zipFile.getComment();
            if (zipComment != null) {
                return;
            }

            byte[] byteComment = comment.getBytes();
            outputStream = new ByteArrayOutputStream();

            outputStream.write(byteComment);
            outputStream.write(short2Stream((short) byteComment.length));

            byte[] data = outputStream.toByteArray();

            accessFile = new RandomAccessFile(file, "rw");
            accessFile.seek(file.length() - 2);
            accessFile.write(short2Stream((short) data.length));
            accessFile.write(data);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (zipFile != null) {
                    zipFile.close();
                }
                if (outputStream != null) {
                    outputStream.close();
                }
                if (accessFile != null) {
                    accessFile.close();
                }
            } catch (Exception e) {

            }

        }}

    /**
     * 字节数组转换成short（小端序）
     */
    private byte[] short2Stream(short data) {
        ByteBuffer buffer = ByteBuffer.allocate(2);
        buffer.order(ByteOrder.LITTLE_ENDIAN);
        buffer.putShort(data);
        buffer.flip();
        return buffer.array();
    }
}
