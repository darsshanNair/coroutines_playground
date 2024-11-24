package com.darsshannair.coroutinesplayground.presentation.states

data class DownloadJobHandlerState(
    val progress: Int = 0,
    val isDownloading: Boolean = false,
    val downloadStatus: String = "Idle"
)
