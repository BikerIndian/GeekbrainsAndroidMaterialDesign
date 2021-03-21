package net.svishch.android.pictureoftheday.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import net.svishch.android.pictureoftheday.R
import net.svishch.android.pictureoftheday.ui.picture.PictureOfTheDayFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, PictureOfTheDayFragment.newInstance())
                .commitNow()
        }
    }
}