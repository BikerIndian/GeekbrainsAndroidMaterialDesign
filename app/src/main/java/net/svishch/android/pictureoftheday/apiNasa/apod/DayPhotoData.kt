package net.svishch.android.pictureoftheday.apiNasa.apod

import net.svishch.android.pictureoftheday.apiNasa.apod.entity.PODServerResponseData

sealed class DayPhotoData {
    data class Success(val serverResponseData: PODServerResponseData) : DayPhotoData()
    data class Error(val error: Throwable) : DayPhotoData()
    data class Loading(val progress: Int?) : DayPhotoData()
}
