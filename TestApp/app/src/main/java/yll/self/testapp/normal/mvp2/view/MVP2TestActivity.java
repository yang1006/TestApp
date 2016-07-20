package yll.self.testapp.normal.mvp2.view;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.Toast;

import yll.self.testapp.R;
import yll.self.testapp.normal.mvp2.presenter.ISplashPresenter;

/**
 * Created by yll on 2015/12/22.
 * mvp模式测试2
 */
public class MVP2TestActivity extends Activity implements ISplashView{

    private ISplashPresenter iSplashPresenter;
    private ProgressDialog progress;

    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mvp2_test);
        context = MVP2TestActivity.this;
        progress = new ProgressDialog(this);
        progress.setCancelable(true);
        progress.setCanceledOnTouchOutside(true);
        progress.setTitle("加载数据中，请稍后...");

        iSplashPresenter = new ISplashPresenter(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        iSplashPresenter.startLoad(context);
    }

    @Override
    public void showProgressDialog() {
        progress.show();
    }

    @Override
    public void hideProgressDialog() {
        progress.hide();
    }

    @Override
    public void showErrorInfo() {
        Toast.makeText(context, "没有网络" , Toast.LENGTH_LONG).show();
    }

    @Override
    public void jump2NextActivity() {
        Toast.makeText(context, "加载成功，跳转到下一个activity" , Toast.LENGTH_LONG).show();
    }
}
