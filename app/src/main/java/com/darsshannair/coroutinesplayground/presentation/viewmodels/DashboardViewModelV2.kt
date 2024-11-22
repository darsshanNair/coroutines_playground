package com.darsshannair.coroutinesplayground.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.darsshannair.coroutinesplayground.data.models.AccountBalance
import com.darsshannair.coroutinesplayground.data.models.CreditScore
import com.darsshannair.coroutinesplayground.data.models.PortfolioValue
import com.darsshannair.coroutinesplayground.presentation.intents.DashboardIntent
import com.darsshannair.coroutinesplayground.presentation.states.DashboardV2State
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DashboardViewModelV2 : ViewModel() {

    private val _state = MutableStateFlow(DashboardV2State())
    val state: StateFlow<DashboardV2State> = _state

    fun loadDashboardDataWithRunCatching() {
        _state.value = _state.value.copy(isLoading = true)

        viewModelScope.launch {
            // Fetch balance
            val balanceResult = runCatching { fetchAccountBalance() }
            val balance = balanceResult.getOrNull()
            val balanceError = balanceResult.exceptionOrNull()?.message

            // Fetch credit score
            val creditScoreResult = runCatching { fetchCreditScore() }
            val creditScore = creditScoreResult.getOrNull()
            val creditScoreError = creditScoreResult.exceptionOrNull()?.message

            // Fetch portfolio value
            val portfolioValueResult = runCatching { fetchPortfolioValueWithException() }
            val portfolioValue = portfolioValueResult.getOrNull()
            val portfolioValueError = portfolioValueResult.exceptionOrNull()?.message

            // Update state
            _state.value = DashboardV2State(
                isLoading = false,
                balance = balance,
                creditScore = creditScore,
                portfolioValue = portfolioValue,
                balanceErrorMessage = balanceError,
                creditScoreErrorMessage = creditScoreError,
                portfolioValueErrorMessage = portfolioValueError
            )
        }
    }

    fun handleIntent(intent: DashboardIntent) {
        when (intent) {
            DashboardIntent.LoadDashboardInfoIntent -> loadDashboardDataWithRunCatching()
        }
    }

    // Simulated API calls
    private suspend fun fetchAccountBalance(): AccountBalance {
        delay(1000)
        return AccountBalance(1234.56)
    }

    private suspend fun fetchCreditScore(): CreditScore {
        delay(1000)
        return CreditScore(750)
    }

    private suspend fun fetchPortfolioValueWithException(): PortfolioValue {
        delay(1000)
        throw Exception("Portfolio service unavailable")
    }
}
