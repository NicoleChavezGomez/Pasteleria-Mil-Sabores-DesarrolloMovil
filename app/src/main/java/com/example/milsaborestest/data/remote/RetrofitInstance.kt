package com.example.milsaborestest.data.remote

import android.util.Log
import com.example.milsaborestest.util.Constants
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    private const val BASE_URL = "https://693e248ef55f1be793046cd9.mockapi.io/api/v1/"

    val api: ApiService by lazy {
        Log.d(Constants.TAG, "RetrofitInstance: Inicializando Retrofit con URL base: $BASE_URL")
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val apiService = retrofit.create(ApiService::class.java)
        Log.d(Constants.TAG, "RetrofitInstance: ApiService creado exitosamente")
        apiService
    }
}

