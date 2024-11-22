package com.darsshannair.coroutinesplayground.presentation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.darsshannair.coroutinesplayground.presentation.intents.DashboardIntent
import com.darsshannair.coroutinesplayground.presentation.viewmodels.DashboardViewModelV2

@Composable
fun DashboardScreenV2(viewModel: DashboardViewModelV2 = viewModel()) {
    val viewState by viewModel.state.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Display a loading spinner while API calls are being made
        if (viewState.isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
        }

        // Balance Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 4.dp
            )
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text("Balance")
                if (viewState.balanceErrorMessage != null) {
                    Text(
                        "Error: ${viewState.balanceErrorMessage}",
                        color = Color.Red
                    )
                } else {
                    Text("Balance: \$${viewState.balance?.amount ?: "N/A"}")
                }
            }
        }

        // Credit Score Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 4.dp
            )
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text("Credit Score")
                if (viewState.creditScoreErrorMessage != null) {
                    Text(
                        "Error: ${viewState.creditScoreErrorMessage}",
                        color = Color.Red
                    )
                } else {
                    Text("Credit Score: ${viewState.creditScore?.score ?: "N/A"}")
                }
            }
        }

        // Portfolio Value Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 4.dp
            )
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text("Portfolio Value")
                if (viewState.portfolioValueErrorMessage != null) {
                    Text(
                        "Error: ${viewState.portfolioValueErrorMessage}",
                        color = Color.Red
                    )
                } else {
                    Text("Portfolio Value: \$${viewState.portfolioValue?.value ?: "N/A"}")
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Initiate Button
        Button(
            onClick = { viewModel.handleIntent(DashboardIntent.LoadDashboardInfoIntent) },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text("Initiate")
        }
    }
}
