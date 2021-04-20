package com.aemiralfath.moviecatalogue.data.network

import com.aemiralfath.moviecatalogue.data.entity.MovieEntity
import com.aemiralfath.moviecatalogue.data.entity.TvEntity
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ServiceRepository {

    @GET("/3/discover/movie")
    fun getMovie(
        @Query("api_key") key: String
    ): Call<MovieEntity>

    @GET("/3/discover/tv")
    fun geTvShow(
        @Query("api_key") key: String
    ): Call<TvEntity>
}