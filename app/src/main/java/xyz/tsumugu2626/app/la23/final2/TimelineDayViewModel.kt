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

class TimelineDayViewModel(private val savedStateHandle: SavedStateHandle) : ViewModel() {

    companion object {
        private const val SAVED_STATE_HANDLE_KEY = "TIMELINE_EVENT_DATA"
    }
    private val _timelineEvent: MutableLiveData<ArrayList<TimelineEvent>>
        = savedStateHandle.getLiveData(SAVED_STATE_HANDLE_KEY, ArrayList<TimelineEvent>())
    val timelineEvent: LiveData<ArrayList<TimelineEvent>> get() = _timelineEvent

    @RequiresApi(Build.VERSION_CODES.O)
    fun load(dateMillis: Long) {

        Log.d("tlvm-dateMillis", dateMillis.toString())

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
                    tmpTimelineEvent.add(event)
                }
                Log.d("tmpTimelineEvent", tmpTimelineEvent.size.toString())
                _timelineEvent.value = tmpTimelineEvent
                savedStateHandle.set(SAVED_STATE_HANDLE_KEY, _timelineEvent.value)
            } else {
                Log.d("error", "Current data: null")
            }
        }
    }
}