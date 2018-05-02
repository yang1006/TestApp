package yll.self.testapp.kotlin

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import yll.self.testapp.MainActivity
import yll.self.testapp.R
import java.util.*
import kotlin.collections.ArrayList

/**
 * Created by yll on 18/3/23.
 * 学习一波koltin
 */
class TestKotlinActivity : Activity() {

    //用 var 定义变量，像 js 一样
    var name: String = "my name"

    // 用val定义常量（相当于final）
    val TAG : String = "TestKotlinActivity"

    //支持类型判断，可以省略 : String
    var name1 = "my name1"

    //定义一个变量必须赋值 只写var name 编译器会报错

    //懒加载 变量在第一次被调用时才会初始化 针对 val
    private val tv_1: TextView by lazy {
        findViewById<TextView>(R.id.tv_1)
    }

    //懒加载 针对 var
    lateinit var name2: String

    //空指针安全
    var b: String? = "can be null"
    fun nullSafe(){
        //非空型
        var noNull : String = "noNull"
//        noNull = null    //不能赋值成null

        //可选型 (可空)
        var canNull : String? = "canNull"
        canNull = null     //可以赋值成null

        //不允许直接调用一个 可选型的方法和属性
//        val l = canNull.length
        // 1、可以像java一样先进行非空判断, 但全局变量做了空判断也不能调用
        val l1 = if (canNull != null) canNull.length else -1
//        val l2 = if (b != null) b.length else -1

        //安全调用操作符 ?. 使用该操作符可以方便调用可选型的方法或者属性。
        //返回还是一个可选项的 Int?
        val l3 = b?.length

        //Kotlin 还提供了一个强转的操作符 !!，这个操作符能够强行调用变量的方法或者属性，而不管这个变量是否为空，
        // 如果这个时候该变量为空时，那么就会发生 NPE。
        val l4 = b!!.length  //可能会有空指针异常


        /** Elvis 操作符*/
        // ?:  如果b为null 就执行 ?: 后面的代码，和  if (b != null) b.length else -1 等价
        val l5 = b?.length ?: -1
        // 其实你还可以在 ?: 后面添加任何表达式，比如你可以在后面会用 return 和 throw（在 Kotlin 中它们都是表达式）。
        var l6 = b?.length ?: return
        var l7 = b?.length ?: throw IllegalAccessException("1111")
    }

    /**定义函数*/
    fun getAddress(id : String, name: String) : String{
        return "北京"
    }

    //由于Kotlin可以对函数的返回值进行类型推断，所以经常用“=”代替返回类型和“return”关键字，上面这段代码也可以写成
    fun getAddress2(id: String, name: String) = {
        "北京"
    }

    //如果函数内代码只有一行，我们甚至可以去掉{}
    fun getAddress3(id: String, name: String) = "北京"

    // 函数也允许空指针安全
    fun getAddress4(id: String, name: String) : String? = "北京"

    //有时候，函数的返回类型是个Unit，这其实就是Java中的void, 但是一般不写Unit
    fun getAddress5(id: String, name: String) : Unit{}
    fun getAddress6(id: String, name: String){}

    //用 is 取代了 instance of
    fun fun2(id: Any){
        if (id is String){
            return
        }
    }

    /**in，区间和集合*/
    fun testIn(){
        var i = Random().nextInt(10)
        if (i in 1..5){
            System.out.println("i 在 1~5之间")
        }

        var list = ArrayList<Int>()
        for (j in 1..5){  //遍历1~5
            list.add(Random().nextInt(10))
        }
        if (3 in list){  // 3是否在list中
            System.out.println("3 在list中")
        }else{
            System.out.println("3 不在list中")
        }

        for (j in list){
            System.out.println("list的元素 ：" + j)
        }

        for (j in 2..list.size - 1){
            //相当于 for(int j = 2; j <= list.size - 1; j++)
            //还可以写成 until形式 for (j in 2 until list.size){}
        }

        //可以反转列表
        for (j in (1..5).reversed()){}

        //可以指定步长
        for (j in 1..10 step 2){

        }

        //Kotlin 的集合还自带 foreach 函数
        list.forEach {
            System.out.println("list的元素")
        }
    }

    /** 用when 取代了 switch*/
    fun doWhen(obj : Any){
        //可以处理多种类型
        when(obj){
            1-> " obj is 1"
            in 2..5 -> "obj between 2~5"
            "str" -> fun1("yang")
            is String -> "obj is type of String"
            else -> "相当于switch 中的 default"
        }
    }


    /**数据类 用data修饰class   object 在kotlin中表示单例*/
    data class User(var id:Long, var name: String, var age: Int){

        // 静态属性和方法
        companion object {
            var VERSION = "1.0"
            var VER_CODE = 1
            var user: User? = null
            //静态方法
            fun newInstance(id: Long, name: String, age: Int): User?{
                if (user == null) {
                    user = User(id, name, age)
                }
                return user
            }
        }

        fun doubleAge(){
            age *= 2
        }
    }

    /** 单例模式*/
    object TheOne {
        var NAME = "Yang"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_koltin)
        var tv_1 = findViewById<TextView>(R.id.tv_1)
        tv_1.text = "杀得死大多"
        var user = User.newInstance(1, "yang", 10)
        user?.doubleAge()
        System.out.println("now age->" + user?.age)
        var one = TheOne

    }



    fun fun1(name : String): Boolean{
        name2 = name
        return false
    }

    fun jump2Main(){

        //实例化对象不需要 new
        var list = ArrayList<String>()

        // :: 使用java类
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}