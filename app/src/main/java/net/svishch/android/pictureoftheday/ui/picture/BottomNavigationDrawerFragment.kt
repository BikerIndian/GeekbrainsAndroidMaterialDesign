package net.svishch.android.pictureoftheday.ui.picture

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

import kotlinx.android.synthetic.main.bottom_navigation_layout.*
import net.svishch.android.pictureoftheday.R

class BottomNavigationDrawerFragment : BottomSheetDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.bottom_navigation_layout, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        navigation_view.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.navigation_one -> selectTheme(AppTheme.THEME_PICTURE_OF_THE_DAY)
                R.id.navigation_two -> selectTheme(AppTheme.THEME_MARS)
            }
            true
        }
    }

    private fun selectTheme(theme: Int){
        activity?.getPreferences(AppCompatActivity.MODE_PRIVATE)?.let { AppTheme.saveTheme(it,theme) }
        activity?.recreate()
    }

}
