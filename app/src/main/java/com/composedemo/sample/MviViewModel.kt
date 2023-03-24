package com.composedemo.sample

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
@HiltViewModel
class MviViewModel @Inject constructor(private val dataRepository: DataRepository): ViewModel() {
    private val _viewState= MutableStateFlow(SearchViewState())
    val viewState=_viewState.asStateFlow()


    private val _navigateToResults = MutableSharedFlow<OneShotEvent>()
    val navigateToResults = _navigateToResults.asSharedFlow()



    fun onAction(uiAction:UiAction){
        when(uiAction){
            is UiAction.SearchInput->{
                viewModelScope.launch {
                    _viewState.value=_viewState.value.copy(isLoading = true)
                    val result= withContext(Dispatchers.IO){
                        dataRepository.getArticlesList(uiAction.input)

                    }
                        _viewState.value=_viewState.value.copy(result = result.data.datas,key = uiAction.input)
                        _navigateToResults.emit(OneShotEvent.NavigateToResults)
                        _viewState.value = _viewState.value.copy(isLoading = false)
                }

            }
        }
    }
}



data class SearchViewState(
    val isLoading: Boolean = false,
    val result: List<ArticleBean> = emptyList(),
    val key: String = "",
)

sealed class OneShotEvent {
    object NavigateToResults : OneShotEvent()
}
sealed class UiAction{
    class SearchInput(val input:String):UiAction()
}