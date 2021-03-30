package net.svishch.android.pictureoftheday.apiNasa

import net.svishch.android.pictureoftheday.apiNasa.mars.entity.Photos
import net.svishch.android.pictureoftheday.apiNasa.apod.entity.PODServerResponseData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface PictureOfTheDayAPI {

    @GET("planetary/apod")
    fun getPictureOfTheDay(@Query("api_key") apiKey: String): Call<PODServerResponseData>

    @GET("planetary/apod")
    fun getPictureOfTheDayDate(
            @Query("api_key") apiKey: String,
            @Query("date") date: String
    ):Call<PODServerResponseData>

    @GET("mars-photos/api/v1/rovers/curiosity/photos?sol=3030")
    fun getPhotos(
            @Query("api_key") apiKey: String
    ):Call<Photos>
}
