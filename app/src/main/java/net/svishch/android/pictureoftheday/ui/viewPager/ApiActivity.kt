package net.svishch.android.pictureoftheday.ui.viewPager

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import androidx.viewpager.widget.ViewPager
import kotlinx.android.synthetic.main.activity_api.*
import net.svishch.android.pictureoftheday.R
import net.svishch.android.pictureoftheday.ui.picture.AppTheme


private const val DAY = 0
private const val MARS = 1


class ApiActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_api)
        view_pager.adapter = ViewPagerAdapter(supportFragmentManager)
        tab_layout.setupWithViewPager(view_pager)
       setHighlightedTab(0)

        view_pager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {

            override fun onPageSelected(position: Int) {
                setHighlightedTab(position)
            }

            override fun onPageScrollStateChanged(state: Int) {}
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }
        })

        setTheme(AppTheme.getTheme(getPreferences(MODE_PRIVATE)))

    }

    private fun setHighlightedTab(position: Int) {
        val layoutInflater = LayoutInflater.from(this@ApiActivity)

        tab_layout.getTabAt(DAY)?.customView = null
        tab_layout.getTabAt(MARS)?.customView = null

        when (position) {
            DAY -> {
                setDeyTabHighlighted(layoutInflater)
            }
            MARS -> {
                setMarsTabHighlighted(layoutInflater)
            }
            else -> {
                setDeyTabHighlighted(layoutInflater)
            }
        }
    }

    private fun setDeyTabHighlighted(layoutInflater: LayoutInflater) {
        val day =
                layoutInflater.inflate(R.layout.activity_api_custom_tab_day, null)

        setSelectColor(day)

        tab_layout.getTabAt(DAY)?.customView = day
        tab_layout.getTabAt(MARS)?.customView =
                layoutInflater.inflate(R.layout.activity_api_custom_tab_mars, null)
    }

    private fun setMarsTabHighlighted(layoutInflater: LayoutInflater) {
        val mars =
            layoutInflater.inflate(R.layout.activity_api_custom_tab_mars, null)
        setSelectColor(mars)

        tab_layout.getTabAt(DAY)?.customView =
            layoutInflater.inflate(R.layout.activity_api_custom_tab_day, null)
        tab_layout.getTabAt(MARS)?.customView = mars
    }

    fun setSelectColor( view: View){
        view.findViewById<AppCompatTextView>(R.id.tab_image_textview)
                .setTextColor(
                        ContextCompat.getColor(
                                this@ApiActivity,
                                R.color.colorAccent
                        )
                )

    }
}
