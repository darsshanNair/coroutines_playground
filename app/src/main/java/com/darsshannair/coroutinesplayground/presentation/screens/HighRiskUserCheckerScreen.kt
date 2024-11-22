package com.darsshannair.coroutinesplayground.presentation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.darsshannair.coroutinesplayground.presentation.intents.LoanEligibilityIntent
import com.darsshannair.coroutinesplayground.presentation.intents.Screen
import com.darsshannair.coroutinesplayground.presentation.viewmodels.LoanEligibilityViewModel

@Composable
fun HighRiskUserCheckerScreen(
    navController: NavController,
    viewModel: LoanEligibilityViewModel
) {
    val viewState by viewModel.viewState.collectAsState()
    var isPoliticallyExposed by remember { mutableStateOf(false) }
    var isCelebrity by remember { mutableStateOf(false) }
    var isIncomeGamblingRelated by remember { mutableStateOf(false) }

    LaunchedEffect(viewState.screen) {
        if (viewState.screen != Screen.HighRiskChecker) {
            navController.navigate(viewState.screen.name)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.Start
    ) {
        Text("Are you politically exposed?")
        Row {
            RadioButton(
                selected = isPoliticallyExposed,
                onClick = { isPoliticallyExposed = true }
            )
            Text("Yes")
            Spacer(modifier = Modifier.width(16.dp))
            RadioButton(
                selected = !isPoliticallyExposed,
                onClick = { isPoliticallyExposed = false }
            )
            Text("No")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text("Are you a celebrity?")
        Row {
            RadioButton(
                selected = isCelebrity,
                onClick = { isCelebrity = true }
            )
            Text("Yes")
            Spacer(modifier = Modifier.width(16.dp))
            RadioButton(
                selected = !isCelebrity,
                onClick = { isCelebrity = false }
            )
            Text("No")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text("Is your income gambling-related?")
        Row {
            RadioButton(
                selected = isIncomeGamblingRelated,
                onClick = { isIncomeGamblingRelated = true }
            )
            Text("Yes")
            Spacer(modifier = Modifier.width(16.dp))
            RadioButton(
                selected = !isIncomeGamblingRelated,
                onClick = { isIncomeGamblingRelated = false }
            )
            Text("No")
        }

        Spacer(modifier = Modifier.height(32.dp))
        Button(
            onClick = {
                viewModel.handleIntent(
                LoanEligibilityIntent.CheckHighRiskInfoIntent(
                    isPoliticallyExposed = isPoliticallyExposed,
                    isCelebrity = isCelebrity,
                    isIncomeGamblingRelated = isIncomeGamblingRelated
                )
            ) }

        ) {
            Text("Continue")
        }
    }
}
