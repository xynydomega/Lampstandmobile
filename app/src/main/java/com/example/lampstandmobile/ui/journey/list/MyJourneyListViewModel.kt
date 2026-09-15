package com.example.lampstandmobile.ui.journey.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MyJourneyListViewModel(
    private val dataFlow: MyJourneyListDataFlow = MyJourneyListDataFlow()
) : ViewModel() {

    private val _uiState = MutableStateFlow(MyJourneyListState(isLoading = true))
    val uiState: StateFlow<MyJourneyListState> = _uiState.asStateFlow()

    init {
        refreshData()
    }

    fun onAction(action: MyJourneyListAction) {
        when (action) {
            MyJourneyListAction.Refresh -> refreshData()
            is MyJourneyListAction.PathClicked -> { /* navigation handled by screen */ }
            MyJourneyListAction.GoToPaths -> { /* navigation handled by screen */ }
        }
    }

    private fun refreshData() {
        _uiState.update { it.copy(isLoading = true, errorMessage = null) }
        viewModelScope.launch {
            dataFlow.getMyJourneyPaths()
                .onSuccess { paths ->
                    _uiState.update {
                        it.copy(isLoading = false, paths = paths, errorMessage = null)
                    }
                }
                .onFailure { error ->
                    _uiState.update {
                        it.copy(isLoading = false, errorMessage = error.message ?: "Failed to load journey")
                    }
                }
        }
    }
}
