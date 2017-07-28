package yll.self.testapp.compont.service;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;

import yll.self.testapp.R;
import yll.self.testapp.utils.UtilsManager;

/**
 * Created by yll on 2016/1/19.
 */
public class TestServiceActivity extends Activity implements View.OnClickListener {

    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_service);
        context = TestServiceActivity.this;
        init();
    }

    private void init(){

        findViewById(R.id.btn_start_service).setOnClickListener(this);
        findViewById(R.id.btn_stop_service).setOnClickListener(this);
        findViewById(R.id.btn_bind_service).setOnClickListener(this);
        findViewById(R.id.btn_unbind_service).setOnClickListener(this);
        findViewById(R.id.tv_observer).setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(context, TestService.class);;
        switch (view.getId()){
            case R.id.btn_start_service:
                context.startService(intent);
                break;
            case R.id.btn_stop_service:
                context.stopService(intent);
                break;
            case R.id.btn_bind_service:
                context.bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
                break;
            case R.id.btn_unbind_service:
                context.unbindService(serviceConnection);
                break;
            case R.id.tv_observer:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                    if (PermissionChecker.checkSelfPermission(context, Manifest.permission.PACKAGE_USAGE_STATS) == PackageManager.PERMISSION_DENIED) {
//                        startActivity(new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS));
//                    } else {
//                        startService(new Intent(context, CheckActivityService.class));
                    }
//                }else {
                    startService(new Intent(context, CheckActivityService.class));
//                }
                break;

        }
    }
    ServiceConnection serviceConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            UtilsManager.log("onServiceConnected");
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            UtilsManager.log("onServiceDisconnected");
        }
    };
}
