package yll.self.testapp.normal.mvp.view;

/**
 * Created by yll on 2015/12/22.
 * 建立view（更新ui中的view状态），这里列出需要操作当前view的方法，也是接口
 */
public interface IUserView {

    int getID();

    String getFirstName();

    String getLastName();

    void setFirstName(String firstName);

    void setLastName(String lastName);

    void cleanAll();
}
