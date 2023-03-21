package com.composedemo.mvi

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class CounterViewModel: ViewModel() {
    private val _counterState = MutableStateFlow(CounterState())
    val counterState = _counterState.asStateFlow()

    fun handleIntent(intent: CounterIntent) {
        when (intent) {
             CounterIntent.Increment -> {
                 //用copy取代新建对象，节省内存开销
//                _counterState.value = CounterState(counterState.value.count + 1,counterState.value.clickNum+1)
                 _counterState.value=_counterState.value.copy(count = counterState.value.count+1, clickNum = counterState.value.clickNum+1)
            }
             CounterIntent.Decrement -> {
                _counterState.value =_counterState.value.copy(count = counterState.value.count-1, clickNum = counterState.value.clickNum+1)
            }
        }
    }
}