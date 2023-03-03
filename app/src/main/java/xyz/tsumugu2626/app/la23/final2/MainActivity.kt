package xyz.tsumugu2626.app.la23.final2

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import xyz.tsumugu2626.app.la23.final2.databinding.ActivityMainBinding
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    // 無限スクロール参考 : https://qiita.com/leb397/items/b78b8f526e86d7699dea

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewPager2: ViewPager2
    private lateinit var viewPagerAdapter: ViewPagerAdapter

    private val timelineModel: TimelineModel by lazy {
        ViewModelProvider.NewInstanceFactory().create(TimelineModel::class.java)
    }

    private val callBack = object : ViewPager2.OnPageChangeCallback() {
        private var realPosition = -1
        private var prevPosition = 1
        override fun onPageScrollStateChanged(state: Int) {
            super.onPageScrollStateChanged(state)
            if (state == ViewPager2.SCROLL_STATE_IDLE && realPosition >= 0) {
                viewPager2.setCurrentItem(realPosition, false)
                realPosition = -1
            }
        }
        override fun onPageSelected(position: Int) {
            super.onPageSelected(position)

            when (position - prevPosition) {
                -1 -> timelineModel.prev()
                1 -> timelineModel.next()
            }
            prevPosition = position

            when (position) {
                0 -> realPosition = viewPagerAdapter.getRealCount()
                viewPagerAdapter.getRealCount() + 1 -> realPosition = 1
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater).apply { setContentView(this.root) }

        //ViewPager2の設定
        viewPager2 = binding.pager
        viewPagerAdapter = ViewPagerAdapter(this)
        viewPager2.apply {
            adapter = viewPagerAdapter
            setCurrentItem(1,false)
            offscreenPageLimit = 1
            registerOnPageChangeCallback(callBack)
        }

        timelineModel.date.observe(this, { date ->
            Log.d("observeDate", date)
            binding.pageNumberText.setText(date)
        })

        binding.nextButton.setOnClickListener {
            viewPager2.setCurrentItem(viewPager2.currentItem + 1)
        }

        binding.prevButton.setOnClickListener {
            viewPager2.setCurrentItem(viewPager2.currentItem - 1)
        }

    }
}