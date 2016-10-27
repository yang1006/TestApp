package yll.self.testapp.other.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by yll on 16/10/27.
 * 自定义注解1
 * 注解和类一样，也可以定义属性
 *
 * 如果某个注解属性使用value作为名称如ClassFunction中的value，
 * 那么赋值的时候可以直接@TestAnnotation("yll")，
 * 但是如果你使用的是其他名称，那么必须@TestAnnotation(name="jj")这样调用
 */

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface TestAnnotation {
    /**value，也是注解的方法，value() 也是value*/
    String value() default "";
}
