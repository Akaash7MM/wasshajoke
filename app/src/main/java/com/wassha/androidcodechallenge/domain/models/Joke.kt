package com.wassha.androidcodechallenge.domain.models

import com.wassha.androidcodechallenge.data.local.entities.JokeEntity

data class Joke(
    val id: Int,
    val joke: String,
)

fun Joke.toJokeEntity(): JokeEntity {
    return JokeEntity(id, joke)
}
