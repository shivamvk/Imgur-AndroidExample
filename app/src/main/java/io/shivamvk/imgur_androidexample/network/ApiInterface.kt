package io.shivamvk.imgur_androidexample.network

import io.shivamvk.imgur_androidexample.models.ResponseModel
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*
import java.io.File

interface ApiInterface {

    @GET("gallery/r/pics/")
    fun getImages(@Header("Authorization") token:String): Call<ResponseModel>

    @POST("image/")
    fun uploadImage(
        @Header("Authorization") token: String,
        @Query("title") title: String,
        @Query("description") description: String,
        @Query("album") album: String,
        @Query("account_url") username: String,
        @Part file: MultipartBody.Part
    ): Call<ResponseModel>
}