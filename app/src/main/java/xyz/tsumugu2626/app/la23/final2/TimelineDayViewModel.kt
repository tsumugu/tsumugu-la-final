package xyz.tsumugu2626.app.la23.final2

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
    fun genTimeLineEvents(timelineEvent: ArrayList<TimelineEvent>, currentTimeMillis: Long): List<TimelineEvent> {

        val currentDateStr = currentTimeMillis.millisToYmdStr()

        val filteredTimelineEvent = timelineEvent.filter { it.dayHm == currentDateStr }.sortedBy { it.createdAt }

        val tmpTimelineEventList = ArrayList<TimelineEvent>()

        //0時0分から23時59分までの時間帯において、既に存在しているイベントを考慮しながら、1時間ごとにTimelineEventを生成し、tmpTimelineEventListに追加する
        val startOfDate = currentTimeMillis.millisToStartOfDate()
        val startTime = startOfDate.offsetDate(0, -1)
        val endTime = startOfDate.offsetDate(23, 59)
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
                    overlappedEvent = event
                    endedAt = event.endedAt // 重なっていたらendedAtをそのイベントのendedAtにする
                    break
                }
            }
//            if (overlappedEvent == null) {
//                endedAt = endedAt.setMinute(59); // 重なっていなかったらendedAtはHH:59にする
//            }
            Log.d("timebox", startedAt.toString()+" - "+endedAt.toString())
            // アイテムの追加
            if (overlappedEvent == null) {
                val newEvent = TimelineEvent(
                    type = "space",
                    startHm = (startedAt.toLocaleEpochSeconds()*1000).millisToHmStr(),
                    endHm = (endedAt.toLocaleEpochSeconds()*1000).millisToHmStr(),
                    dayHm = (startOfDate.toLocaleEpochSeconds()*1000).millisToHmStr(),
                    startedAt = startedAt,
                    endedAt = endedAt
                )
                tmpTimelineEventList.add(newEvent)
            } else {
                Log.d("overlappedEvent", overlappedEvent.startedAt.toString()+" / "+overlappedEvent.endedAt.toString())
                tmpTimelineEventList.add(overlappedEvent)
            }
            //
            prevEndTime = endedAt
        }
//        val startOfDate = currentTimeMillis.millisToStartOfDate()
//        val startTime = startOfDate.offsetDate(0, -1)
//        val endTime = startOfDate.offsetDate(23, 59)
//        var prevEndTime = startTime
//        for (i in 0..23) {
//            val startedAt = prevEndTime.offsetDate(0, 1)
//            val endedAt = startedAt.offsetDate(0, 59)
//            if (endedAt.after(endTime)) {
//                break
//            }
//            val newEvent = TimelineEvent(
//                            type = "space",
//                            startHm = (startedAt.toLocaleEpochSeconds()*1000).millisToHmStr(),
//                            endHm = (endedAt.toLocaleEpochSeconds()*1000).millisToHmStr(),
//                            dayHm = (startOfDate.toLocaleEpochSeconds()*1000).millisToHmStr(),
//                            startedAt = startedAt,
//                            endedAt = endedAt
//                        )
//            var overlapedEvents = ArrayList<TimelineEvent>()
//            for (event in tmpTimelineEventList) {
//                if (newEvent.startedAt.before(event.endedAt) && newEvent.endedAt.after(event.startedAt)) {
//                    overlapedEvents.add(event)
//                    break
//                }
//            }
//            if (overlapedEvents.size == 0) {
//                tmpTimelineEventList.add(newEvent)
//                prevEndTime = endedAt
//            } else {
//                Log.d("logddd", newEvent.startedAt.toString()+"-"+newEvent.endedAt.toString()+"  "+overlapedEvents.size.toString())
////                val segment = (newEvent.startedAt.time - prevEndTime.time) / 1000
////                Log.d("overlap", segment.toString())
////                for (j in 0 until segment step 60) {
////                    val subStartedAt = Calendar.getInstance().apply { time = prevEndTime }.apply { add(Calendar.MINUTE, j.toInt()) }.time
////                    val subEndedAt = Calendar.getInstance().apply { time = subStartedAt }.apply { add(Calendar.MINUTE, 0) }.time
////                    if (subEndedAt.after(newEvent.endedAt)) {
////                        break
////                    }
////                    val subEvent = TimelineEvent(
////                        type = "space",
////                        startHm = (subStartedAt.toLocaleEpochSeconds()*1000).millisToHmStr(),
////                        endHm = (subEndedAt.toLocaleEpochSeconds()*1000).millisToHmStr(),
////                        dayHm = (startOfDate.toLocaleEpochSeconds()*1000).millisToHmStr(),
////                        startedAt = subStartedAt,
////                        endedAt = subEndedAt
////                    )
////                    tmpTimelineEventList.add(subEvent)
////                }
//                prevEndTime = newEvent.endedAt
//            }
//        }
    }
}