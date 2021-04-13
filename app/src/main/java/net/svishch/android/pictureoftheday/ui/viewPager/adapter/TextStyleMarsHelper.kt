package net.svishch.android.pictureoftheday.ui.viewPager.adapter

import android.content.Context
import android.graphics.Typeface.BOLD
import android.text.Spannable
import android.text.SpannableString
import android.text.style.StyleSpan
import net.svishch.android.pictureoftheday.R

class TextStyleMarsHelper(val context: Context, val text : String) {
    val spannable = SpannableString(text)

    fun getSpanInfoPhoto(): SpannableString {
        setBoldText(R.string.photo_id)

        setBoldText(R.string.camera_id)
        setBoldText(R.string.camera_name)
        setBoldText(R.string.camera_full_name)

        setBoldText(R.string.rover_id)
        setBoldText(R.string.rover_name)
        setBoldText(R.string.rover_landing_date)
        setBoldText(R.string.rover_launch_date)
        setBoldText(R.string.rover_status)


        return spannable
    }

    private fun setBoldText(str : Int){
        spannable.setSpan(
            StyleSpan(BOLD),
            geiIndexIn(str),  geiIndexOut(str),
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
    }

    private fun geiIndexIn(str: Int) : Int{
        return this.text.indexOf(context.getString(str))
    }

    private fun geiIndexOut(str: Int) : Int{
        return this.text.indexOf(context.getString(str))+context.getString(str).length
    }


}