package yll.self.testapp.mvvm.databinding

import android.annotation.SuppressLint
import androidx.databinding.BindingConversion
import androidx.databinding.InverseMethod
import com.bumptech.glide.Glide
import java.text.SimpleDateFormat


// https://www.jianshu.com/p/41218ec1d383


//转换属性类型 这个注解声明一个，所有控件都会自动转换，用起来不太灵活
//@SuppressLint("SimpleDateFormat")
//@BindingConversion
//fun convertLongToString(value: Long) : String {
//    val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
//    return formatter.format(value)
//}

//这个注解的作用和上面那个注解@BindingConversion有点类似，但是不同的是，这个可以具体作用到某个控件实例上，还有双向绑定的作用

// 这个注解参数为反转的方法名，意味着一个这个注解需要两个方法才能完成
@InverseMethod("sexToNum")
fun numToSex(num: Int): String {
    return when (num) {
        0 -> "女"
        1 -> "男"
        else -> "未知性别"
    }
}

fun sexToNum(sex: String): Int {
    return when(sex) {
        "女" -> 0
        "男" -> 1
        else -> 2
    }
}