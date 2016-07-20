package yll.self.testapp.normal.mvp.model;

import yll.self.testapp.normal.mvp.bean.UserBean;

/**
 * Created by yll on 2015/12/22.
 * IUserModel接口的实现类，封装具体的数据操作
 */
public class UserModel implements IUserModel {

    private UserBean bean;

    public UserModel(){
        bean = new UserBean();
    }

    @Override
    public void setID(int id) {
        bean.setId(id);
    }

    @Override
    public void setFirstName(String firstName) {
        bean.setmFirstName(firstName);
    }

    @Override
    public void setLastName(String lastName) {
        bean.setmLastName(lastName);
    }

    @Override
    public int getID() {
        return bean.getId();
    }

    @Override
    public UserBean load(int id) {
        return bean;
    }
}
