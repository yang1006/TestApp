package yll.self.testapp.userinterface.animation;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.widget.SlidingPaneLayout;
import android.view.View;

import yll.self.testapp.R;
import yll.self.testapp.utils.UtilsManager;

/**
 * Created by yll on 16/12/23.
 */

public class SlidingPaneActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sliding_pane_layout);
        SlidingPaneLayout slidingPaneLayout = (SlidingPaneLayout) findViewById(R.id.sliding);
        slidingPaneLayout.setPanelSlideListener(new SlidingPaneLayout.PanelSlideListener() {
            @Override
            public void onPanelSlide(View panel, float slideOffset) {

            }

            @Override
            public void onPanelOpened(View panel) {
                UtilsManager.log("onPanelOpened");
            }

            @Override
            public void onPanelClosed(View panel) {
                UtilsManager.log("onPanelClosed");
            }
        });
    }
}
