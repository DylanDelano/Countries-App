package com.example.countriesapp.di

import android.content.Context
import androidx.navigation.common.ktx.R
import com.example.countriesapp.modal_data.CountriesApi
import com.example.countriesapp.modal_data.CountriesApi.Companion.BASE_URL
import com.example.countriesapp.modal_data.country_repo
import com.example.countriesapp.util.ConnectivityObserver
import com.example.countriesapp.util.NetworkConnectivityObserver
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideHttpClient():OkHttpClient{
        return OkHttpClient.Builder()
            .readTimeout(18,TimeUnit.SECONDS)
            .connectTimeout(18,TimeUnit.SECONDS)
            .addInterceptor(HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY })
            .build()
    }

    @Singleton
    @Provides
    fun provideCountriesApi(okHttpClient: OkHttpClient):CountriesApi {
       return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
           .client(okHttpClient)
            .build()
           .create(CountriesApi::class.java)
    }
    @Singleton
    @Provides
    fun provideCountryRepo(api: CountriesApi): country_repo {
        return country_repo(api)
    }
    @Singleton
    @Provides
    fun provideConnectivityObserver(@ApplicationContext context: Context): ConnectivityObserver{
        return NetworkConnectivityObserver(context)
    }



}