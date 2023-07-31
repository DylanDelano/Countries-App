package com.example.countriesapp.modal_data

import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class country_repo @Inject constructor(private val api: CountriesApi)
{
    suspend fun getAllCountries(): List<countries_data>{
        return api.getAllCountries().body()!!
    }

}





