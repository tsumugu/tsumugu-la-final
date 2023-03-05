package xyz.tsumugu2626.app.la23.final2

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

import androidx.lifecycle.ViewModel
import java.text.SimpleDateFormat
import java.util.Date


class MainActivityViewModel : ViewModel() {

    private val _currentTimeMillis: MutableLiveData<Long> =
        MutableLiveData<Long>(System.currentTimeMillis())
    val currentTimeMillis: LiveData<Long> get() = _currentTimeMillis

    fun oneDayMillis(): Int {
        return 24 * 60 * 60 * 1000
    }

    fun next() {
        _currentTimeMillis.value = _currentTimeMillis.value?.plus(oneDayMillis())
    }

    fun prev() {
        _currentTimeMillis.value = _currentTimeMillis.value?.minus(oneDayMillis())
    }

}

fun Long.toDateStr(): String = SimpleDateFormat("yyyy/MM/dd").format(Date(this))