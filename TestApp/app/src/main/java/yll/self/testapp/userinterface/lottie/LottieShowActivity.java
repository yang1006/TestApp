package yll.self.testapp.userinterface.lottie;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.airbnb.lottie.LottieAnimationView;

import yll.self.testapp.R;

/**
 * Created by yll on 17/6/15.
 */

public class LottieShowActivity extends Activity {

    private boolean isPause = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lottie_show);
        String fileName = getIntent().getStringExtra("fileName");
        final LottieAnimationView lottie_view = (LottieAnimationView) findViewById(R.id.lottie_view);
        lottie_view.loop(true);
        lottie_view.setAnimation(fileName);
        lottie_view.playAnimation();

        final Button btn_pause = (Button) findViewById(R.id.btn_pause);
        btn_pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isPause){
                    lottie_view.pauseAnimation();
                    btn_pause.setText("继续");
                }else {
                    lottie_view.resumeAnimation();
                    btn_pause.setText("暂停");
                }
                isPause = !isPause;
            }
        });
    }
}
