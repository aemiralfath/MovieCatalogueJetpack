package com.aemiralfath.moviecatalogue.utils

import android.content.Context
import com.aemiralfath.moviecatalogue.data.local.entity.MovieEntity
import com.aemiralfath.moviecatalogue.data.local.entity.TvEntity
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException

object DataDummy {

    private fun parsingFileToString(context: Context, fileName: String): String? {
        return try {
            val `is` = context.assets.open(fileName)
            val buffer = ByteArray(`is`.available())
            `is`.read(buffer)
            `is`.close()
            String(buffer)
        } catch (ex: IOException) {
            ex.printStackTrace()
            null
        }
    }

    fun loadMovie(context: Context): List<MovieEntity> {
        val list = ArrayList<MovieEntity>()
        try {
            val responseObject = JSONObject(parsingFileToString(context, "MovieDb.json").toString())
            val listArray = responseObject.getJSONArray("movies")
            for (i in 0 until listArray.length()) {
                val movie = listArray.getJSONObject(i)

                list.add(
                    MovieEntity(
                        movie.getString("overview"),
                        movie.getString("original_language"),
                        movie.getString("title"),
                        movie.getString("poster_path"),
                        movie.getString("release_date"),
                        movie.getDouble("popularity"),
                        movie.getDouble("vote_average"),
                        movie.getInt("id"),
                        movie.getBoolean("adult"),
                        movie.getInt("vote_count"),
                    )
                )
            }
        } catch (e: JSONException) {
            e.printStackTrace()
        }

        return list
    }

    fun loadTv(context: Context): List<TvEntity> {
        val list = ArrayList<TvEntity>()
        try {
            val responseObject = JSONObject(parsingFileToString(context, "TvDb.json").toString())
            val listArray = responseObject.getJSONArray("tv")
            for (i in 0 until listArray.length()) {
                val tv = listArray.getJSONObject(i)

                list.add(
                    TvEntity(
                        tv.getString("first_air_date"),
                        tv.getString("overview"),
                        tv.getString("original_language"),
                        tv.getString("poster_path"),
                        tv.getDouble("popularity"),
                        tv.getDouble("vote_average"),
                        tv.getString("name"),
                        tv.getInt("id"),
                        tv.getInt("vote_count"),
                    )
                )
            }
        } catch (e: JSONException) {
            e.printStackTrace()
        }

        return list
    }


}