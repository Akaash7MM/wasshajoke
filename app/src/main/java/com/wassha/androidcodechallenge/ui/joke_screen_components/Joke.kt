package com.wassha.androidcodechallenge.ui.joke_screen_components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.wassha.androidcodechallenge.domain.models.JokeSource

@Composable
fun Joke(
    jokeSource: JokeSource,
    onButtonClick: () -> Unit,
) {
    val joke = jokeSource.joke.joke
    val isFromDb = jokeSource.isFromDb
    val lengthMoreThan80 =
        remember {
            mutableStateOf(true)
        }
    lengthMoreThan80.value = joke.length > 80
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
        Text(
            text = "Joke",
            style = MaterialTheme.typography.titleLarge,
        )

        Text(
            text = joke,
            style = MaterialTheme.typography.bodyLarge,
        )
        AnimatedVisibility(lengthMoreThan80.value) {
            Text(
                text = "Length: ${joke.length}",
                style = MaterialTheme.typography.bodySmall,
            )
        }

        Text(
            text = "Words: ${joke.split("\\s+".toRegex())
                .filter { it.isNotEmpty() }
                .count()}",
            style = MaterialTheme.typography.bodySmall,
        )

        Text(
            text = "Data is fetched from ${
                if (isFromDb){
                    "local"
                } else {
                    "remote"
                }}",
            style = MaterialTheme.typography.bodySmall,
            color = Color.Red,
        )
        Button(onClick = onButtonClick) {
            Text(text = "Fetch Joke")
        }
    }
}
