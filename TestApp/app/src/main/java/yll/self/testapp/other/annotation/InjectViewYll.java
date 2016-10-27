package yll.self.testapp.other.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by yll on 16/10/27.
 * 自定义annotation 来代替findViewById
 */

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface InjectViewYll {
    String value() default "";
    /**控件的id*/
    int id() default -1;
}
