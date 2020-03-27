package yll.self.testapp.mvvm.lifecycle

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

//构造带参数的ViewModel实例

data class TipMsg(val tip: String)

class MsgViewModelFactory(private val msg: String) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MsgViewModel(msg) as T
    }
}

class MsgViewModel(tip: String) : ViewModel() {
    val tipMsg = TipMsg(tip)
}

