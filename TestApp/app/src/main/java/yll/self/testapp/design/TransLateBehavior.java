package yll.self.testapp.design;

import android.content.Context;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import androidx.core.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;

import yll.self.testapp.utils.UtilsManager;

/**
 * Created by yll on 2016/3/24.
 * 自定义behavior实现FloatingActionButton随SnackBar移动
 */
public class TransLateBehavior extends CoordinatorLayout.Behavior<FloatingActionButton> {

    public TransLateBehavior(){}

    public TransLateBehavior(Context context, AttributeSet attrs){
        super(context, attrs);
    }

    /**设置关心哪个view*/
    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, FloatingActionButton child, View dependency) {
        return dependency instanceof Snackbar.SnackbarLayout;
    }

    /**关心的view状态改变时*/
    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, FloatingActionButton child, View dependency) {
        /**ViewCompat.getTranslationY-->相对于view.getTop的偏移*/
        float offset = ViewCompat.getTranslationY(dependency) - dependency.getHeight();
        UtilsManager.log("offset-->"+offset);
        UtilsManager.log("dependency.getTop-->"+dependency.getTop());
        child.setTranslationY(offset);
        return false;
    }

}
