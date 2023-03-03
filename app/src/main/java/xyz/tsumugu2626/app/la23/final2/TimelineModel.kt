package xyz.tsumugu2626.app.la23.final2

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

import androidx.lifecycle.ViewModel
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Date
import kotlin.time.Duration.Companion.milliseconds


class TimelineModel : ViewModel() {

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