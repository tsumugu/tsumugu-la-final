package xyz.tsumugu2626.app.la23.final2

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import xyz.tsumugu2626.app.la23.final2.databinding.TimelineDayFragmentBinding

class TimelineDayFragment : Fragment() {

    companion object {
        private const val BUNDLE_KEY = "CURRENT_PAGE_MILLIS"
        fun instantiate(dateLong: Long) = TimelineDayFragment().apply {
            arguments = Bundle().apply { putLong(BUNDLE_KEY, dateLong) }
        }
    }

    private lateinit var binding: TimelineDayFragmentBinding
    private val timelineDayViewModel: TimelineDayViewModel by viewModels<TimelineDayViewModel>()
    private var dateMillis: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dateMillis = arguments?.getLong(BUNDLE_KEY) ?: 0
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = TimelineDayFragmentBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        timelineDayViewModel.load(dateMillis)
        timelineDayViewModel.timelineEvent.observe(viewLifecycleOwner) { timelineEvent ->
            if (timelineEvent != null) {
                binding.timelineView.timelineEvents = timelineEvent
            }
        }

    }
}