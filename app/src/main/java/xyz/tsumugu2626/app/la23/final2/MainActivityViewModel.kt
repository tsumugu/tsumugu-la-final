package xyz.tsumugu2626.app.la23.final2

import androidx.lifecycle.MutableLiveData

import androidx.lifecycle.ViewModel
import java.text.SimpleDateFormat
import java.util.Date


class MainActivityViewModel : ViewModel() {

    private var millis = System.currentTimeMillis()
    val date: MutableLiveData<String>
        = MutableLiveData<String>(millis.toDateStr())

    fun next() {
        millis += 24*60*60*1000
        date.value = millis.toDateStr()
    }

    fun prev() {
        millis -= 24*60*60*1000
        date.value = millis.toDateStr()
    }

}

fun Long.toDateStr(): String = SimpleDateFormat("yyyy/MM/dd").format(Date(this))