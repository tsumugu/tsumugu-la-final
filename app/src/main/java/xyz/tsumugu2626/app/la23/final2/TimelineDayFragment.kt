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
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


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

        mainActivityViewModel.currentTimeMillis.observe(viewLifecycleOwner) { currentTimeMillis ->
            drawTimeline(currentTimeMillis)
        }

        timelineDayViewModel.timelineEvent.observe(viewLifecycleOwner) {
            mainActivityViewModel.currentTimeMillis.value?.let {
                    drawTimeline(it)
            }
        }

    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun drawTimeline(currentTimeMillis: Long) {
        timelineDayViewModel.timelineEvent.value?.let {
            binding.recyclerView.adapter = TimelineEventAdapter(
                timelineDayViewModel.genTimeLineEvents(it, currentTimeMillis)
            )
        }
    }
}