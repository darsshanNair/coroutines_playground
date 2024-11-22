package com.darsshannair.coroutinesplayground.presentation.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.darsshannair.coroutinesplayground.presentation.viewmodels.ManualJobHandlerViewModel

@Composable
fun ManualJobHandlerScreen(viewModel: ManualJobHandlerViewModel = viewModel()) {
    val progress = viewModel.downloadProgress.value
    val isDownloading = viewModel.isDownloading.value

    Column(
        modifier = Modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Download Progress: $progress%")

        // Show a button to start the download if not already downloading
        if (isDownloading) {
            Button(onClick = { viewModel.cancelDownload() }) {
                Text("Cancel Download")
            }
        } else {
            Button(onClick = { viewModel.startDownload() }) {
                Text("Start Download")
            }
        }

        // Display download status
        if (isDownloading) {
            Text("Downloading...")
        } else {
            Text("Download Complete")
        }
    }
}
