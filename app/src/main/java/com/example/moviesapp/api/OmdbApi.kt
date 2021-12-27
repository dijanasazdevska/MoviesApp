package com.example.moviesapp.api

import com.example.moviesapp.models.Movie
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface OmdbApi {
    @GET("?apikey=e800ff32")
    fun searchMoviesByTitle(@Query("t") t: String): Call<Movie>

}