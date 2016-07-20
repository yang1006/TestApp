package yll.self.testapp.userinterface.animation;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import yll.self.testapp.R;

/**
 * Created by yll on 2016/1/20.
 */
public class ClickBgActivity extends Activity{
    private Context ctx;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ctx = ClickBgActivity.this;
        setContentView(R.layout.activity_click_bg);
    }
}

