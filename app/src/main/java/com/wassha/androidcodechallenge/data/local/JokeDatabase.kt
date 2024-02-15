package com.wassha.androidcodechallenge.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.wassha.androidcodechallenge.data.local.dao.JokeDao
import com.wassha.androidcodechallenge.data.local.entities.JokeEntity

@Database(
    entities = [JokeEntity::class],
    version = 1,
    exportSchema = true,
)
abstract class JokeDatabase : RoomDatabase() {
    abstract fun jokeDao(): JokeDao
}
