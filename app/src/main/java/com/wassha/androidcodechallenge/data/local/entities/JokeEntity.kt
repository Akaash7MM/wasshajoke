package com.wassha.androidcodechallenge.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.wassha.androidcodechallenge.domain.models.Joke

@Entity
data class JokeEntity(
    @PrimaryKey
    val id: Int,
    val joke: String,
)

fun JokeEntity.toJoke(): Joke {
    return Joke(id, joke)
}
