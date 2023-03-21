package com.composedemo.mvi

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Preview(showBackground = true)
@Composable
fun CounterScreen(viewModel: CounterViewModel= androidx.lifecycle.viewmodel.compose.viewModel()){
    val state by viewModel.counterState.collectAsState()


    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Total Click: ${state.clickNum}")
        Text(text = "Count: ${state.count}")
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { viewModel.handleIntent(CounterIntent.Increment) }) {
            Text(text = "Increment")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { viewModel.handleIntent(CounterIntent.Decrement) }) {
            Text(text = "Decrement")
        }
    }
}