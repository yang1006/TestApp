package yll.self.testapp.mvvm.lifecycle

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.activity_view_model_test.*
import yll.self.testapp.R
import yll.self.testapp.databinding.ActivityViewModelTestBindingImpl

class ViewModelActivity : AppCompatActivity() {

    private lateinit var mDataBinding: ActivityViewModelTestBindingImpl

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_view_model)
        mDataBinding = DataBindingUtil.setContentView<ActivityViewModelTestBindingImpl>(this, R.layout.activity_view_model_test)

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
        mDataBinding.userViewModel = userViewModel

    }

    override fun onDestroy() {
        super.onDestroy()
        mDataBinding.unbind()
    }

}