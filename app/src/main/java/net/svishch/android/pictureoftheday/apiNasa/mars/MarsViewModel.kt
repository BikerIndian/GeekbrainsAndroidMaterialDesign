package net.svishch.android.pictureoftheday.apiNasa.mars

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import net.svishch.android.pictureoftheday.BuildConfig
import net.svishch.android.pictureoftheday.apiNasa.mars.MarsPhotoData
import net.svishch.android.pictureoftheday.apiNasa.mars.entity.Photos
import net.svishch.android.pictureoftheday.ui.picture.PODRetrofitImpl
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MarsViewModel(
    private val liveDataForViewToObserve: MutableLiveData<MarsPhotoData> = MutableLiveData(),
    private val retrofitImpl: PODRetrofitImpl = PODRetrofitImpl()
) :
    ViewModel() {

    fun getMarsData(): LiveData<MarsPhotoData> {
        sendServerRequest()
        return liveDataForViewToObserve
    }

    private fun sendServerRequest() {
        liveDataForViewToObserve.value = MarsPhotoData.Loading(null)
        val apiKey: String = BuildConfig.NASA_API_KEY
        if (apiKey.isBlank()) {
            MarsPhotoData.Error(Throwable("You need API key"))
        } else {
            retrofitImpl.getMarsPhotos().getPhotos(apiKey).enqueue(object :
                Callback<Photos> {
                override fun onResponse(
                    call: Call<Photos>,
                    response: Response<Photos>
                ) {
                    if (response.isSuccessful && response.body() != null) {
                        liveDataForViewToObserve.value =
                                MarsPhotoData.Success(response.body()!!)
                    } else {
                        val message = response.message()
                        if (message.isNullOrEmpty()) {
                            liveDataForViewToObserve.value =
                                    MarsPhotoData.Error(Throwable("Unidentified error"))
                        } else {
                            liveDataForViewToObserve.value =
                                    MarsPhotoData.Error(Throwable(message))
                        }
                    }
                }

                override fun onFailure(call: Call<Photos>, t: Throwable) {
                    liveDataForViewToObserve.value = MarsPhotoData.Error(t)
                }
            })
        }
    }

}
