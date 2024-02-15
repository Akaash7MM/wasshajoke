package com.wassha.androidcodechallenge.data.remote

import com.wassha.androidcodechallenge.domain.models.Joke
import retrofit2.http.GET

interface JokeApi {
    @GET("joke/Programming?type=single")
    suspend fun getJoke(): Joke
}
