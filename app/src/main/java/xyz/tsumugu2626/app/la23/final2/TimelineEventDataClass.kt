package xyz.tsumugu2626.app.la23.final2

import com.google.firebase.firestore.DocumentId
import java.util.Date

data class TimelineEvent (
    @DocumentId
    val id: String = "",
    var type: String = "timeline_event",
    var createdAt: Date = Date(System.currentTimeMillis()),
    var updatedAt: Date = Date(System.currentTimeMillis()),
    var startedAt: Date = Date(System.currentTimeMillis()),
    var endedAt: Date = Date(System.currentTimeMillis()),
    var startHm: String = "",
    var endHm: String = "",
    var dayHm: String = "",
    val url: String = "",
    val users: MutableList<User>? = null
)