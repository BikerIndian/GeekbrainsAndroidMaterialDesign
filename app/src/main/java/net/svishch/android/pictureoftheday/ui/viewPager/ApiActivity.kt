package net.svishch.android.pictureoftheday.ui.viewPager

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import androidx.viewpager.widget.ViewPager
import kotlinx.android.synthetic.main.activity_api.*
import net.svishch.android.pictureoftheday.R
import net.svishch.android.pictureoftheday.ui.picture.AppTheme


private const val EARTH = 0
private const val MARS = 1
private const val WEATHER = 2
private const val DAY = 3

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

        tab_layout.getTabAt(EARTH)?.customView = null
        tab_layout.getTabAt(MARS)?.customView = null
        tab_layout.getTabAt(WEATHER)?.customView = null
      //  tab_layout.getTabAt(DAY)?.customView = null

        when (position) {
            DAY -> {
                setDeyTabHighlighted(layoutInflater)
            }
            EARTH -> {
                setEarthTabHighlighted(layoutInflater)
            }
            MARS -> {
                setMarsTabHighlighted(layoutInflater)
            }
            WEATHER -> {
                setWeatherTabHighlighted(layoutInflater)
            }
            else -> {
                setDeyTabHighlighted(layoutInflater)
            }
        }
    }

    private fun setDeyTabHighlighted(layoutInflater: LayoutInflater) {
        val earth =
                layoutInflater.inflate(R.layout.activity_api_custom_tab_day, null)
        earth.findViewById<AppCompatTextView>(R.id.tab_image_textview)
                .setTextColor(
                        ContextCompat.getColor(
                                this@ApiActivity,
                                R.color.colorAccent
                        )
                )
        tab_layout.getTabAt(EARTH)?.customView = earth
        tab_layout.getTabAt(MARS)?.customView =
                layoutInflater.inflate(R.layout.activity_api_custom_tab_mars, null)
        tab_layout.getTabAt(WEATHER)?.customView =
                layoutInflater.inflate(R.layout.activity_api_custom_tab_weather, null)
    }
    private fun setEarthTabHighlighted(layoutInflater: LayoutInflater) {
        val earth =
            layoutInflater.inflate(R.layout.activity_api_custom_tab_day, null)
        earth.findViewById<AppCompatTextView>(R.id.tab_image_textview)
            .setTextColor(
                ContextCompat.getColor(
                    this@ApiActivity,
                    R.color.colorAccent
                )
            )
        tab_layout.getTabAt(EARTH)?.customView = earth
        tab_layout.getTabAt(MARS)?.customView =
            layoutInflater.inflate(R.layout.activity_api_custom_tab_mars, null)
        tab_layout.getTabAt(WEATHER)?.customView =
            layoutInflater.inflate(R.layout.activity_api_custom_tab_weather, null)
    }

    private fun setMarsTabHighlighted(layoutInflater: LayoutInflater) {
        val mars =
            layoutInflater.inflate(R.layout.activity_api_custom_tab_mars, null)
        mars.findViewById<AppCompatTextView>(R.id.tab_image_textview)
            .setTextColor(
                ContextCompat.getColor(
                    this@ApiActivity,
                    R.color.colorAccent
                )
            )
        tab_layout.getTabAt(EARTH)?.customView =
            layoutInflater.inflate(R.layout.activity_api_custom_tab_day, null)
        tab_layout.getTabAt(MARS)?.customView = mars
        tab_layout.getTabAt(WEATHER)?.customView =
            layoutInflater.inflate(R.layout.activity_api_custom_tab_weather, null)
    }

    private fun setWeatherTabHighlighted(layoutInflater: LayoutInflater) {
        val weather =
            layoutInflater.inflate(R.layout.activity_api_custom_tab_weather, null)
        weather.findViewById<AppCompatTextView>(R.id.tab_image_textview)
            .setTextColor(
                ContextCompat.getColor(
                    this@ApiActivity,
                    R.color.colorAccent
                )
            )
        tab_layout.getTabAt(EARTH)?.customView =
            layoutInflater.inflate(R.layout.activity_api_custom_tab_day, null)
        tab_layout.getTabAt(MARS)?.customView =
            layoutInflater.inflate(R.layout.activity_api_custom_tab_mars, null)
        tab_layout.getTabAt(WEATHER)?.customView = weather
    }
}
