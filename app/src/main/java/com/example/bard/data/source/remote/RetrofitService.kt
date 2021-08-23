package com.example.bard.data.source.remote

import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET

interface RetrofitService {
    /**
     * CoroutineCallAdapterFactory 를 사용했을 때 API return type
     */
    @GET("test/")
    fun coroutineCallAdapterFactory(): Deferred<Response<String>>

    /**
     * CoroutineCallAdapterFactory 를 사용하지 않고 suspend 를 사용했을 때 API return type
     */
    @GET("test/")
    suspend fun suspendRequest(): Response<String>
}