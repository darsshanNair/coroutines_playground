package com.darsshannair.coroutinesplayground.presentation.viewmodels

import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ManualJobHandlerViewModel : ViewModel() {
    private var downloadJob: Job? = null
    val downloadProgress = mutableIntStateOf(0)
    val isDownloading = mutableStateOf(false) // Add this flag to track download status

    // Function to start the file download (simulated)
    fun startDownload() {
        isDownloading.value = true  // Set downloading to true when download starts

        downloadJob = viewModelScope.launch {
            for (i in 1..100) {
                delay(100)  // Simulate download progress
                downloadProgress.value = i
                if (downloadJob?.isCancelled == true) {
                    break  // Stop downloading if the job is cancelled
                }
            }
            // Automatically cancel the job when download is complete
            if (downloadProgress.value == 100) {
                cancelDownload() // Job is canceled manually here, to ensure cleanup.
            }
        }
    }

    // Function to cancel the download
    fun cancelDownload() {
        downloadJob?.cancel() // Explicit cancellation to clean up any resources
        isDownloading.value = false  // Set downloading to false when download is canceled or complete
    }
}

