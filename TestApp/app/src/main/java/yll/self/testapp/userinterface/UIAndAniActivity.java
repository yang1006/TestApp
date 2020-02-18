package yll.self.testapp.userinterface;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.content.PermissionChecker;
import androidx.appcompat.app.AlertDialog;
import android.view.View;
import android.widget.Toast;

import yll.self.testapp.R;
import yll.self.testapp.userinterface.animation.AnimationActivity;
import yll.self.testapp.userinterface.animation.ChangeAniActivity;
import yll.self.testapp.userinterface.animation.ClickBgActivity;
import yll.self.testapp.userinterface.camera.CameraPreviewActivity;
import yll.self.testapp.userinterface.lottie.LottieActivity;
import yll.self.testapp.userinterface.animation.SlidingPaneActivity;
import yll.self.testapp.userinterface.opengl.OneOpenGlActivity;
import yll.self.testapp.userinterface.views.TextClockTestActivity;

/**
 * Created by yll on 2016/1/20.
 */
public class UIAndAniActivity extends Activity implements View.OnClickListener {
    private Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = UIAndAniActivity.this;
        setContentView(R.layout.activity_ui_ani);
        init();
    }

    private void init(){
        findViewById(R.id.tv_click).setOnClickListener(this);
        findViewById(R.id.tv_animate1).setOnClickListener(this);
        findViewById(R.id.tv_3).setOnClickListener(this);
        findViewById(R.id.tv_4).setOnClickListener(this);
        findViewById(R.id.tv_5).setOnClickListener(this);
        findViewById(R.id.tv_show_all).setOnClickListener(this);
        findViewById(R.id.tv_unlock_view).setOnClickListener(this);
        findViewById(R.id.tv_text_clock).setOnClickListener(this);
        findViewById(R.id.tv_open_gl).setOnClickListener(this);
        findViewById(R.id.tv_camera_preview).setOnClickListener(this);
        findViewById(R.id.tv_gif).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_click:
                Intent intent = new Intent(context, ClickBgActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_animate1:
                startActivity(new Intent(context, ChangeAniActivity.class));
                break;
            case R.id.tv_3:
                startActivity(new Intent(context, AnimationActivity.class));
                break;
            case R.id.tv_4:
                startActivity(new Intent(context, SlidingPaneActivity.class));
                break;
            case R.id.tv_5:
                startActivity(new Intent(context, LottieActivity.class));
                break;
            case R.id.tv_show_all:
                startActivity(new Intent(context, ShowAllTextActivity.class));
                break;
            case R.id.tv_unlock_view:
                startActivity(new Intent(context, TestViewActivity.class));
                break;
            case R.id.tv_text_clock:
                startActivity(new Intent(context, TextClockTestActivity.class));
                break;
            case R.id.tv_open_gl:
                startActivity(new Intent(context, OneOpenGlActivity.class));
                break;
            case R.id.tv_camera_preview:
                requestCameraPermission();
                break;
            case R.id.tv_gif:
                requestStoragePermission();
                break;
            default:
                break;
        }
    }

    private String[] items = new String[]{
            CameraPreviewActivity.SurfaceView,
            CameraPreviewActivity.GlSurfaceView,
            CameraPreviewActivity.TextureView
    };


    private void showCameraPreview(){
        AlertDialog.Builder builder  = new AlertDialog.Builder(context);
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                CameraPreviewActivity.open(context, items[which]);
            }
        });
        builder.show();
    }

    private void jump2GifActivity(){
        startActivity(new Intent(context, GifActivity.class));
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void requestCameraPermission(){
        if (PermissionChecker.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
            requestPermissions(new String[]{Manifest.permission.CAMERA}, CODE_REQUEST_CAMERA);
        }else {
            showCameraPreview();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void requestStoragePermission(){
        if (PermissionChecker.checkSelfPermission(this, Manifest.permission_group.STORAGE) != PackageManager.PERMISSION_GRANTED){
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, CODE_REQUEST_STORAGE);
        }else {
            jump2GifActivity();
        }
    }


    private final int CODE_REQUEST_CAMERA = 100;
    private final int CODE_REQUEST_STORAGE = 101;
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CODE_REQUEST_CAMERA){
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                showCameraPreview();
            }else {
                Toast.makeText(this, "获取相机权限失败", Toast.LENGTH_SHORT).show();
            }
        }else if (requestCode == CODE_REQUEST_STORAGE){
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED
                    && grantResults[1] == PackageManager.PERMISSION_GRANTED){
                jump2GifActivity();
            }else {
                Toast.makeText(this, "获取存储权限失败", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
