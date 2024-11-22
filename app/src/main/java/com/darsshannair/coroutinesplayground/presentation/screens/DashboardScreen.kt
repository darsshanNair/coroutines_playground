package com.darsshannair.coroutinesplayground.presentation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.darsshannair.coroutinesplayground.presentation.intents.DashboardIntent
import com.darsshannair.coroutinesplayground.presentation.viewmodels.DashboardViewModel

@Composable
fun DashboardScreen(viewModel: DashboardViewModel = viewModel()) {
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
            viewState.balance?.let {
                Text("Balance: \$${it.amount}")
            }
            viewState.creditScore?.let {
                Text("Credit Score: ${it.score}")
            }
            viewState.portfolioValue?.let {
                Text("Portfolio Value: \$${it.value}")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { viewModel.handleIntent(DashboardIntent.LoadDashboardInfoIntent) }) {
            Text("Initiate")
        }
    }
}
