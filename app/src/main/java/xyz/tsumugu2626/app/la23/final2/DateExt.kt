package xyz.tsumugu2626.app.la23.final2

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.ZoneId
import java.time.ZonedDateTime
import java.util.Date
import java.util.Calendar

@RequiresApi(Build.VERSION_CODES.O)
fun Date.toLocaleEpochSeconds(): Long = ZonedDateTime.ofInstant(this.toInstant(), ZoneId.systemDefault()).toInstant().toEpochMilli() / 1000

fun Date.offsetDate(offsetHour: Int, offsetMinute: Int): Date {
    val calendar = Calendar.getInstance()
    calendar.time = this
    calendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY) + offsetHour)
    calendar.set(Calendar.MINUTE, calendar.get(Calendar.MINUTE) + offsetMinute)
    return calendar.time
}

fun Date.setMinute(minute: Int): Date {
    val calendar = Calendar.getInstance()
    calendar.time = this
    calendar.set(Calendar.MINUTE, minute)
    return calendar.time
}

fun Date.getMinute(): Int {
    val calendar = Calendar.getInstance()
    calendar.time = this
    return calendar.get(Calendar.MINUTE)
}