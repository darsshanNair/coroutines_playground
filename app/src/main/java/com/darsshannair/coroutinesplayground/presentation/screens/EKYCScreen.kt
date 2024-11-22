package com.darsshannair.coroutinesplayground.presentation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.darsshannair.coroutinesplayground.presentation.intents.EKYCIntent
import com.darsshannair.coroutinesplayground.presentation.viewmodels.EKYCViewModel

@Composable
fun EKYCScreen(viewModel: EKYCViewModel = viewModel()) {
    val viewState by viewModel.state.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        if (viewState.isLoading) {
            CircularProgressIndicator()
        } else if (viewState.errorMessage != null) {
            Text("Error: ${viewState.errorMessage}")
        } else {
            LazyColumn {
                items(viewState.syncedUsers) { user ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        elevation = CardDefaults.cardElevation(
                            defaultElevation = 4.dp
                        )
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text("ID: ${user.id}")
                            Text("Front Photo: ${user.frontPhotoPath}")
                            Text("Back Photo: ${user.backPhotoPath}")
                            Text("Selfie: ${user.selfiePath}")
                            Text("eKYC Done: ${user.isEKYCDone}")
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(onClick = { viewModel.handleIntent(EKYCIntent.LoadLocalData) }) {
                Text("Load Local Data")
            }
            Button(onClick = { viewModel.handleIntent(EKYCIntent.SyncData) }) {
                Text("Sync Data")
            }
        }
    }
}
