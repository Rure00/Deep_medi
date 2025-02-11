package com.rure.deepmedi.data.retrofit

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL = "https://iot.deep-medi.com/api/"
    private const val UPLOAD_URL = "http://blockchain.deep-medi.com/deepmedi-test-first"

    private val okHttpClient=  OkHttpClient().newBuilder()
        .addInterceptor(HttpLoggingInterceptor()).build()
    private val gson = GsonBuilder()
        .setLenient()
        .create()

    val apiInstance = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    val uploadInstance = Retrofit.Builder()
        .baseUrl(UPLOAD_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()
}