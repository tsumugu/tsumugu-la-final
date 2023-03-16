package xyz.tsumugu2626.app.la23.final2

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import xyz.tsumugu2626.app.la23.final2.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    // 無限スクロール参考 : https://qiita.com/leb397/items/b78b8f526e86d7699dea

    private lateinit var binding: ActivityMainBinding
    private val mainActivityViewModel: MainActivityViewModel by viewModels<MainActivityViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater).apply { setContentView(this.root) }

        val viewPagerAdapter = ViewPagerAdapter(this)

        val viewPagerOnPageChangeCallBack = object : ViewPager2.OnPageChangeCallback() {
            private var currentPosition = Int.MAX_VALUE / 2

            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                if (position > currentPosition) mainActivityViewModel.onNextButtonClicked()
                else mainActivityViewModel.onPrevButtonClicked()

                currentPosition = position
            }
        }

        binding.pager.apply {
            adapter = viewPagerAdapter
            currentItem = Int.MAX_VALUE / 2
            registerOnPageChangeCallback(viewPagerOnPageChangeCallBack)
        }

        mainActivityViewModel.currentTimeMillis.observe(this) { currentTimeMillis ->
            binding.pageNumberText.text = currentTimeMillis.toDateStr()
        }

        binding.nextButton.setOnClickListener {
            binding.pager.currentItem = binding.pager.currentItem + 1
        }

        binding.prevButton.setOnClickListener {
            binding.pager.currentItem = binding.pager.currentItem - 1
        }
    }
}