package yll.self.testapp.mvvm.lifecycle

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.activity_view_model.*
import yll.self.testapp.R

class ViewModelActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_model)
        val userViewModel = ViewModelProviders.of(this).get(UserViewModel::class.java)

        //构造带参数的viewModel
        val factory = MsgViewModelFactory("我是参数")
        val msgViewModel = ViewModelProviders.of(this, factory).get(MsgViewModel::class.java)

        //监听ViewModel中的user的变化，当他变化时，会将textView重新设置问题
        userViewModel.user.observe(this, Observer {
            tv_name.text = it?.name
        })

        btn_name.setOnClickListener{
            val user = User("Rose", 18, 0)
            userViewModel.user.value = user
            //setValue只能在主线程，postValue只能在后头线程
        }
    }

}