package xyz.tsumugu2626.app.la23.final2

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DefaultItemAnimator
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

        val taskAdapter = TimelineEventAdapter()
        binding.recyclerView.adapter = taskAdapter
        binding.recyclerView.layoutManager =
            LinearLayoutManager(viewLifecycleOwner, LinearLayoutManager.VERTICAL, false)

        timelineDayViewModel.timelineEvent.observe(viewLifecycleOwner) { timelineEvent ->
            if (timelineEvent != null) {

            }
        }

        mainActivityViewModel.currentTimeMillis.observe(viewLifecycleOwner) { currentTimeMillis ->
            timelineDayViewModel.load(currentTimeMillis)
        }

    }
}