package com.dmendano.megaverse.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dmendano.domain.model.GoalDTO
import com.dmendano.domain.model.MegaverseObjectDTO
import com.dmendano.domain.model.MegaverseOptions
import com.dmendano.domain.usecase.CreateComethUseCase
import com.dmendano.domain.usecase.CreatePolyanetUseCase
import com.dmendano.domain.usecase.CreateSoloonUseCase
import com.dmendano.domain.usecase.DeleteMegaverseObjectUseCase
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
    private val createComethUseCase: CreateComethUseCase,
    private val createSoloonUseCase: CreateSoloonUseCase,
    private val deleteMegaverseObjectUseCase: DeleteMegaverseObjectUseCase,
) : ViewModel() {

    private val _showLoader = MutableStateFlow(false)
    val showLoader = _showLoader.asStateFlow()

    private val batchSize = 3

    fun executeCreateMap() {
        viewModelScope.launch {
            getGoalAndExecute { processCreateMap(it.goal) }
        }
    }

    fun executeClear() {
        viewModelScope.launch {
            showLoader()
            deleteMegaverseObjectUseCase(1, 1, MegaverseOptions.POLYANET)
            hideLoader()
        }
    }

    private suspend fun getGoalAndExecute(onCompleted: (GoalDTO) -> Unit) {
        showLoader()
        getGoalUseCase()
            .onSuccess { onCompleted(it) }
            .onFailure {
                Log.e("*****", it.message ?: "Unknown error")
                hideLoader()
            }
    }

    private fun processCreateMap(magaverseMap: List<List<MegaverseObjectDTO>>?) {
        viewModelScope.launch {
            magaverseMap
                ?.flatten()
                ?.filterNot { it is MegaverseObjectDTO.Space }
                ?.chunked(batchSize)
                ?.forEach { batch ->
                    batch.map { megaverseObject ->
                        async {
                            createMegaverseObject(megaverseObject)
                            delay(300)
                        }
                    }.awaitAll()
                    delay(3000)
                }
            hideLoader()
        }
    }

    private suspend fun createMegaverseObject(megaverseObject: MegaverseObjectDTO) {
        when (megaverseObject) {
            is MegaverseObjectDTO.Cometh -> createComethUseCase(megaverseObject)
            is MegaverseObjectDTO.Soloon -> createSoloonUseCase(megaverseObject)
            is MegaverseObjectDTO.Polyanet -> createPolyanetUseCase(megaverseObject)
            else -> Unit // Do nothing
        }
    }

    private fun showLoader() {
        _showLoader.value = true
    }

    private fun hideLoader() {
        _showLoader.value = false
    }
}
