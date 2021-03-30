package net.svishch.android.pictureoftheday.apiNasa.mars

import net.svishch.android.pictureoftheday.apiNasa.mars.entity.Photos

sealed class MarsPhotoData {
    data class Success(val serverResponseData: Photos) : MarsPhotoData()
    data class Error(val error: Throwable) : MarsPhotoData()
    data class Loading(val progress: Int?) : MarsPhotoData()
}