package net.svishch.android.pictureoftheday.ui.picture

import net.svishch.android.pictureoftheday.apiNasa.mars.entity.Photos
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

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
