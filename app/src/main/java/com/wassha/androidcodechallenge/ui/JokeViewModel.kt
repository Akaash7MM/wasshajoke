package com.wassha.androidcodechallenge.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wassha.androidcodechallenge.domain.JokeRepository
import com.wassha.androidcodechallenge.domain.models.JokeSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class JokeViewModel
    @Inject
    constructor(
        private val repository: JokeRepository,
    ) : ViewModel() {
        val joke: MutableStateFlow<JokeScreenState<JokeSource>> = MutableStateFlow(JokeScreenState.Loading)

        init {
            getJoke()
        }

        fun getJoke() {
            joke.value = JokeScreenState.Loading
            viewModelScope.launch {
                val jokeSource = repository.fetchJoke()
                val jokeString = jokeSource.joke.joke
                if (jokeString.isNotBlank()) {
                    joke.value = JokeScreenState.Success(jokeSource)
                } else {
                    joke.value = JokeScreenState.Empty
                }
            }
        }
    }
