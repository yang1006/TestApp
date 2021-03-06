package yll.self.testapp.normal;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import yll.self.testapp.R;
import yll.self.testapp.normal.mvp.view.MVPTestActivity;
import yll.self.testapp.normal.mvp2.view.MVP2TestActivity;

/**
 * Created by yll on 2015/11/26.
 */
public class NormalActivity extends Activity implements View.OnClickListener {

    private Context ctx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_normal);
        ctx = NormalActivity.this;
        init();
    }

    private void init() {
        findViewById(R.id.tv_hash).setOnClickListener(this);
        findViewById(R.id.tv_mvp_test).setOnClickListener(this);
        findViewById(R.id.tv_mvp2).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.tv_hash:
                intent = new Intent(ctx, HashTestActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_mvp_test:
                intent = new Intent(ctx, MVPTestActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_mvp2:
                intent = new Intent(ctx, MVP2TestActivity.class);
                startActivity(intent);
                break;
        }
    }
}
