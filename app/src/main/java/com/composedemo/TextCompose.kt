package com.composedemo

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.random.Random

@Preview
@Composable
fun TextShow(viewModel: TextViewModel = hiltViewModel()) {
    val text = viewModel.text.collectAsState()

    LaunchedEffect(key1 = Unit, block = {
        viewModel.startFlow()
    })
    LazyColumn(content = {
        item {
            Text(text = text.value)
        }
    })

}

@HiltViewModel
class TextViewModel @Inject constructor() : ViewModel() {
    val text = MutableStateFlow("hello")
    fun startFlow() {
        viewModelScope.launch {
            flow {
                repeat(2000) {
                    delay(Random(System.currentTimeMillis()).nextLong(200-50)+50)
                    emit(it.toString())
                }
            }.collect {
                text.value = text.value + it
            }
        }

    }
}