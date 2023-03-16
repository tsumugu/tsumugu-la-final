package xyz.tsumugu2626.app.la23.final2

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPagerAdapter(mainActivity: MainActivity): FragmentStateAdapter(mainActivity) {

    private val realNumPage = 3

    override fun getItemCount(): Int = realNumPage + 2
    fun getRealCount() = realNumPage

    override fun createFragment(position: Int): Fragment {
//        val fragment = TimelineDayFragment()
//        val args = Bundle()
//        args.putInt("POSITION", )
//        fragment.setArguments(args)
//
//        return fragment
        return when(position) {
            0,3 -> TimelineDayFragment() // ThirdFragment()
            1,4 -> TimelineDayFragment() // FirstFragment()
            2 -> TimelineDayFragment() // SecondFragment()
            else -> TimelineDayFragment() // ThirdFragment()
        }
    }
}