package yll.self.testapp.video;

import android.os.Bundle;
import androidx.annotation.Nullable;
import android.view.View;

import yll.self.testapp.BaseActivity;
import yll.self.testapp.R;

public class VideoMainActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_main);
        findViewById(R.id.tv_player).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
