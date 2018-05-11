package yll.self.testapp.compont.wallpaper;

import android.Manifest;
import android.app.Activity;
import android.app.WallpaperInfo;
import android.app.WallpaperManager;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.content.PermissionChecker;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;

import java.io.IOException;

import yll.self.testapp.R;

/**
 * Created by yll on 17/8/2.
 */

public class WallPaperActivity extends AppCompatActivity implements View.OnClickListener {

    private CheckBox mCbVoice;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallpaper);

        mCbVoice = findViewById(R.id.id_cb_voice);
        mCbVoice.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // 静音
                    VideoLiveWallpaper.voiceSilence(getApplicationContext());
                } else {
                    VideoLiveWallpaper.voiceNormal(getApplicationContext());
                }
            }
        });

        findViewById(R.id.tv_camera).setOnClickListener(this);
        findViewById(R.id.tv_clear).setOnClickListener(this);
        findViewById(R.id.tv_gif).setOnClickListener(this);
        findViewById(R.id.tv_mv).setOnClickListener(this);
        WallpaperInfo info =  WallpaperManager.getInstance(this).getWallpaperInfo();
        Log.e("yll", "wallpaper info " + info);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_camera:
                startCameraWallpaper();
                break;
            case R.id.tv_gif:
                startGifWallpaper();
                break;
            case R.id.tv_clear:
                clearWallPaper();
                break;
            case R.id.tv_mv:
                VideoLiveWallpaper.setToWallPaper(this);
                break;
        }
    }

    private void clearWallPaper(){
        try {
            WallpaperManager.getInstance(this).clear();
            Toast.makeText(this, "清除壁纸成功", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "清除壁纸失败", Toast.LENGTH_SHORT).show();
        }
    }

    private void startCameraWallpaper(){
        Intent pickWallpaper = new Intent();
        //api 16可以直接跳到壁纸预览
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1){
            pickWallpaper.setAction(WallpaperManager.ACTION_CHANGE_LIVE_WALLPAPER);
            pickWallpaper.putExtra(WallpaperManager.EXTRA_LIVE_WALLPAPER_COMPONENT,
                    new ComponentName(getPackageName(), CameraLiveWallpaper.class.getCanonicalName()));
        }else {
            pickWallpaper.setAction(WallpaperManager.ACTION_LIVE_WALLPAPER_CHOOSER);
        }
//        Intent chooser = Intent.createChooser(pickWallpaper, "选择壁纸");
        startActivityForResult(pickWallpaper, REQUEST_CODE_SET_LIVE_WALLPAPER);
    }

    private void startGifWallpaper(){
        GifLiveWallpaper.GIF_RES_ID = R.drawable.gittest;
        Intent pickWallpaper = new Intent();
        //api 16可以直接跳到壁纸预览
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1){
            pickWallpaper.setAction(WallpaperManager.ACTION_CHANGE_LIVE_WALLPAPER);
            pickWallpaper.putExtra(WallpaperManager.EXTRA_LIVE_WALLPAPER_COMPONENT,
                    new ComponentName(getPackageName(), GifLiveWallpaper.class.getCanonicalName()));
        }else {
            pickWallpaper.setAction(WallpaperManager.ACTION_LIVE_WALLPAPER_CHOOSER);
        }
//        Intent chooser = Intent.createChooser(pickWallpaper, "选择壁纸");
        startActivityForResult(pickWallpaper, REQUEST_CODE_SET_LIVE_WALLPAPER);
    }

    private final int REQUEST_CODE_SET_LIVE_WALLPAPER = 100;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_SET_LIVE_WALLPAPER){
            if (resultCode == Activity.RESULT_OK){
                Toast.makeText(this, "设置壁纸成功", Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(this, "设置壁纸失败", Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * 不需要手动启动服务
     */
    void setTransparentWallpaper() {
        startService(new Intent(this, CameraLiveWallpaper.class));
    }

}
