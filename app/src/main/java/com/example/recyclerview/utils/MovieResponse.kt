package com.example.recyclerview.utils

data class MoviesResponse(var results: List<MovieData>) {

}

data class MovieData(
    val id: Int,
    val overview: String,
    val poster_path: String,
    val release_date: String,
    val title: String,
    val vote_average: Double,
    val vote_count: Int
)
