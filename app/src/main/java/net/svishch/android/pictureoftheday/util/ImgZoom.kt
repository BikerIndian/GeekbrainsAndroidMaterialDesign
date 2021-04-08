package net.svishch.android.pictureoftheday.util

import android.view.ViewGroup
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.transition.ChangeBounds
import androidx.transition.ChangeImageTransform
import androidx.transition.TransitionSet
import kotlinx.android.synthetic.main.fragment_main.*

class ImgZoom(var image_view: EquilateralImageView, var main: ViewGroup) {
    private var isExpanded = false
    init {
        image_view.setOnClickListener {
            setZoomImg()
        }
    }

    private fun setZoomImg() {

        isExpanded = !isExpanded
        //Анимация картинки
        val img = image_view

        androidx.transition.TransitionManager.beginDelayedTransition(
            main, TransitionSet()
                .addTransition(ChangeBounds())
                .addTransition(ChangeImageTransform())
        )

        img.scaleType =
            if (isExpanded) ImageView.ScaleType.CENTER_CROP else
                ImageView.ScaleType.FIT_CENTER
    }
}