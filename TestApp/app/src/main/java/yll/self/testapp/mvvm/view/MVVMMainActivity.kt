package yll.self.testapp.mvvm.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import yll.self.testapp.R
import yll.self.testapp.databinding.ActivityMvvmTestBindingImpl
import yll.self.testapp.mvvm.viewmodel.MainViewModel
import yll.self.testapp.mvvm.viewmodel.ObserveViewModel

class MVVMMainActivity : AppCompatActivity() {

    private lateinit var mDataBinding: ActivityMvvmTestBindingImpl


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mDataBinding = DataBindingUtil.setContentView<ActivityMvvmTestBindingImpl>(this, R.layout.activity_mvvm_test)
        mDataBinding.viewModel = MainViewModel()
        mDataBinding.text = "点我不能也修改名字"
        mDataBinding.observeViewModel = ObserveViewModel()
    }

    override fun onDestroy() {
        super.onDestroy()
        mDataBinding.unbind()
    }
}