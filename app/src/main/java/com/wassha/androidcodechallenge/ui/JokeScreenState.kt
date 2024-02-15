package com.wassha.androidcodechallenge.ui

sealed class JokeScreenState<out T> {
    object Empty : JokeScreenState<Nothing>()

    object Loading : JokeScreenState<Nothing>()

    class Success<T>(val data: T) : JokeScreenState<T>()
}
