package net.svishch.android.outerspace.ui.adapter

import android.transition.Slide
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.ChangeBounds
import androidx.transition.ChangeImageTransform
import androidx.transition.TransitionManager
import androidx.transition.TransitionSet
import coil.api.load
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_mars_photos.view.*
import net.svishch.android.pictureoftheday.R
import net.svishch.android.pictureoftheday.apiNasa.mars.entity.Photo


class MarsPhotosRVAdapter(private val values: List<Photo>) :
    RecyclerView.Adapter<MarsPhotosRVAdapter.ViewHolder>() {

    // Размер списка
    override fun getItemCount() = values.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView =
            LayoutInflater.from(parent?.context).inflate(R.layout.item_mars_photos, parent, false)
        return ViewHolder(itemView)
    }

    // Позиция
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.setInfo(values[position].earthDate.toString())
        holder.setImg(values[position].imgSrc.toString())

        holder.containerView.setOnClickListener {
            zoom(holder)
        }
    }

    class ViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView),
        LayoutContainer {
        var isExpanded = false
        fun setInfo(text: String) = with(containerView) {
            tv_info.text = text
        }

        fun setImg(url: String) = with(containerView) {
            iv_img.load(url) {
                error(R.drawable.ic_load_error_vector)
                placeholder(R.drawable.ic_no_photo_vector)
            }
        }

    }

    private fun zoom(holder: ViewHolder) {

        holder.isExpanded = !holder.isExpanded

        //Анимация картинки
        val img = holder.containerView.iv_img

        TransitionManager.beginDelayedTransition(
            holder.containerView.iv_LinearLayout, TransitionSet()
                .addTransition(ChangeBounds())
                .addTransition(ChangeImageTransform())
        )

        img.scaleType =
            if (holder.isExpanded) ImageView.ScaleType.CENTER_CROP else
                ImageView.ScaleType.FIT_CENTER

        // Анимация текста
        android.transition.TransitionManager.beginDelayedTransition(
            holder.containerView.mars_info,
            Slide(Gravity.END).setDuration(600L)
        )
        holder.containerView.tv_info.visibility = if (holder.isExpanded) View.VISIBLE else View.GONE

    }

}