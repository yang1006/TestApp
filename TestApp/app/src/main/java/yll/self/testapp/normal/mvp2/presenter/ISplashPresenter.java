package yll.self.testapp.normal.mvp2.presenter;

import android.content.Context;

import yll.self.testapp.normal.mvp2.model.INetConnect;
import yll.self.testapp.normal.mvp2.model.NetConnect;
import yll.self.testapp.normal.mvp2.view.ISplashView;

/**
 * Created by lip on 2015/12/22.
 * presenter实现
 */
public class ISplashPresenter {

    private ISplashView splashView;

    private INetConnect netConnect;

    public ISplashPresenter(ISplashView splashView){
        this.splashView = splashView;
        netConnect = new NetConnect();
    }

    public void startLoad(Context context){
        splashView.showProgressDialog();
        if (netConnect.isNetConnect(context)){
            splashView.jump2NextActivity();
        }else {
            splashView.showErrorInfo();
        }
        splashView.hideProgressDialog();
    }


}
