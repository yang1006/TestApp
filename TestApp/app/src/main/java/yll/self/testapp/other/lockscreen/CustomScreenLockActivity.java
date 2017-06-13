package yll.self.testapp.other.lockscreen;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import yll.self.testapp.R;

/**
 * Created by yll on 17/6/12.
 * 自定义锁屏
 * 流程:启动一个service,接收 ACTION_SCREEN_OFF 广播时,启动一个activity替换系统自己的锁屏界面
 * 问题: 带密码的锁屏界面无法替换, 有多个锁屏软件时会有多个锁屏界面, home按钮屏蔽等
 */

public class CustomScreenLockActivity extends Activity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_screen_lock);
        getWindow().addFlags(Window.FEATURE_NO_TITLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
        init();
    }

    private void init(){
        findViewById(R.id.tv_unlock).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_unlock:
                finish();
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        /**屏蔽 back和menu事件 但是无法屏蔽 home键*/
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK || event.getKeyCode() == KeyEvent.KEYCODE_MENU){
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
