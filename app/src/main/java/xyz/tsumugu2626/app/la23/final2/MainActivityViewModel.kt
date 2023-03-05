package xyz.tsumugu2626.app.la23.final2

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

import androidx.lifecycle.ViewModel
import java.text.SimpleDateFormat
import java.util.Date


class MainActivityViewModel : ViewModel() {

    private var _currentTimeMillis: MutableLiveData<Long>
            = MutableLiveData<Long>(System.currentTimeMillis())
    val currentTimeMillis: LiveData<Long> get() = _currentTimeMillis

    fun next() {
        _currentTimeMillis += 24*60*60*1000
    }

    fun prev() {
        _currentTimeMillis -= 24*60*60*1000
    }

}

fun Long.toDateStr(): String = SimpleDateFormat("yyyy/MM/dd").format(Date(this))