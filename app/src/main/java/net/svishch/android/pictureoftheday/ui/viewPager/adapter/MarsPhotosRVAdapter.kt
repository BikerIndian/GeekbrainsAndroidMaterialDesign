package net.svishch.android.outerspace.ui.adapter

import android.graphics.Color
import android.transition.Slide
import android.view.*
import android.widget.ImageView
import androidx.core.view.MotionEventCompat
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
import net.svishch.android.pictureoftheday.ui.viewPager.adapter.ItemTouchHelperAdapter
import net.svishch.android.pictureoftheday.ui.viewPager.adapter.ItemTouchHelperViewHolder
import net.svishch.android.pictureoftheday.ui.viewPager.adapter.OnStartDragListener
import net.svishch.android.pictureoftheday.ui.viewPager.adapter.TextStyleMarsHelper


class MarsPhotosRVAdapter(
    private var data: List<Photo>,
    private val dragListener: OnStartDragListener
) :
    RecyclerView.Adapter<MarsPhotosRVAdapter.ViewHolder>(), ItemTouchHelperAdapter {
    private val values = data.toMutableList()


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
        holder.setInfoPhoto(values[position])

        holder.containerView.iv_img.setOnClickListener {
            zoom(holder)
        }
    }


    inner class ViewHolder(override val containerView: View) :
        RecyclerView.ViewHolder(containerView),
        LayoutContainer, ItemTouchHelperViewHolder {
        var cardBackgroundColor = 0
        var isExpanded = false
        var isInfo = false

        fun setInfo(text: String) = with(containerView) {
            tv_info.text = text
        }

        fun setImg(url: String) = with(containerView) {
            iv_img.load(url) {
                error(R.drawable.ic_load_error_vector)
                placeholder(R.drawable.ic_no_photo_vector)
            }
        }

        fun setInfoPhoto(photo: Photo) = with(containerView) {
            val text = String.format(
                "%s %s\n\n" +
                        "%s %s\n" +
                        "%s %s\n" +
                        "%s %s\n\n" +

                        "%s %s\n" +
                        "%s %s\n" +
                        "%s %s\n" +
                        "%s %s\n" +
                        "%s %s\n",

                context.getString(R.string.photo_id), photo.id,
                context.getString(R.string.camera_id), photo.camera?.id,
                context.getString(R.string.camera_name), photo.camera?.name,
                context.getString(R.string.camera_full_name), photo.camera?.fullName,
                context.getString(R.string.rover_id), photo.rover?.id,
                context.getString(R.string.rover_name), photo.rover?.name,
                context.getString(R.string.rover_landing_date), photo.rover?.landingDate,
                context.getString(R.string.rover_launch_date), photo.rover?.launchDate,
                context.getString(R.string.rover_status), photo.rover?.status,
            )

            marsInfoPhoto.text = TextStyleMarsHelper(context, text).getSpanInfoPhoto()
        }

        override fun onItemSelected() {
            cardBackgroundColor = itemView.iv_LinearLayout.cardBackgroundColor.defaultColor
            itemView.iv_LinearLayout.setCardBackgroundColor(Color.LTGRAY)
        }

        override fun onItemClear() {
            itemView.iv_LinearLayout.setCardBackgroundColor(cardBackgroundColor)
        }


        init {
            containerView.removeItemImageView.setOnClickListener { removeItem() }
            containerView.dragHandleImageView.setOnTouchListener { _, event ->
                if (MotionEventCompat.getActionMasked(event) == MotionEvent.ACTION_DOWN) {
                    dragListener.onStartDrag(this)
                }
                false
            }
            setInfoClick()
        }

        fun removeItem() {
            values.removeAt(layoutPosition)
            notifyItemRemoved(layoutPosition)
        }

        fun setInfoClick() {
            containerView.img_mars_photo_info.visibility = View.GONE

            containerView.img_mars_photo_info.setOnClickListener {
                android.transition.TransitionManager.beginDelayedTransition(
                    containerView.iv_LinearLayout,
                    Slide(Gravity.END).setDuration(600L)
                )

                isInfo = !isInfo
                containerView.marsInfoPhoto.visibility =
                    if (isInfo) View.VISIBLE else View.GONE

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


        if (holder.isExpanded) {
            holder.containerView.marsInfoPhoto.visibility = View.GONE
            holder.containerView.dragHandleImageView.visibility = View.GONE
            holder.containerView.removeItemImageView.visibility = View.GONE

        } else {
            holder.containerView.img_mars_photo_info.visibility = View.GONE
            holder.containerView.marsInfoPhoto.visibility = View.GONE
            holder.containerView.dragHandleImageView.visibility = View.VISIBLE
            holder.containerView.removeItemImageView.visibility = View.VISIBLE

            android.transition.TransitionManager.beginDelayedTransition(
                holder.containerView.iv_LinearLayout,
                Slide(Gravity.END).setDuration(600L)
            )
            holder.containerView.marsInfoPhoto.visibility = View.VISIBLE
        }
        img.scaleType =
            if (holder.isExpanded) {
                ImageView.ScaleType.CENTER_CROP
            } else {
                ImageView.ScaleType.FIT_CENTER
            }

        // Анимация текста
        android.transition.TransitionManager.beginDelayedTransition(
            holder.containerView.iv_LinearLayout,
            Slide(Gravity.END).setDuration(600L)
        )
        holder.containerView.tv_info.visibility = if (holder.isExpanded) View.VISIBLE else View.GONE

    }

    override fun onItemMove(fromPosition: Int, toPosition: Int) {
        values.removeAt(fromPosition).apply {
            values.add(if (toPosition > fromPosition) toPosition - 1 else toPosition, this)
        }
        notifyItemMoved(fromPosition, toPosition)
    }

    override fun onItemDismiss(position: Int) {
        values.removeAt(position)
        notifyItemRemoved(position)
    }

}