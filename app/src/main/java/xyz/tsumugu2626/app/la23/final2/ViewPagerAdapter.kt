package xyz.tsumugu2626.app.la23.final2

import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPagerAdapter(mainActivity: MainActivity) : FragmentStateAdapter(mainActivity) {

    private var _currentPageMillis: Long = 0

    override fun getItemCount(): Int = Int.MAX_VALUE

    fun setCurrentPageMillis(currentPageMillis: Long) {
        _currentPageMillis = currentPageMillis
    }

    override fun createFragment(position: Int): Fragment = TimelineDayFragment().apply {
        bundleOf(
            "CURRENT_PAGE_MILLIS" to _currentPageMillis
        )
    }
}