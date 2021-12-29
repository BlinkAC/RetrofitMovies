package com.example.recyclerview.services

import com.example.recyclerview.utils.*
import com.google.gson.annotations.JsonAdapter
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface API_interface {

    @GET("3/discover/movie?api_key=b846df5ed3d8fc810355515f7af6df6b&language=en-US&sort_by=popularity.desc&page=1")
     suspend fun seasonMovies(@Query("with_keywords") queryString: String): Response<MoviesResponse>

    @GET("3/movie/upcoming?api_key=b846df5ed3d8fc810355515f7af6df6b&page=1&region=US")
    suspend fun getUpcomingMovies(): Response<MoviesResponse>

    @GET("3/movie/now_playing?api_key=b846df5ed3d8fc810355515f7af6df6b&page=1")
    suspend fun getNowPlaying(): Response<MoviesResponse>

    @GET("3/movie/popular?api_key=b846df5ed3d8fc810355515f7af6df6b&page=1")
     suspend fun getPopularMovies(): Response<MoviesResponse>

     @GET("3/search/movie?api_key=b846df5ed3d8fc810355515f7af6df6b&language=en-US")
     fun searchMovies(@Query("query") queryString: String,
                      @Query("page") queryPage: Int): Response<MoviesResponse>

     @GET("/3/movie/{id}/watch/providers?api_key=b846df5ed3d8fc810355515f7af6df6b")
     fun getWatchProviders(@Path("id")  id: String): Call<ProvidersResponse>

    @GET("3/authentication/token/new?api_key=b846df5ed3d8fc810355515f7af6df6b")
    fun getTokenRequest(): Call<RequestToken>

    @Headers("Content-Type: application/json")
    @POST("3/authentication/session/new?api_key=b846df5ed3d8fc810355515f7af6df6b")
    fun getSessionID(@Body tokenInfo: TokenInfo): Call<SessionResponse>

    @Headers("Content-Type: application/json;charset=utf-8")
    @POST("3/movie/{movie_id}/rating?api_key=b846df5ed3d8fc810355515f7af6df6b")
    fun rateMovie(@Path("movie_id") movie_id: String,
                  @Query("session_id") sessionID: String,
                  @Body rateInfo: RateInfo): Call<RateResponse>

}