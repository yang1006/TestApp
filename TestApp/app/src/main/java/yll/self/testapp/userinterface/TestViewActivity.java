package yll.self.testapp.userinterface;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import yll.self.testapp.R;
import yll.self.testapp.userinterface.views.RemindScrollSpinView;

/**
 * Created by yll on 17/7/28.
 */

public class TestViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_view);

        LinearLayout ll_content = (LinearLayout) findViewById(R.id.ll_content);
        RemindScrollSpinView roundSpinView = new RemindScrollSpinView(this);

        ll_content.addView(roundSpinView, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));

        roundSpinView.setOnUnlockListener(new RemindScrollSpinView.OnUnlockListener() {
            @Override
            public void onUnlock(int direction) {
                Toast.makeText(TestViewActivity.this, direction == 0 ? "上滑解锁成功" : "下滑解锁成功", Toast.LENGTH_SHORT).show();
                TestViewActivity.this.finish();
            }
        });
    }
}
