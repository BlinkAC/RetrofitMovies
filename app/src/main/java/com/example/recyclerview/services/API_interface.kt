package com.example.recyclerview.services

import com.example.recyclerview.utils.MoviesResponse
import retrofit2.Call
import retrofit2.http.GET

interface API_interface {
    @GET("3/movie/popular?api_key=b846df5ed3d8fc810355515f7af6df6b&page=1")
    fun getPopularMovies(): Call<MoviesResponse>
}