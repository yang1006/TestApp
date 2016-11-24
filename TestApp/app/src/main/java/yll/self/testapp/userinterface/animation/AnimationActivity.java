package yll.self.testapp.userinterface.animation;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.RelativeLayout;
import android.widget.TextView;

import yll.self.testapp.R;

/**
 * Created by yll on 16/11/23.
 */

public class AnimationActivity extends Activity implements View.OnClickListener {

    private RelativeLayout rl_ani;
    private TextView tv_text;
    private MyAnimationView myAnimationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation);
        findViewById(R.id.btn_start_ani).setOnClickListener(this);
        rl_ani = (RelativeLayout) findViewById(R.id.rl_ani);
        myAnimationView = (MyAnimationView) findViewById(R.id.mav);
        tv_text = (TextView) findViewById(R.id.tv_text);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_start_ani:
                startAni(tv_text);
                break;
        }
    }


    Animation animation;
    private void startAni(final View view){
        final int height = rl_ani.getHeight();

        animation = new Animation() {
            @Override
            public boolean willChangeBounds() {
                return true;
            }

            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                view.getLayoutParams().height = (int) (interpolatedTime * height);
                view.requestLayout();
                if (interpolatedTime == 0){
                    view.setVisibility(View.VISIBLE);
                }
            }
        };
        animation.setDuration(1000);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                tv_text.setText("打卡成功,获得10积分");
                myAnimationView.startAni();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        view.startAnimation(animation);
    }

}
