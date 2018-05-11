package yll.self.testapp.compont;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.PermissionChecker;
import android.view.View;
import android.widget.Toast;

import yll.self.testapp.compont.broadCast.SendBroadCastActivity;
import yll.self.testapp.R;
import yll.self.testapp.compont.contentprovider.ContentProviderActivity;
import yll.self.testapp.compont.service.TestServiceActivity;
import yll.self.testapp.compont.wallpaper.WallPaperActivity;

/**
 * Created by yll on 2016/1/19.
 *
 */
public class CompontActivity extends Activity implements View.OnClickListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compont);
        init();
    }

    private void init(){
        findViewById(R.id.tv_service).setOnClickListener(this);
        findViewById(R.id.tv_broadcast).setOnClickListener(this);
        findViewById(R.id.tv_provider).setOnClickListener(this);
        findViewById(R.id.tv_wallpaper).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_service:
                startActivity(new Intent(CompontActivity.this, TestServiceActivity.class));
                break;
            case R.id.tv_broadcast:
                startActivity(new Intent(CompontActivity.this, SendBroadCastActivity.class));
                break;
            case R.id.tv_provider:
                startActivity(new Intent(CompontActivity.this, ContentProviderActivity.class));
                break;
            case R.id.tv_wallpaper:
                if (PermissionChecker.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
                    requestPermissions(new String[]{Manifest.permission.CAMERA}, CODE_REQUEST_CAMERA);
                }else {
                    startActivity(new Intent(CompontActivity.this, WallPaperActivity.class));
                }

                break;
        }
    }




    private final int CODE_REQUEST_CAMERA = 100;
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CODE_REQUEST_CAMERA){
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                startActivity(new Intent(CompontActivity.this, WallPaperActivity.class));
            }else {
                Toast.makeText(this, "获取相机权限失败", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
