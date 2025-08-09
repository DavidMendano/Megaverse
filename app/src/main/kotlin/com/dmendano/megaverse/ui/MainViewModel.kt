package com.dmendano.megaverse.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dmendano.domain.model.GoalDTO
import com.dmendano.domain.model.MegaverseObjectDTO
import com.dmendano.domain.usecase.CreatePolyanetUseCase
import com.dmendano.domain.usecase.GetGoalUseCase
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel(
    private val getGoalUseCase: GetGoalUseCase,
    private val createPolyanetUseCase: CreatePolyanetUseCase,
) : ViewModel() {

    private val _showLoader = MutableStateFlow(false)
    val showLoader = _showLoader.asStateFlow()

    private val batchSize = 5

    fun executePhase1() {
        showLoader()
        viewModelScope.launch {
            getGoalUseCase()
                .onSuccess(::processGoal)
                .onFailure { hideLoader() }
        }
    }

    private fun processGoal(goal: GoalDTO) {
        viewModelScope.launch {
            val polyanets = goal.goal?.flatten()?.filterIsInstance<MegaverseObjectDTO.Polyanet>()
            polyanets?.chunked(batchSize)?.forEachIndexed { bidx, batch ->
                batch.map { polyanet ->
                    async {
                        createPolyanetUseCase(polyanet)
                    }
                }.awaitAll()
                delay(2000)
            }
            hideLoader()
        }
    }

    private fun showLoader() {
        _showLoader.value = true
    }

    private fun hideLoader() {
        _showLoader.value = false
    }
}
