package com.composedemo.ui.wellness

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun WellnessScreen(modifier: Modifier = Modifier, viewModel: WellnessViewModel = androidx.lifecycle.viewmodel.compose.viewModel()) {
    Column(modifier = modifier) {
        StatefulCounter()

        WellnessTasksList(modifier = Modifier.weight(1f), viewModel.tasks,
            onCheckedTask = { task, checked ->
                viewModel.changeTaskChecked(task, checked)
            },
            onCloseTask = { task ->
            viewModel.removeTask(task)
        })
    }
}


@Composable
fun WellnessTasksList(
    modifier: Modifier,
    list: List<WellnessTask>,
    onCheckedTask: (WellnessTask, Boolean) -> Unit,
    onCloseTask: (WellnessTask) -> Unit = {}
) {
    //在可变列表中，当数据集发生变化时,会丢失状态，要加key
    LazyColumn(modifier = modifier) {
        items(items = list,
            key = { task ->
                task.id
            }) { task ->
            WellnessTaskItem(taskName = task.label,
                checked = task.checked,
                onCheckedChange = { checked ->
                    onCheckedTask(task, checked)
                },
                onClose = { onCloseTask(task) })
        }
    }
}

class WellnessTask(val id: Int, val label: String,initialChecked:Boolean=false) {
    //从data class 改为class 是为了可以使用by 委托
    var checked by mutableStateOf(initialChecked)
}