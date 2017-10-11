package yll.self.testapp.thirdlib.rxjava;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import yll.self.testapp.R;

/**
 * Created by yll on 17/9/28.
 */

public class RxJavaTextActivity extends AppCompatActivity {

    private ImageView iv_0;
    private MyRxJava myRxJava = new MyRxJava();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_java_test);
        init();
        test();
    }

    private void init(){
        iv_0 = (ImageView) findViewById(R.id.iv_0);
    }


    private void test(){
//        myRxJava.setImageView(this, R.drawable.ic_launcher, iv_0);

//        myRxJava.testScheduler1();

        myRxJava.setSchedulerImageView(this, R.drawable.ic_launcher, iv_0);

//        myRxJava.testMap(this, R.drawable.ic_launcher, iv_0);
    }


}
