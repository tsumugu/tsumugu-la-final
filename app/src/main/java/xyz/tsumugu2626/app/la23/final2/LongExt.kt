package xyz.tsumugu2626.app.la23.final2

import java.text.SimpleDateFormat
import java.util.*

fun Long.toDateStr(): String = SimpleDateFormat("yyyy/MM/dd").format(Date(this))