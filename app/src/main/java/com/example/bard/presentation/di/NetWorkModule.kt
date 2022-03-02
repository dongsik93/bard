package com.example.bard.presentation.di

import android.content.Context
import com.example.bard.data.source.remote.RetrofitService
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
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

@InstallIn(SingletonComponent::class)
@Module
object NetWorkModule {

    @Singleton
    @Provides
    fun providesRetrofit(
        gsonConverterFactory: GsonConverterFactory,
        coroutineCallAdapterFactory: CoroutineCallAdapterFactory,
        okHttpClient: OkHttpClient,
    ): Retrofit = Retrofit.Builder()
        .baseUrl("")
        .addConverterFactory(gsonConverterFactory)
        .addCallAdapterFactory(coroutineCallAdapterFactory)
        .client(okHttpClient)
        .build()

    @Singleton
    @Provides
    fun providesOkHttpClient(
        @ApplicationContext context: Context
    ): OkHttpClient {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        val client = OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .addNetworkInterceptor(interceptor)
        return client.build()
    }

    @Singleton
    @Provides
    fun providesGsonConverterFactory(): GsonConverterFactory = GsonConverterFactory.create()

    @Singleton
    @Provides
    fun providesCoroutineCallAdapterFactory(): CoroutineCallAdapterFactory = CoroutineCallAdapterFactory()

    @Singleton
    @Provides
    fun providesService(retrofit: Retrofit): RetrofitService = retrofit.create(RetrofitService::class.java)
}