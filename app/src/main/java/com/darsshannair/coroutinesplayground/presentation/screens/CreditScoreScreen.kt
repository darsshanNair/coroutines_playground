package com.darsshannair.coroutinesplayground.presentation.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.darsshannair.coroutinesplayground.presentation.intents.LoanEligibilityIntent
import com.darsshannair.coroutinesplayground.presentation.intents.Screen
import com.darsshannair.coroutinesplayground.presentation.viewmodels.LoanEligibilityViewModel

@Composable
fun CreditScoreCheckerScreen(
    navController: NavController,
    viewModel: LoanEligibilityViewModel
) {
    val viewState by viewModel.viewState.collectAsState()
    var salary by remember { mutableStateOf("") }
    var commitments by remember { mutableStateOf("") }

    LaunchedEffect(viewState.screen) {
        if (viewState.screen != Screen.CreditScoreChecker) {
            navController.navigate(viewState.screen.name)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            value = salary,
            onValueChange = { salary = it },
            label = { Text("Monthly Salary") },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
        )
        Spacer(modifier = Modifier.height(16.dp))
        TextField(
            value = commitments,
            onValueChange = { commitments = it },
            label = { Text("Total Monthly Commitments") },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
        )
        Spacer(modifier = Modifier.height(32.dp))
        Button(
            onClick = {
                val salaryInt = salary.toIntOrNull() ?: 0
                val commitmentsInt = commitments.toIntOrNull() ?: 0
                viewModel.handleIntent(
                    LoanEligibilityIntent.CheckCreditScoreIntent(
                        salary = salaryInt,
                        commitments = commitmentsInt
                    )
                )
            },
            enabled = salary.isNotEmpty() && commitments.isNotEmpty()
        ) {
            Text("Continue")
        }
    }
}
