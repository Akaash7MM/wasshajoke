package com.wassha.androidcodechallenge.ui.joke_screen_components

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun NoJoke(onButtonClick: () -> Unit) {
    Text("No Jokes Available!")
    Button(onClick = onButtonClick) {
        Text(text = "Fetch Joke")
    }
}
