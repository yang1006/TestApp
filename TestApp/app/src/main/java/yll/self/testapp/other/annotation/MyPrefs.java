package yll.self.testapp.other.annotation;

import org.androidannotations.annotations.sharedpreferences.DefaultBoolean;
import org.androidannotations.annotations.sharedpreferences.DefaultInt;
import org.androidannotations.annotations.sharedpreferences.DefaultString;
import org.androidannotations.annotations.sharedpreferences.SharedPref;

/**直接使用@SharedPref可以简单地使用SharedPreferences的功能。
 首先，建一个类存放需要存取的数据：

 如果这些数据要在一些不同的组件中同步共享，需在@SharedPref加上(value=SharedPref.Scope.UNIQUE)*/

@SharedPref
public interface MyPrefs {
    @DefaultBoolean(true)
    boolean isFirstIn();

    @DefaultString("")
    String ignoreVersion();

    @DefaultInt(0)
    int shockLevel();

}
