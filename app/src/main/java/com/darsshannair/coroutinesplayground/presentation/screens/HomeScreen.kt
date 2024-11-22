package com.darsshannair.coroutinesplayground.presentation.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun HomeScreen(
    onNavigateToTransactionListScreen: () -> Unit,
    onNavigateToMemoryLeakResolverScreen: () -> Unit,
    onNavigateToManualJobHandlerScreen: () -> Unit,
    onNavigateToScopeManagementScreen: () -> Unit,
    onNavigateToDashboardScreen: () -> Unit,
    onNavigateToDashboardScreenV2: () -> Unit,
    onNavigateToLoanEligibilityFlow: () -> Unit,
    onNavigateToEKYCScreen: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Button(
            onClick = onNavigateToTransactionListScreen,
            modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
        ) {
            Text("Go to Transaction List")
        }

        Button(
            onClick = onNavigateToMemoryLeakResolverScreen,
            modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
        ) {
            Text("Go to Memory Leak Resolver Screen")
        }

        Button(
            onClick = onNavigateToManualJobHandlerScreen,
            modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
        ) {
            Text("Go to Manual Job Handler Screen")
        }

        Button(
            onClick = onNavigateToScopeManagementScreen,
            modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
        ) {
            Text("Go to Scope Management Screen")
        }

        Button(
            onClick = onNavigateToDashboardScreen,
            modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
        ) {
            Text("Go to Dashboard Screen")
        }

        Button(
            onClick = onNavigateToDashboardScreenV2,
            modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
        ) {
            Text("Go to Dashboard Screen V2")
        }

        Button(
            onClick = onNavigateToLoanEligibilityFlow,
            modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
        ) {
            Text("Start Loan Eligibility Flow")
        }

        Button(
            onClick = onNavigateToEKYCScreen,
            modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
        ) {
            Text("Go to EKYC Screen")
        }
    }
}


