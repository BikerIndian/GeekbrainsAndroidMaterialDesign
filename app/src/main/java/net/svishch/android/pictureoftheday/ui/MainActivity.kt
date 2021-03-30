package net.svishch.android.pictureoftheday.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import net.svishch.android.pictureoftheday.R
import net.svishch.android.pictureoftheday.ui.picture.AppTheme
import net.svishch.android.pictureoftheday.ui.viewPager.DayFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        // Загрузка темы
        setTheme(AppTheme.getTheme(getPreferences(MODE_PRIVATE)))
        R.style.Widget_Design_TextInputLayout
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, DayFragment.newInstance())
                .commitNow()
        }
    }
}