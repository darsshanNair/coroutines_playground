package com.darsshannair.coroutinesplayground.presentation.intents

sealed class DownloadJobHandlerIntent {
    object StartDownload : DownloadJobHandlerIntent()
    object CancelDownload : DownloadJobHandlerIntent()
}