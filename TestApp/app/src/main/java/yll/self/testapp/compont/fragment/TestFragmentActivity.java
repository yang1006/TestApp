package yll.self.testapp.compont.fragment;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentTransaction;

import yll.self.testapp.BaseActivity;
import yll.self.testapp.R;

public class TestFragmentActivity extends BaseActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_fragment);
    }

    @Override
    protected void onPause() {
        super.onPause();
        //测试在onPause中是否可以实例化fragment
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        TestFragment fragment = new TestFragment();
        ft.add(R.id.fl, fragment);
        ft.commit();
    }
}
