package net.svishch.android.pictureoftheday.apiNasa.mars.entity

import android.os.Parcelable
import com.google.gson.annotations.Expose
import kotlinx.android.parcel.Parcelize
import net.svishch.android.pictureoftheday.apiNasa.mars.entity.Photo

@Parcelize
class Photos(
    @Expose private var photos: List<Photo>? = null
) : Parcelable {


    fun getPhotos(): List<Photo>? {
        return photos
    }

    fun setPhotos(photos: List<Photo>?) {
        this.photos = photos
    }
}