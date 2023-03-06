package xyz.tsumugu2626.app.la23.final2

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.akribase.timelineview.Event

class TimelineDayViewModel(private val savedStateHandle: SavedStateHandle) : ViewModel() {

    companion object {
        private const val SAVED_STATE_HANDLE_KEY = "TIMELINE_EVENT_DATA"
    }
    private val _timelineEvent: MutableLiveData<ArrayList<Event>>
        = savedStateHandle.getLiveData(SAVED_STATE_HANDLE_KEY, ArrayList<Event>())
    val timelineEvent: LiveData<ArrayList<Event>> get() = _timelineEvent

    fun load(dateMillis: Long) {
        Log.d("dateMillisFromArg", dateMillis.toDateStr());
        // TODO: firebaseからの取得処理
        val tmpTimelineEvent = _timelineEvent
        tmpTimelineEvent.value?.apply {
            add(Event("vmから1", 1636949888, 1636959000))
            add(Event("vmから2", 1636960100, 1636966000))
            add(Event("vmから3", 1636967000, 1636987000))
        }
        _timelineEvent.value = tmpTimelineEvent.value
        savedStateHandle.set(SAVED_STATE_HANDLE_KEY, _timelineEvent.value)
    }
}