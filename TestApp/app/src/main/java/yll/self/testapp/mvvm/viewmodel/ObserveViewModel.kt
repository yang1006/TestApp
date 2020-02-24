package yll.self.testapp.mvvm.viewmodel

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.library.baseAdapters.BR
import kotlin.random.Random

class ObserveViewModel : BaseObservable(){

    var mFirstName : String = "ll"

    var mLastName : String = "y"

    @Bindable
    fun getFirstName() : String {
        return mFirstName
    }

    @Bindable
    fun getLastName() : String {
        return mLastName
    }

    fun setFirstName(s: String) {
        mFirstName = s
        notifyPropertyChanged(BR.firstName)
    }

    fun setLastName(s: String) {
        mLastName = s
        notifyPropertyChanged(BR.lastName)
    }

    fun changeLastName() {
        val random = Random
        setLastName("yyyy" + random.nextInt(10))
    }

}