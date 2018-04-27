package yll.self.testapp.userinterface.camera;

import android.content.Context;
import android.view.View;

public abstract class CameraPreviewFragment {

    protected View rootView;
    protected Context context;

    public CameraPreviewFragment(Context context){
        this.context = context;
        init();
    }

    public  View getRootView(){
        return rootView;
    }

    public abstract void init();

    public void onDestroy(){}

}
