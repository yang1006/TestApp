package yll.self.testapp.datasave;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.zip.ZipFile;

import yll.self.testapp.R;

/**
 * Created by yll on 2015/12/21.
 * 保存数据相关测试
 */
public class SaveDataActivity extends Activity implements View.OnClickListener {
    private TextView tv_stream;
    private Context ctx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_data);
        ctx = SaveDataActivity.this;
        init();
    }

    private void init(){
        tv_stream = (TextView) findViewById(R.id.tv_stream);
        tv_stream.setOnClickListener(this);
        findViewById(R.id.tv_db).setOnClickListener(this);
        findViewById(R.id.tv_test).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()){
            case R.id.tv_stream:
                intent = new Intent(ctx, StreamTestActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_db:
                intent = new Intent(ctx, DbTestActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_test:
                File file = new File(Environment.getExternalStorageDirectory().getPath() + "/test.apk");
                writeApk(file, "111112312312");
                break;
        }
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
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
            Toast.makeText(this, "写入完成", Toast.LENGTH_SHORT).show();
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
     * @return
     */
    private byte[] short2Stream(short data) {
        ByteBuffer buffer = ByteBuffer.allocate(2);
        buffer.order(ByteOrder.LITTLE_ENDIAN);
        buffer.putShort(data);
        buffer.flip();
        return buffer.array();
    }


    /**从apk末尾读出 刚才写入的数据
     *   String apkPath = getPackagePath(this);
         if (!TextUtils.isEmpty(apkPath)){
           String s = readApk(new File(apkPath));
           if (!TextUtils.isEmpty(s)){
              UtilsManager.Toast(this, s);
              }
        }
     */
    public String getPackagePath(Context context) {
        if (context != null) {
            return context.getPackageCodePath();
        }
        return null;
    }

    public String readApk(File file) {
        byte[] bytes = null;
        try {
            RandomAccessFile accessFile = new RandomAccessFile(file, "r");
            long index = accessFile.length();

            bytes = new byte[2];
            index = index - bytes.length;
            accessFile.seek(index);
            accessFile.readFully(bytes);

            int contentLength = stream2Short(bytes, 0);

            bytes = new byte[contentLength];
            index = index - bytes.length;
            accessFile.seek(index);
            accessFile.readFully(bytes);

            return new String(bytes, "utf-8");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;}

    /**
     * short转换成字节数组（小端序）
     * @return
     */
    private short stream2Short(byte[] stream, int offset) {
        ByteBuffer buffer = ByteBuffer.allocate(2);
        buffer.order(ByteOrder.LITTLE_ENDIAN);
        buffer.put(stream[offset]);
        buffer.put(stream[offset + 1]);
        return buffer.getShort(0);
    }
}
