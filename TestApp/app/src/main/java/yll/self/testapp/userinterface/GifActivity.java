package yll.self.testapp.userinterface;

import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


import pl.droidsonroids.gif.GifDrawable;
import yll.self.testapp.R;

public class GifActivity extends AppCompatActivity {


    final String gifUrl = "http://attach.cgjoy.com/attachment/forum/201108/31/180954ubjbbabfb0s4ja4j.gif";

    private ImageView gifImageView, gifImageView2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gif);

        gifImageView = findViewById(R.id.gifImageView);
        gifImageView2 = findViewById(R.id.gifImageView2);

        Glide.with(this).load(gifUrl).into(gifImageView);

        new Thread(new Runnable() {
            @Override
            public void run() {
                File file = null;
                try {
                     file = Glide.with(GifActivity.this).load(gifUrl).downloadOnly(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL).get();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (file == null){
                        Log.e("yll", "failed");
                    }else {
                        Log.e("yll", "success");
                        File parent = new File(Environment.getExternalStorageDirectory() + "/testApp");
                        if (!parent.exists()){
                            parent.mkdirs();
                        }
                        final File newFile = new File(Environment.getExternalStorageDirectory() + "/testApp/111.gif");
                        copySdcardFile(file.getAbsolutePath(), newFile.getAbsolutePath());
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    GifDrawable gifDrawable = new GifDrawable(newFile);
                                    gifImageView2.setImageDrawable(gifDrawable);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        });

                    }
                }
            }
        }).start();

    }
    //文件拷贝
    //要复制的目录下的所有非子目录(文件夹)文件拷贝
    public int copySdcardFile(String fromFile, String toFile)
    {
        try
        {
            InputStream fosfrom = new FileInputStream(fromFile);
            OutputStream fosto = new FileOutputStream(toFile);
            byte bt[] = new byte[1024];
            int c;
            while ((c = fosfrom.read(bt)) > 0)
            {
                fosto.write(bt, 0, c);
            }
            fosfrom.close();
            fosto.close();
            return 0;

        } catch (Exception ex)
        {
            return -1;
        }
    }



}
