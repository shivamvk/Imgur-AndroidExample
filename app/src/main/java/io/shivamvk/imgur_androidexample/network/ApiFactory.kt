package io.shivamvk.imgur_androidexample.network

import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object ApiFactory {

    const val BASE_URL = "https://api.imgur.com/3/"

    fun makeRetrofitService(): ApiInterface {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build().create(ApiInterface::class.java)
    }

    /*private fun makeOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(makeInterceptor())
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .writeTimeout(90, TimeUnit.SECONDS)
            .build()
    }

    private fun makeInterceptor(): Interceptor {
         fun intercept(chain: Interceptor.Chain): Response{
            var request = chain.request()
            request = request?.newBuilder()
                ?.addHeader("Authorization", "Client-ID ba496ffbb44166d")
                ?.build()
            return chain.proceed(request)
        }
    }*/

}