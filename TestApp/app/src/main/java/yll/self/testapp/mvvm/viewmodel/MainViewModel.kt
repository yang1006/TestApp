package yll.self.testapp.mvvm.viewmodel

import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt
import yll.self.testapp.utils.LogUtil
import kotlin.random.Random

class MainViewModel {
    var name = ObservableField<String>("张三")
    var age = ObservableInt(10)
    var isMan = true
    var includeText = "嵌套的字符串"

    fun ageGrow() {
        var lastAge = age.get()
        age.set(++lastAge)
        LogUtil.d("Log function")
    }

    fun changeName() {
        val random = Random
        val newName = "张三" + random.nextInt(10)
        name.set(newName)
    }
}