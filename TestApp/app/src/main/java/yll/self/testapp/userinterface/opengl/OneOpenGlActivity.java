package yll.self.testapp.userinterface.opengl;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;



public class OneOpenGlActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        OneGlSurfaceView glSurfaceView = new OneGlSurfaceView(this);
        setContentView(glSurfaceView);
    }
}
