package xyz.tsumugu2626.app.la23.final2

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import xyz.tsumugu2626.app.la23.final2.databinding.TimelineDayFragmentBinding


class TimelineDayFragment : Fragment() {

    private lateinit var binding: TimelineDayFragmentBinding
    private val timelineDayViewModel: TimelineDayViewModel by viewModels()
    private val mainActivityViewModel: MainActivityViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = TimelineDayFragmentBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerView.layoutManager = TimelineEventLayoutManager(view.context)
        val dividerItemDecoration = DividerItemDecoration(view.context, LinearLayoutManager(view.context).orientation)
        binding.recyclerView.addItemDecoration(dividerItemDecoration)
        var localCurrentTimeMillis: Long = 0

        timelineDayViewModel.timelineEvent.observe(viewLifecycleOwner) { timelineEvent ->
            if (timelineEvent != null) {
                drawTimeLine(timelineEvent, localCurrentTimeMillis)
            }
        }

        mainActivityViewModel.currentTimeMillis.observe(viewLifecycleOwner) { currentTimeMillis ->
            timelineDayViewModel.load(currentTimeMillis)
            localCurrentTimeMillis = currentTimeMillis
        }

    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun drawTimeLine(timelineEvent: ArrayList<TimelineEvent>, currentTimeMillis: Long) {
        // 時間順で降順
//        timelineEvent.sortedWith(compareBy {
//            it.startedAt.toLocaleEpochSeconds()
//        })

        var tmpTimelineEventList = timelineEvent
        for (i in 0..23) {
            val event = TimelineEvent()
            event.type = "space"
            event.startHm = i.toString()
            event.dayHm = currentTimeMillis.toDateStr()
            tmpTimelineEventList.add(event)
        }
    }
}