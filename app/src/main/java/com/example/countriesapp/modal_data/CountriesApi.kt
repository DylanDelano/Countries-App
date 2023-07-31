package com.example.countriesapp.modal_data

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET

interface CountriesApi {

    companion object{
        const val BASE_URL= "https://restcountries.com/v3.1/"
    }
    @GET("all")
    suspend fun getAllCountries():Response<List<countries_data>>



}