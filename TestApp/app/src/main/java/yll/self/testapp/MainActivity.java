package yll.self.testapp;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.FragmentActivity;
import androidx.core.content.PermissionChecker;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import yll.self.testapp.compont.CompontActivity;
import yll.self.testapp.datasave.ParcelableTestClass;
import yll.self.testapp.datasave.SaveDataActivity;
import yll.self.testapp.datasave.SerializableTestClass;
import yll.self.testapp.design.DesignActivity;
import yll.self.testapp.mvvm.view.MVVMMainActivity;
import yll.self.testapp.normal.NormalActivity;
import yll.self.testapp.opengl.OpenGLMainActivity;
import yll.self.testapp.thirdlib.ThirdLibActivity;
import yll.self.testapp.userinterface.UIAndAniActivity;
import yll.self.testapp.other.OtherActivity;

public class MainActivity extends FragmentActivity implements View.OnClickListener {

    private TextView tv_normal, tv_data_save;
    private Context ctx;

    private final int CODE_REQUEST_CAMERA = 100;
    private final int CODE_REQUEST_STORAGE = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("yll", "MainActivity onCreate");
        setContentView(R.layout.activity_main);
        getWindow().addFlags(Window.FEATURE_NO_TITLE);
        ctx = MainActivity.this;
        init();
        tv_normal = (TextView) findViewById(R.id.tv_normal);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestStoragePermission();
        }
    }

    private void init(){
        tv_normal = (TextView) findViewById(R.id.tv_normal);
        tv_normal.setOnClickListener(this);
        tv_data_save = (TextView) findViewById(R.id.tv_data_save);
        tv_data_save.setOnClickListener(this);
        findViewById(R.id.tv_compont).setOnClickListener(this);
        findViewById(R.id.tv_ui).setOnClickListener(this);
        findViewById(R.id.tv_design).setOnClickListener(this);
        findViewById(R.id.tv_other).setOnClickListener(this);
        findViewById(R.id.tv_third_lib).setOnClickListener(this);
        findViewById(R.id.tv_open_gl).setOnClickListener(this);
        findViewById(R.id.tv_mvvm).setOnClickListener(this);
        SerializableTestClass.serialize();
        SerializableTestClass.deSerialize();

        ParcelableTestClass.testParcelable();
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()){
            case R.id.tv_normal:
                intent = new Intent(ctx, NormalActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_data_save:
                intent = new Intent(ctx, SaveDataActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_compont:
                intent = new Intent(ctx, CompontActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_ui:
                intent = new Intent(ctx, UIAndAniActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_design:
                intent = new Intent(ctx, DesignActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_other:
                intent = new Intent(ctx, OtherActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_third_lib:
                intent = new Intent(ctx, ThirdLibActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_open_gl:
                intent = new Intent(ctx, OpenGLMainActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_mvvm:
                intent = new Intent(ctx, MVVMMainActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void requestStoragePermission(){
        if (PermissionChecker.checkSelfPermission(this, Manifest.permission_group.STORAGE) != PackageManager.PERMISSION_GRANTED){
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, CODE_REQUEST_STORAGE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CODE_REQUEST_STORAGE){

        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e("yll", "MainActivity onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("yll", "MainActivity onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e("yll", "MainActivity onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e("yll", "MainActivity onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("yll", "MainActivity onDestroy");
    }
}
