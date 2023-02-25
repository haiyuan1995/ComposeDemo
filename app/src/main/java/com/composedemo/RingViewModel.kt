package com.composedemo

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class RingViewModel : ViewModel() {
    private val _showDialog = MutableStateFlow(false)
    val showDialog: StateFlow<Boolean> = _showDialog.asStateFlow()

    fun openDialog(){
        _showDialog.value=true
    }

    fun dismissDialog(){
        _showDialog.value=false
    }

    var dlgBuyData= mutableStateOf(
        RingProvideBean.Data.Record(
            content = "",
            id = "",
            image = "",
            logo = "",
            name = "",
            price1 = "",
            price2 = "",
            seq = "",
            type = 0,
            typeName = "",
            valDesc = "",
            validity = "",
            typeShortName = ""
        ))
    private set

}