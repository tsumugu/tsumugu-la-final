package xyz.tsumugu2626.app.la23.final2

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPagerAdapter(mainActivity: MainActivity): FragmentStateAdapter(mainActivity) {

    private val _realNumPage = 3
    private var _currentPageMillis: Long = 0

    override fun getItemCount(): Int = _realNumPage + 2
    fun getRealCount() = _realNumPage

    fun setCurrentPageMillis(currentPageMillis: Long) {
        _currentPageMillis = currentPageMillis
    }

    override fun createFragment(position: Int): Fragment {
        val fragment = TimelineDayFragment()

        val args = Bundle()
        args.putLong("CURRENT_PAGE_MILLIS", _currentPageMillis)
        fragment.arguments = args

        return fragment
    }
}