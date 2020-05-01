package io.shivamvk.imgur_androidexample.network

import io.shivamvk.imgur_androidexample.models.ResponseModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header

interface ApiInterface {

    @GET("gallery/r/pics/")
    fun getImages(@Header("Authorization") token:String): Call<ResponseModel>
}