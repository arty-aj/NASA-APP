package com.example.nasa_api.domain.repository

import com.example.nasa_api.data.APOD
import com.example.nasa_api.utils.Util
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {
    @GET("/planetary/apod${Util.key}")
    suspend fun getApod() : Response<APOD>

    @GET("/planetary/apod${Util.key}")
    suspend fun getApodDate(@Query("date") date: String) : Response<APOD>
}