package xyz.tsumugu2626.app.la23.final2

import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.akribase.timelineview.Event
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.*
import kotlin.collections.ArrayList

@RequiresApi(Build.VERSION_CODES.O)
class TimelineDayViewModel(private val savedStateHandle: SavedStateHandle) : ViewModel() {

    companion object {
        private const val SAVED_STATE_HANDLE_KEY = "TIMELINE_EVENT_DATA"
    }
    private val _timelineEvent: MutableLiveData<ArrayList<TimelineEvent>>
        = savedStateHandle.getLiveData(SAVED_STATE_HANDLE_KEY, ArrayList<TimelineEvent>())
    val timelineEvent: LiveData<ArrayList<TimelineEvent>> get() = _timelineEvent

    init {
       loadDataFromFirebase()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun loadDataFromFirebase() {

        val db = Firebase.firestore

        db.collection("events").addSnapshotListener { events, e ->
            if (e != null) {
                Log.e("error", "Listen failed.", e)
                return@addSnapshotListener
            }

            if (events != null) {
                val tmpTimelineEvent =  ArrayList<TimelineEvent>()
                events.forEach {
                    val event = it.toObject(TimelineEvent::class.java)
                    event.startHm = (event.startedAt.toLocaleEpochSeconds() * 1000).millisToHmStr()
                    event.endHm = (event.endedAt.toLocaleEpochSeconds() * 1000).millisToHmStr()
                    event.dayHm = (event.startedAt.toLocaleEpochSeconds() * 1000).millisToYmdStr()
                    tmpTimelineEvent.add(event)
                }
                _timelineEvent.value = tmpTimelineEvent
                savedStateHandle.set(SAVED_STATE_HANDLE_KEY, _timelineEvent.value)
            } else {
                Log.d("error", "Current data: null")
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun genTimeLineEvents(timelineEvent: ArrayList<TimelineEvent>, currentTimeMillis: Long, context: Context): List<TimelineEvent> {

        val currentDateStr = currentTimeMillis.millisToYmdStr()

        val filteredTimelineEvent = timelineEvent.filter { it.dayHm == currentDateStr }.sortedBy { it.createdAt }

        val tmpTimelineEventList = ArrayList<TimelineEvent>()

        //0時0分から23時59分までの時間帯において、既に存在しているイベントを考慮しながら、1時間ごとにTimelineEventを生成し、tmpTimelineEventListに追加する
        val startOfDate = currentTimeMillis.millisToStartOfDate()
        val startTime = startOfDate.offsetDate(0, -1)
        val endTime = startOfDate.offsetDate(24, 0)
        var prevEndTime = startTime
        for (i in 0..100) { // ループさせたいが無限ループは怖いので適当に
            var startedAt = prevEndTime.offsetDate(0, 1)
            var endedAt = startedAt.offsetDate(0, 59)
            if (endedAt.after(endTime)) {
                break
            }
            // 枠が重なっていないかを判定
            var overlappedEvent: TimelineEvent? = null
            for (event in filteredTimelineEvent) {
                if (startedAt.before(event.endedAt) && endedAt.after(event.startedAt)) {
                    if (startedAt <= event.startedAt && endedAt <= event.endedAt) {
                        endedAt = event.startedAt
                    } else if (startedAt  <= event.startedAt && !(endedAt  <= event.endedAt)) {
                        endedAt = event.startedAt
                    } else if (!(startedAt <= event.startedAt) && endedAt <= event.endedAt) {
                        endedAt = event.endedAt
                    } else if (!(startedAt <= event.startedAt) && !(endedAt <= event.endedAt)) {
                        endedAt = event.endedAt
                    }
                    overlappedEvent = event
                    break
                }
            }
            if (overlappedEvent == null) {
                endedAt = endedAt.setMinute(59)
            }
            // アイテムの追加
            val newEvent = TimelineEvent(
                type = "space",
                dayHm = (startOfDate.toLocaleEpochSeconds()*1000).millisToHmStr(),
                startHm = (startedAt.toLocaleEpochSeconds()*1000).millisToHmStr(),
                //endHm = (endedAt.toLocaleEpochSeconds()*1000).millisToHmStr(),
                startedAt = startedAt,
                endedAt = endedAt,
                heightPx = DP.dpToPx(DP.calcDpFromTimestamp(startedAt, endedAt), context)
            )
            if (overlappedEvent != null && startedAt.after(overlappedEvent.startedAt)) {
                newEvent.type = "timeline_event"
                newEvent.id = overlappedEvent.id
                newEvent.createdAt = overlappedEvent.createdAt
                newEvent.updatedAt = overlappedEvent.updatedAt
                newEvent.url = overlappedEvent.url
                newEvent.users = overlappedEvent.users
                newEvent.endHm = (overlappedEvent.endedAt.toLocaleEpochSeconds()*1000).millisToHmStr()
            }
            tmpTimelineEventList.add(newEvent)
            prevEndTime = endedAt
        }

        // 最後のひとつだけはendHmを追加
        val lastOneIndex = tmpTimelineEventList.size - 1
        val lastOneItem = tmpTimelineEventList.get(lastOneIndex)
        lastOneItem.endHm = (lastOneItem.endedAt.toLocaleEpochSeconds()*1000).millisToHmStr()
        tmpTimelineEventList.removeAt(lastOneIndex)
        tmpTimelineEventList.add(lastOneItem)

        return tmpTimelineEventList
    }
}