package yll.self.testapp.mvvm.lifecycle

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


data class User(var name: String, var age: Int, var sex: Int)

class UserViewModel : ViewModel() {
//    val user = User("jack", 20, 1)
    //让user具有生命周期感知能力
    val user = MutableLiveData<User>()
}