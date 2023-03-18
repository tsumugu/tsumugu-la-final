package xyz.tsumugu2626.app.la23.final2

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Calendar

fun Long.millisToYmdhmsStr(): String = SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(Date(this))

fun Long.millisToYmdStr(): String = SimpleDateFormat("yyyy/MM/dd").format(Date(this))

fun Long.millisToHmStr(): String = SimpleDateFormat("HH:mm").format(Date(this))

fun Long.millisToStartOfDate(): Date {
    val calendar = Calendar.getInstance()
    calendar.time = Date(this)
    calendar.set(Calendar.HOUR_OF_DAY, 0)
    calendar.set(Calendar.MINUTE, 0)
    calendar.set(Calendar.SECOND, 0)
    calendar.set(Calendar.MILLISECOND, 0)
    return calendar.time
}