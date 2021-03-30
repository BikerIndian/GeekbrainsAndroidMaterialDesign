package net.svishch.android.pictureoftheday.apiNasa.apod

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import net.svishch.android.pictureoftheday.BuildConfig
import net.svishch.android.pictureoftheday.apiNasa.PODRetrofitImpl
import net.svishch.android.pictureoftheday.apiNasa.apod.entity.PODServerResponseData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DayViewModel(
        private val livePhotoDataForViewToObserve: MutableLiveData<DayPhotoData> = MutableLiveData(),
        private val retrofitImpl: PODRetrofitImpl = PODRetrofitImpl()
) :
    ViewModel() {

    fun getData(): LiveData<DayPhotoData> {
        sendServerRequest()
        return livePhotoDataForViewToObserve
    }

    private fun sendServerRequest() {
        livePhotoDataForViewToObserve.value = DayPhotoData.Loading(null)
        val apiKey: String = BuildConfig.NASA_API_KEY
        if (apiKey.isBlank()) {
            DayPhotoData.Error(Throwable("You need API key"))
        } else {
            retrofitImpl.getRetrofitImpl().getPictureOfTheDay(apiKey).enqueue(object :
                Callback<PODServerResponseData> {
                override fun onResponse(
                        call: Call<PODServerResponseData>,
                        response: Response<PODServerResponseData>
                ) {
                    if (response.isSuccessful && response.body() != null) {
                        livePhotoDataForViewToObserve.value =
                            DayPhotoData.Success(response.body()!!)
                    } else {
                        val message = response.message()
                        if (message.isNullOrEmpty()) {
                            livePhotoDataForViewToObserve.value =
                                DayPhotoData.Error(Throwable("Unidentified error"))
                        } else {
                            livePhotoDataForViewToObserve.value =
                                DayPhotoData.Error(Throwable(message))
                        }
                    }
                }

                override fun onFailure(call: Call<PODServerResponseData>, t: Throwable) {
                    livePhotoDataForViewToObserve.value = DayPhotoData.Error(t)
                }
            })
        }
    }
}
