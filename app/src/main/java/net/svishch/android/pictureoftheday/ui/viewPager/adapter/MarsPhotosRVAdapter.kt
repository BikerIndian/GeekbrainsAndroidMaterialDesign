package net.svishch.android.outerspace.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
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
        val itemView = LayoutInflater.from(parent?.context).inflate(R.layout.item_mars_photos, parent, false)
        return ViewHolder(itemView)
    }

    // Позиция
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.setInfo(values[position].earthDate.toString())
        holder.setImg(values[position].imgSrc.toString())

    }

     class ViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView),
            LayoutContainer {

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

}