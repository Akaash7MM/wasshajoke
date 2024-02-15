package com.wassha.androidcodechallenge.data

import android.util.Log
import com.wassha.androidcodechallenge.data.local.dao.JokeDao
import com.wassha.androidcodechallenge.data.local.entities.toJoke
import com.wassha.androidcodechallenge.data.remote.JokeApi
import com.wassha.androidcodechallenge.domain.JokeRepository
import com.wassha.androidcodechallenge.domain.models.Joke
import com.wassha.androidcodechallenge.domain.models.JokeSource
import com.wassha.androidcodechallenge.domain.models.toJokeEntity
import com.wassha.androidcodechallenge.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import safeResult

class JokeRepositoryImpl(
    private val jokeApi: JokeApi,
    private val jokeDao: JokeDao,
) : JokeRepository {
    companion object {
        const val TAG = "JokeRepository"
    }

    override suspend fun fetchJoke(): JokeSource {
        return withContext(Dispatchers.IO) {
            val result =
                safeResult {
                    jokeApi.getJoke()
                }
            when (result) {
                is Resource.Success -> {
                    try {
                        jokeDao.clearTable()
                        jokeDao.addJoke(joke = result.data.toJokeEntity())
                    } catch (e: Exception) {
                        Log.e(TAG, "Database Error")
                    }
                    JokeSource(result.data, false)
                }

                is Resource.Failure -> {
                    val joke = jokeDao.getJoke()
                    joke?.let {
                        return@let JokeSource(joke.toJoke(), true)
                    } ?: JokeSource(Joke(-1, ""), true)
                }
            }
        }
    }
}
