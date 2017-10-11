package yll.self.testapp.thirdlib;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import yll.self.testapp.R;
import yll.self.testapp.thirdlib.retrofit.RetrofitActivity;
import yll.self.testapp.thirdlib.rxjava.RxJavaTextActivity;

/**
 * Created by yll on 17/7/18.
 * 第三方库
 */

public class ThirdLibActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third_lib);

        findViewById(R.id.tv_retrofit).setOnClickListener(this);
        findViewById(R.id.tv_rxjava).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_retrofit:
                startActivity(new Intent(this, RetrofitActivity.class));
                break;
            case R.id.tv_rxjava:
                startActivity(new Intent(this, RxJavaTextActivity.class));
                break;
        }
    }
}
