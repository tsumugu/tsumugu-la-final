package xyz.tsumugu2626.app.la23.final2

import java.text.SimpleDateFormat
import java.util.Date

fun Long.toDateStr(): String = SimpleDateFormat("yyyy/MM/dd").format(Date(this))
fun Long.toHmStr(): String = SimpleDateFormat("HH:mm").format(Date(this))