package com.wassha.androidcodechallenge.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.wassha.androidcodechallenge.ui.joke_screen_components.Joke
import com.wassha.androidcodechallenge.ui.joke_screen_components.NoJoke

@Composable
fun JokeScreen(viewModel: JokeViewModel = hiltViewModel()) {
    val jokeScreenState = viewModel.joke.collectAsState().value
    Column(
        modifier =
            Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement =
            Arrangement.spacedBy(
                space = 16.dp,
                alignment = Alignment.CenterVertically,
            ),
    ) {
        when (jokeScreenState) {
            is JokeScreenState.Empty -> {
                NoJoke(onButtonClick = { viewModel.getJoke() })
            }
            is JokeScreenState.Loading -> {
                CircularProgressIndicator()
            }
            is JokeScreenState.Success -> {
                Joke(
                    jokeSource = jokeScreenState.data,
                    onButtonClick = {
                        viewModel.getJoke()
                    },
                )
            }
        }
    }
}
