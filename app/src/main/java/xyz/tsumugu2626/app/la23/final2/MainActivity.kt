package xyz.tsumugu2626.app.la23.final2

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import xyz.tsumugu2626.app.la23.final2.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

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
            binding.pageNumberText.text = currentTimeMillis.millisToYmdStr()
        }

        binding.nextButton.setOnClickListener {
            binding.pager.currentItem = binding.pager.currentItem + 1
        }

        binding.prevButton.setOnClickListener {
            binding.pager.currentItem = binding.pager.currentItem - 1
        }

        binding.addEventButton.setOnClickListener {
            val url = mainActivityViewModel.fabUrl.value
            if (url != null) {
                Log.d("OpenUrl", url.toString())
                val uri = Uri.parse(url.toString())
                val i = Intent(Intent.ACTION_VIEW, uri)
                startActivity(i);
            } else {
                Toast.makeText(applicationContext, "エラーが発生しました。もう一度時間を空けて試してみてください", Toast.LENGTH_SHORT).show()
            }
        }
    }
}