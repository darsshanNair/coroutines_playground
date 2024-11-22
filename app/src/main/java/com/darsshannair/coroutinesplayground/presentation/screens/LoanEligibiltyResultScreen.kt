package com.darsshannair.coroutinesplayground.presentation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.darsshannair.coroutinesplayground.presentation.intents.LoanEligibilityIntent
import com.darsshannair.coroutinesplayground.presentation.viewmodels.LoanEligibilityViewModel

@Composable
fun LoanEligibilityResultScreen(
    navController: NavController,
    viewModel: LoanEligibilityViewModel
) {
    val viewState by viewModel.viewState.collectAsState()

    LaunchedEffect(
        key1 = viewState.isRiskCheckLoading,
        key2 = viewState.isCreditScoreCheckLoading
    ) {
        if (!viewState.isRiskCheckLoading && !viewState.isCreditScoreCheckLoading) {
            viewModel.handleIntent(LoanEligibilityIntent.ShowEligibleAmountIntent)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if(viewState.isRiskCheckLoading || viewState.isCreditScoreCheckLoading) {
            CircularProgressIndicator()
        } else if (viewState.errorMessage != null) {
            Text(
                text = viewState.errorMessage!!,
                color = Color.Red,
                fontSize = 20.sp,
                textAlign = TextAlign.Center
            )
        } else if (viewState.loanAmount > 0) {
            Text(
                text = "Congratulations! You are eligible for a loan amount of RM ${viewState.loanAmount}",
                fontSize = 20.sp,
                textAlign = TextAlign.Center
            )
        }
    }
}
