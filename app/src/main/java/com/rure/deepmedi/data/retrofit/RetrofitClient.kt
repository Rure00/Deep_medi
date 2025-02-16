package com.rure.deepmedi.data.retrofit

import com.google.gson.GsonBuilder
import com.rure.deepmedi.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private val BASE_URL = BuildConfig.API_URL
    private val UPLOAD_URL = BuildConfig.UPLOAD_URL

    private val okHttpClient=  OkHttpClient().newBuilder()
        .addInterceptor(HttpLoggingInterceptor()).build()
    private val gson = GsonBuilder()
        .setLenient()
        .create()

    val apiInstance: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    val uploadInstance: Retrofit = Retrofit.Builder()
        .baseUrl(UPLOAD_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()
}