package yll.self.testapp.opengl.newstudy.triangle;

import android.os.Bundle;

import androidx.annotation.Nullable;

import yll.self.testapp.BaseActivity;
import yll.self.testapp.R;

public class MyTriangleActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new TriangleView(this));
    }
}
