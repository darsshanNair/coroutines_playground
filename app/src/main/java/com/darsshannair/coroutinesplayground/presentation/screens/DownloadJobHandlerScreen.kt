package com.darsshannair.coroutinesplayground.presentation.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.darsshannair.coroutinesplayground.presentation.intents.DownloadJobHandlerIntent
import com.darsshannair.coroutinesplayground.presentation.viewmodels.DownloadJobHandlerViewModel

@Composable
fun ManualJobHandlerScreen(
    viewModel: DownloadJobHandlerViewModel // Pass the ViewModel from MainActivity
) {
    // Collect state from the ViewModel
    val state by viewModel.state.collectAsState()

    Column(
        modifier = Modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Display the download progress
        Text(text = "Download Progress: ${state.progress}%")
        Text(text = "Status: ${state.downloadStatus}")

        // Show appropriate buttons based on the state
        if (state.isDownloading) {
            Button(onClick = { viewModel.handleIntent(DownloadJobHandlerIntent.CancelDownload) }) {
                Text("Cancel Download")
            }
        } else {
            Button(onClick = { viewModel.handleIntent(DownloadJobHandlerIntent.StartDownload) }) {
                Text("Start Download")
            }
        }
    }
}

