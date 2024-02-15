package com.wassha.androidcodechallenge.data.remote

import com.wassha.androidcodechallenge.domain.models.Joke
import retrofit2.http.GET
import retrofit2.http.Query

interface JokeApi {
    @GET("joke/Programming")
    suspend fun getJoke(
        @Query("type") type: String = "single",
    ): Joke
}
