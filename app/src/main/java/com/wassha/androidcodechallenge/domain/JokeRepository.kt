package com.wassha.androidcodechallenge.domain

import com.wassha.androidcodechallenge.domain.models.JokeSource

interface JokeRepository {
    suspend fun fetchJoke(): JokeSource
}
