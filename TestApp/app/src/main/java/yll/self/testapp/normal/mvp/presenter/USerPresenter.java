package yll.self.testapp.normal.mvp.presenter;

import yll.self.testapp.normal.mvp.bean.UserBean;
import yll.self.testapp.normal.mvp.model.IUserModel;
import yll.self.testapp.normal.mvp.model.UserModel;
import yll.self.testapp.normal.mvp.view.IUserView;

/**
 * Created by yll on 2015/12/22.
 * 建立presenter（主导器，通过iView和iModel接口操作model和view）
 * activity可以把所有逻辑给presenter处理，这样java逻辑就从手机的activity中分离出来。
 */
public class USerPresenter {

    private IUserView mUserView;

    private IUserModel mUserModel;

    public USerPresenter(IUserView userView){
        mUserView = userView;
        mUserModel = new UserModel();
    }

    /**操作model处理数据*/
    public void saveData(int id, String firstName, String lastName){
        mUserModel.setID(id);
        mUserModel.setFirstName(firstName);
        mUserModel.setLastName(lastName);
        mUserView.cleanAll();
    }

    /**操作view更新UI*/
    public void loadUser(int id){
        UserBean user = mUserModel.load(id);
        // 通过调用IUserView的方法来更新显示
        mUserView.setFirstName(user.getmFirstName());
        mUserView.setLastName(user.getmLastName());
    }

}
