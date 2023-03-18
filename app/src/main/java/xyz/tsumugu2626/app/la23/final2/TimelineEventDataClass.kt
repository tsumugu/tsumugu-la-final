package xyz.tsumugu2626.app.la23.final2

import com.google.firebase.firestore.DocumentId
import java.util.Date

data class TimelineEvent (
    @DocumentId
    var id: String = "",
    var type: String = "timeline_event",
    var createdAt: Date = Date(System.currentTimeMillis()),
    var updatedAt: Date = Date(System.currentTimeMillis()),
    var startedAt: Date = Date(System.currentTimeMillis()),
    var endedAt: Date = Date(System.currentTimeMillis()),
    var startHm: String = "",
    var endHm: String = "",
    var dayHm: String = "",
    var url: String = "",
    var users: MutableList<User>? = null,
    var heightPx: Int = 0
)