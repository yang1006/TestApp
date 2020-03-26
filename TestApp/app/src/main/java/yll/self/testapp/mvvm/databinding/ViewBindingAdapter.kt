package yll.self.testapp.mvvm.databinding

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

// http://m.imeitou.com/uploads/allimg/2019102119/xybbwq2gytt.jpg
//拓展属性，直接在layout直接给imageView 的imgUrl属性设置url就可以加载网络图
@BindingAdapter("imgUrl")
fun setImgUrl(view: ImageView, url: String) {
    Glide.with(view.context).load(url).into(view)
}

//可以一次性给ImageView 扩展2个属性，requireAll表示是否必须要在layout中同时设置这2个属性
//如果第二个参数设置为true但是在xml中有没有将全部的拓展属性设置好的话在编译的时候就会报错：Found data binding errors.
//属性名前可以加命名空间  @BindingAdapter(value = ["app:imgUrl, app:imgBgRes"], requireAll = false)
//@BindingAdapter(value = ["imgUrl, imgBgRes"], requireAll = false)
//fun setImageUrl(view: ImageView, url: String, resId: Int) {
//    Glide.with(view.context).load(url).into(view)
//    view.setBackgroundResource(resId)
//}
