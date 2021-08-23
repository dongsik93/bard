package com.example.bard.data.source.remote

import retrofit2.http.GET

interface RetrofitService {
    @GET("test/")
    fun getTest(): String
}