package yll.self.testapp.datasave;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import yll.self.testapp.R;

/**
 *  Created by yll on 2015/12/21.
 * 创建文件夹和文件，进行输入输出流测试
 * 问题：openFileInput()和openFileOutput两个函数都没卵用
 * 应写成  File file = new File(FILE_PATH + FILE_NAME);
 *        FileInputStream fis = new FileInputStream(file);
 *   个人文件默认路径：data/data/包名/files
 *   Environment.getExternalStorageDirectory().getPath()获取sd卡路径
 *   也可以使用/mnt/sdcard/路线代表sd卡的路径
 */
public class StreamTestActivity extends Activity implements View.OnClickListener {
    private EditText et_read, et_write;
    private Button btn_read, btn_write;
    private TextView tv_log;
    /**文件路径和文件名*/
    String FILE_PATH = "/sdcard/Test";  // 就等于(最好使用)Environment.getExternalStorageDirectory().getPath()+"/Test"
    final String FILE_NAME = "/yll.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stream_test);
        init();
    }

    private void init(){
        et_read = (EditText) findViewById(R.id.et_read);
        et_write = (EditText) findViewById(R.id.et_write);
        btn_read = (Button) findViewById(R.id.btn_read);
        btn_read.setOnClickListener(this);
        btn_write = (Button) findViewById(R.id.btn_write);
        btn_write.setOnClickListener(this);
        tv_log = (TextView) findViewById(R.id.tv_log);
        initLog();
    }

    /**测试各种路径的获取方法*/
    private void initLog(){
        StringBuilder sbBuilder = new StringBuilder();
        File dataDir = Environment.getDataDirectory();
        String dirPath = dataDir.getAbsolutePath(); //result is: /data

        dataDir = Environment.getExternalStorageDirectory();

        /**判断sd卡是否存在*/
        sbBuilder.append("The sdcard mounted?" + Environment.getExternalStorageState() +"\n\n");// mounted
        /**获取各种存储路径*/
        sbBuilder.append("Environment.getDataDirectory:"+ dirPath + "\n\n");  //  /data
        sbBuilder.append("Environment.getExternalStorageDirectory:"+ dataDir.getAbsolutePath()+"\n\n");// /storage/emulated/0
        sbBuilder.append("context.getExternalFilesDir:"+ getExternalFilesDir(null)+"\n\n");// /storage/emulated/0/Android/data/yll.self.testapp/files
        sbBuilder.append("context.getFileDir:" + getFilesDir().getAbsolutePath() + "\n\n");//    /data/data/yll.self.testapp/files
        sbBuilder.append("context.getCacheDir:"+ getCacheDir().getAbsolutePath()+"\n\n");//   /data/data/yll.self.testapp/cache
        tv_log.setText(sbBuilder.toString());

        FILE_PATH = "/mnt/sdcard/Test";  //  路径/sdcard/Test也可以，等于Environment.getExternalStorageDirectory().getPath()+"/Test"
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_read:
                et_read.setText(read());
                break;
            case R.id.btn_write:
                write(et_write.getText().toString().trim());
                break;
        }
    }

    /**读取文件的数据*/
    private String read(){
        try{
            File file = new File(FILE_PATH + FILE_NAME);

            //FileInputStream extends InputStream
            FileInputStream fis = new FileInputStream(file);
            byte[] buff = new byte[1024];
            int hasRead = 0;
            StringBuilder sb = new StringBuilder();
            //fis.read(buff) Returns the number of bytes actually read or -1 if the end of the stream
            // has been reached.
            while ((hasRead = fis.read(buff)) > 0){
                sb.append(new String(buff, 0 , hasRead));
            }
            fis.close();
            return sb.toString();

        }catch (Exception e){e.printStackTrace();}
        return null;
    }

    /**向文件中追加数据*/
    private void write(String data){
        try{
//            File file = new File(FILE_PATH);
//            if (!file.exists()){    //创建文件夹（路径）
//                file.mkdir();
//            }
            File file1 = new File(FILE_PATH + FILE_NAME);
            if (!file1.exists()){   // 创建文件
                file1.createNewFile();
            }
            data = read() + data;
            byte[] buffer = data.getBytes();
            //PrintStream extends FileOutputStream extends OutputStream
            FileOutputStream fos = new FileOutputStream(file1);
            fos.write(buffer);
//            PrintStream ps = new PrintStream(fos);
//            ps.append(data);
            fos.flush();
            fos.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
