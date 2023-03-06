package xyz.tsumugu2626.app.la23.final2

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        timelineDayViewModel.timelineEvent.observe(viewLifecycleOwner) { timelineEvent ->
            if (timelineEvent != null) {
                binding.timelineView.timelineEvents = timelineEvent
            }
        }

        mainActivityViewModel.currentTimeMillis.observe(viewLifecycleOwner) {
            timelineDayViewModel.load(it)
        }
    }
}