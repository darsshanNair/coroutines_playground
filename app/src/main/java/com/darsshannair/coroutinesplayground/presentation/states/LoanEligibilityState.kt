package com.darsshannair.coroutinesplayground.presentation.states

import com.darsshannair.coroutinesplayground.presentation.intents.Screen

data class LoanEligibilityViewState(
    val screen: Screen = Screen.CreditScoreChecker,
    val salary: Int = 0,
    val creditScore: Int = 0,
    val loanAmount: Int = 0,
    val errorMessage: String? = null,
    val isCreditScoreCheckLoading: Boolean = false,
    val isRiskCheckLoading: Boolean = false
)
