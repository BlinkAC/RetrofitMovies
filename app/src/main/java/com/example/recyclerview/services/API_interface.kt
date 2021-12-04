package com.example.recyclerview.services

import com.example.recyclerview.utils.MovieData
import com.example.recyclerview.utils.MoviesResponse
import com.example.recyclerview.utils.ProvidersResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface API_interface {
    @GET("3/movie/popular?api_key=b846df5ed3d8fc810355515f7af6df6b&page=1")
     fun getPopularMovies(): Call<MoviesResponse>

     @GET("3/search/movie?api_key=b846df5ed3d8fc810355515f7af6df6b&language=en-US&page=1")
     fun searchMovies(@Query("query") queryString: String): Call<MoviesResponse>

     @GET("/3/movie/{id}/watch/providers?api_key=b846df5ed3d8fc810355515f7af6df6b")
     fun getWatchProviders(@Path("id")  id: String): Call<ProvidersResponse>
}