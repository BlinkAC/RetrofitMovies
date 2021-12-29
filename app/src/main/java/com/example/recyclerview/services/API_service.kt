package com.example.recyclerview.services

import com.example.recyclerview.utils.MoviesResponse
import com.example.recyclerview.utils.RequestToken
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface API_service {

    @GET("3/search/movie?api_key=b846df5ed3d8fc810355515f7af6df6b&language=en-US")
    suspend fun getMovies2(@Query("query") queryString: String,
                           @Query("page") queryPage: Int): Response<MoviesResponse>

    @GET("3/authentication/token/new?api_key=b846df5ed3d8fc810355515f7af6df6b")
     fun getTokenRequest(): Response<RequestToken>

    companion object{
        fun getApi() = Retrofit
            .Builder()
            .baseUrl("https://api.themoviedb.org/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(API_service::class.java)
    }
}