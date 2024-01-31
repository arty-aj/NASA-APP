package com.example.nasa_api.data

import com.example.nasa_api.models.APOD
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