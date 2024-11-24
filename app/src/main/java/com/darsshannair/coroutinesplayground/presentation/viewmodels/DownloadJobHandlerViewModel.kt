package com.darsshannair.coroutinesplayground.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.darsshannair.coroutinesplayground.presentation.intents.DownloadJobHandlerIntent
import com.darsshannair.coroutinesplayground.presentation.states.DownloadJobHandlerState
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DownloadJobHandlerViewModel : ViewModel() {
    private val _state = MutableStateFlow(DownloadJobHandlerState())
    val state: StateFlow<DownloadJobHandlerState> get() = _state
    private var downloadJob: Job? = null

    fun handleIntent(intent: DownloadJobHandlerIntent) {
        when (intent) {
            is DownloadJobHandlerIntent.StartDownload -> startDownload()
            is DownloadJobHandlerIntent.CancelDownload -> cancelDownload()
        }
    }

    private fun startDownload() {
        if (_state.value.isDownloading) return

        _state.value = _state.value.copy(isDownloading = true, downloadStatus = "Downloading...")
        downloadJob = viewModelScope.launch {
            for (i in 1..100) {
                delay(100) // Simulate download progress
                _state.value = _state.value.copy(progress = i)

                if (downloadJob?.isCancelled == true) {
                    _state.value = _state.value.copy(downloadStatus = "Canceled", isDownloading = false)
                    return@launch
                }
            }
            _state.value = _state.value.copy(downloadStatus = "Completed", isDownloading = false)
        }
    }

    private fun cancelDownload() {
        downloadJob?.cancel()
        _state.value = _state.value.copy(isDownloading = false, downloadStatus = "Canceled")
    }
}



