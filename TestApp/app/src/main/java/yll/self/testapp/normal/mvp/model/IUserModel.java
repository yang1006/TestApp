package yll.self.testapp.normal.mvp.model;

import yll.self.testapp.normal.mvp.bean.UserBean;

/**
 * Created by yll on 2015/12/22.
 * 建立model（处理业务逻辑，这里指数据读写），先写接口，后写实现
 */
public interface IUserModel {

    void setID(int id);

    void setFirstName(String firstName);

    void setLastName(String lastName);

    int getID();

    /**通过id读取user信息,返回一个UserBean*/
    UserBean load(int id);
}
