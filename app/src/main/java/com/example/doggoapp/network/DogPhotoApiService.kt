package com.example.doggoapp.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

private const val BASE_URL = "https//dog.ceo/api/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

//private val networkLoggingInterceptor =
//    HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
//    .client(OkHttpClient.Builder().addInterceptor(networkLoggingInterceptor).build())
    .baseUrl(BASE_URL)
    .build()

interface DogPhotoApiService {

    //https://dog.ceo/api/breeds/image/random/
    @GET("/breeds/image/random/")
    suspend fun getRandomPhoto(): DogPhoto

}

object DogPhotoApi {
    val retrofitService: DogPhotoApiService by lazy {
        retrofit.create((DogPhotoApiService::class.java))
    }
}