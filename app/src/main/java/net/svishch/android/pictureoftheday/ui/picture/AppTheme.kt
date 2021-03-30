package net.svishch.android.pictureoftheday.ui.picture

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import net.svishch.android.pictureoftheday.R

class AppTheme {

    companion object {
        const  val APP_THEME_KEY = "net.svishch.android.pictureoftheday.ui.picture.AppTheme"
        const val THEME_MARS = 1
        const val THEME_PICTURE_OF_THE_DAY = 2

        fun getTheme(sharedPref: SharedPreferences): Int {
            return getTheme(sharedPref.getInt(APP_THEME_KEY,1))
        }

        fun saveTheme(sharedPref: SharedPreferences, theme: Int) {
            val sharedPrefEdit: SharedPreferences.Editor = sharedPref.edit()
            sharedPrefEdit.putInt(APP_THEME_KEY, theme)
            sharedPrefEdit.commit()
        }
        private fun getTheme(theme:Int) : Int{
            when (theme) {
                THEME_MARS ->  return R.style.Theme_Mars
                THEME_PICTURE_OF_THE_DAY ->  return R.style.Theme_PictureOfTheDay
                else  ->  return R.style.Theme_PictureOfTheDay
            }
        }

        lateinit var activity : FragmentActivity
        fun selectThemeOnMenu(itemId: Int, activity : FragmentActivity){
            this.activity = activity
            when (itemId) {
                R.id.navigation_one -> selectTheme(THEME_PICTURE_OF_THE_DAY)
                R.id.navigation_two -> selectTheme(THEME_MARS)
            }
        }
        private fun selectTheme(theme: Int){
            activity?.getPreferences(AppCompatActivity.MODE_PRIVATE)?.let {saveTheme(it,theme) }
            activity?.recreate()
        }

    }
}