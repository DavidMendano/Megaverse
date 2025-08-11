package com.dmendano.megaverse.ui

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
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MainViewModel(
    private val getGoalUseCase: GetGoalUseCase,
    private val createPolyanetUseCase: CreatePolyanetUseCase,
    private val createComethUseCase: CreateComethUseCase,
    private val createSoloonUseCase: CreateSoloonUseCase,
    private val deleteMegaverseObjectUseCase: DeleteMegaverseObjectUseCase,
) : ViewModel() {

    private val _screenState = MutableStateFlow<MainScreenState>(MainScreenState())
    val screenState = _screenState.asStateFlow()

    private val batchSize = 3

    fun executeCreateMap() {
        viewModelScope.launch {
            getGoalAndExecute { processCreateMap(it.goal) }
        }
    }

    fun executeClear() {
        viewModelScope.launch {
            if (_screenState.value.row < 0 || _screenState.value.column < 0) {
                showErrorMessage(errorMessage = "Row and column must be greater or equal than 0")
                return@launch
            }

            showLoader()
            deleteMegaverseObjectUseCase(
                row = _screenState.value.row,
                column = _screenState.value.column,
                megaverseOption = _screenState.value.selectedOption
            )
            hideLoader()
        }
    }

    fun onRowChanged(row: String) {
        _screenState.update {
            it.copy(row = row.toIntOrNull() ?: 0)
        }
    }

    fun onColumnChanged(column: String) {
        _screenState.update {
            it.copy(column = column.toIntOrNull() ?: 0)
        }
    }

    fun onObjectTypeSelected(type: MegaverseOptions) {
        _screenState.update {
            it.copy(selectedOption = type)
        }
    }

    private suspend fun getGoalAndExecute(onCompleted: (GoalDTO) -> Unit) {
        showLoader()
        getGoalUseCase()
            .onSuccess { onCompleted(it) }
            .onFailure {
                showErrorMessage(it.message ?: "Error")
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
                                .onFailure { showErrorMessage(errorMessage = it.message) }
                            delay(300)
                        }
                    }.awaitAll()
                    delay(3000)
                }
            hideLoader()
        }
    }

    private suspend fun createMegaverseObject(megaverseObject: MegaverseObjectDTO): Result<Unit> =
        when (megaverseObject) {
            is MegaverseObjectDTO.Cometh -> createComethUseCase(megaverseObject)
            is MegaverseObjectDTO.Soloon -> createSoloonUseCase(megaverseObject)
            is MegaverseObjectDTO.Polyanet -> createPolyanetUseCase(megaverseObject)
            else -> Result.failure(IllegalArgumentException("Invalid megaverse object"))
        }

    private fun showLoader() {
        _screenState.update { it.copy(showLoader = true, errorMessage = null) }
    }

    private fun hideLoader() {
        _screenState.update { it.copy(showLoader = false) }
    }

    private fun showErrorMessage(errorMessage: String?) {
        _screenState.update { it.copy(errorMessage = errorMessage) }
    }

    data class MainScreenState(
        val showLoader: Boolean = false,
        val row: Int = 0,
        val column: Int = 0,
        val selectedOption: MegaverseOptions = MegaverseOptions.POLYANET,
        val errorMessage: String? = null
    )
}
