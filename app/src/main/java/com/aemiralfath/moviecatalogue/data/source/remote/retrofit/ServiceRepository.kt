package com.aemiralfath.moviecatalogue.data.source.remote.retrofit

import com.aemiralfath.moviecatalogue.data.source.remote.response.DetailMovieResponse
import com.aemiralfath.moviecatalogue.data.source.remote.response.DetailTvResponse
import com.aemiralfath.moviecatalogue.data.source.remote.response.MovieResponse
import com.aemiralfath.moviecatalogue.data.source.remote.response.TvResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ServiceRepository {

    @GET("/3/discover/movie")
    fun getListMovie(
        @Query("api_key") key: String
    ): Call<MovieResponse>

    @GET("/3/discover/tv")
    fun getListTvShow(
        @Query("api_key") key: String
    ): Call<TvResponse>

    @GET("/3/movie/{id}")
    fun getMovie(
        @Path("id") id: Int,
        @Query("api_key") key: String
    ): Call<DetailMovieResponse>

    @GET("/3/tv/{id}")
    fun getTv(
        @Path("id") id: Int,
        @Query("api_key") key: String
    ): Call<DetailTvResponse>

    @GET("/3/search/movie")
    fun searchMovie(
        @Query("api_key") key: String,
        @Query("query") query: String
    ): Call<MovieResponse>

    @GET("/3/search/tv")
    fun searchTv(
        @Query("api_key") key: String,
        @Query("query") query: String
    ): Call<TvResponse>
}