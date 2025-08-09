package com.dmendano.megaverse.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dmendano.domain.usecase.GetGoalUseCase
import kotlinx.coroutines.launch

class MainViewModel(
    private val getGoalUseCase: GetGoalUseCase
) : ViewModel() {

    fun executePhase1() {
        viewModelScope.launch {
            getGoalUseCase()
        }
    }
}
