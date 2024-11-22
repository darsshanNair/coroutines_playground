package com.darsshannair.coroutinesplayground.presentation.states

import com.darsshannair.coroutinesplayground.data.models.AccountBalance
import com.darsshannair.coroutinesplayground.data.models.CreditScore
import com.darsshannair.coroutinesplayground.data.models.PortfolioValue

data class DashboardState(
    val balance: AccountBalance? = null,
    val creditScore: CreditScore? = null,
    val portfolioValue: PortfolioValue? = null,
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)
