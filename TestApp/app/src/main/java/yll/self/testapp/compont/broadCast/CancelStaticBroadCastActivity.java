package yll.self.testapp.compont.broadCast;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import androidx.annotation.Nullable;
import android.view.View;

import yll.self.testapp.R;

public class CancelStaticBroadCastActivity extends Activity implements View.OnClickListener {

    public static String ACTION = "TEST_CANCEL_STATIC_BROADCAST_RECEIVER";
    PackageManager packageManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cancel_static_broadcast);
        findViewById(R.id.tv_cancel).setOnClickListener(this);
        findViewById(R.id.tv_register).setOnClickListener(this);
        findViewById(R.id.tv_send).setOnClickListener(this);
        packageManager = getPackageManager();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_cancel:
                packageManager.setComponentEnabledSetting(new ComponentName("yll.self.testapp", MyStaticBroadCastReceiver.class.getName()),
                        PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);
                break;
            case R.id.tv_register:
                packageManager.setComponentEnabledSetting(new ComponentName("yll.self.testapp", MyStaticBroadCastReceiver.class.getName()),
                        PackageManager.COMPONENT_ENABLED_STATE_DEFAULT, PackageManager.DONT_KILL_APP);
                break;
            case R.id.tv_send:
                Intent intent = new Intent(ACTION);
                sendBroadcast(intent);
                break;
        }
    }
}
