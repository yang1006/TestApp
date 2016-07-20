package yll.self.testapp.normal.mvp.bean;

/**
 * Created by yll on 2015/12/22.
 * 建立bean
 */
public class UserBean {

    private int id;
    private String mFirstName;
    private String mLastName;

    public UserBean(){}

    public void setId(int id){
        this.id = id;
    }

    public int getId(){
        return id;
    }

    public void setmFirstName(String firstName){
        mFirstName = firstName;
    }

    public void setmLastName(String lastName){
        mLastName = lastName;
    }

    public String getmFirstName(){
        return mFirstName;
    }

    public String getmLastName(){
        return mLastName;
    }


}
