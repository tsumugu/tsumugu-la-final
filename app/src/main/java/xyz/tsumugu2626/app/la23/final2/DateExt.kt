package xyz.tsumugu2626.app.la23.final2

import android.os.Build
import androidx.annotation.RequiresApi
import java.text.SimpleDateFormat
import java.time.ZoneId
import java.time.ZonedDateTime
import java.util.Date

@RequiresApi(Build.VERSION_CODES.O)
fun Date.toLocaleEpochSeconds(): Long = ZonedDateTime.ofInstant(this.toInstant(), ZoneId.systemDefault()).toInstant().toEpochMilli() / 1000