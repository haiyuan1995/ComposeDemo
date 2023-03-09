package com.composedemo.ui.wellness

import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel

//
// implementation "androidx.lifecycle:lifecycle-viewmodel-compose:{latest_version}"
// 通过调用 viewModel() 函数，从任何可组合项访问此 ViewModel。
class WellnessViewModel : ViewModel() {
    private val _tasks = getWellnessTasks().toMutableStateList()
    val tasks: List<WellnessTask>
        get() = _tasks

    fun removeTask(task: WellnessTask) {
        _tasks.remove(task)
    }

    fun changeTaskChecked(item:WellnessTask,checked:Boolean){
        tasks.find { it.id==item.id }?.let {data->
            data.checked=checked
        }
    }
}

private fun getWellnessTasks() = List(30) { i -> WellnessTask(i, "Task # $i") }