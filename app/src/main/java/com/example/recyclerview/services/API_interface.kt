package com.example.recyclerview.services

import com.example.recyclerview.utils.MovieData
import com.example.recyclerview.utils.MoviesResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET

interface API_interface {
    @GET("3/movie/popular?api_key=b846df5ed3d8fc810355515f7af6df6b&page=1")
     fun getPopularMovies(): Call<MoviesResponse>

     @GET("3/movie/{movie_id}?api_key=b846df5ed3d8fc810355515f7af6df6b&language=en-US")
     fun movieDetails(): Call<MovieData>
}