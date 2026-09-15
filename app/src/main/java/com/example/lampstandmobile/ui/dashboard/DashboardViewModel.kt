package com.example.lampstandmobile.ui.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lampstandmobile.data.convex.PathsDataFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DashboardViewModel(
    private val pathsDataFlow: PathsDataFlow = PathsDataFlow()
) : ViewModel() {
    private val _uiState = MutableStateFlow(DashboardState())
    val uiState: StateFlow<DashboardState> = _uiState.asStateFlow()

    init {
        refreshData()
    }

    fun onAction(action: DashboardAction) {
        when (action) {
            DashboardAction.Refresh -> refreshData()
            is DashboardAction.ItemClicked -> handleItemClick(action.id)
        }
    }

    private fun refreshData() {
        _uiState.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            pathsDataFlow.getHomeState()
                .onSuccess { state ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            homeState = state,
                            errorMessage = null
                        )
                    }
                }
                .onFailure { error ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = error.message ?: "Failed to load dashboard"
                        )
                    }
                }
        }
    }

    private fun handleItemClick(id: String) {
        // TODO: Handle item click
    }
}
