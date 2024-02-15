package com.wassha.androidcodechallenge.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.wassha.androidcodechallenge.data.local.entities.JokeEntity
import java.util.concurrent.Flow

@Dao
interface JokeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addJoke(joke:JokeEntity)

    @Query("SELECT * FROM jokeentity")
    suspend fun getJoke():  JokeEntity?
    @Query("DELETE FROM jokeentity")
    suspend fun clearTable()

}