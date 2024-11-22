package com.darsshannair.coroutinesplayground.presentation.states

import com.darsshannair.coroutinesplayground.data.models.AccountBalance
import com.darsshannair.coroutinesplayground.data.models.CreditScore
import com.darsshannair.coroutinesplayground.data.models.PortfolioValue

data class DashboardV2State(
    val isLoading: Boolean = false,
    val balance: AccountBalance? = null,
    val creditScore: CreditScore? = null,
    val portfolioValue: PortfolioValue? = null,
    val balanceErrorMessage: String? = null,
    val creditScoreErrorMessage: String? = null,
    val portfolioValueErrorMessage: String? = null,
)
