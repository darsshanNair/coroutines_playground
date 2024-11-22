package com.darsshannair.coroutinesplayground.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.darsshannair.coroutinesplayground.data.models.AccountBalance
import com.darsshannair.coroutinesplayground.data.models.CreditScore
import com.darsshannair.coroutinesplayground.data.models.PortfolioValue
import com.darsshannair.coroutinesplayground.presentation.intents.DashboardIntent
import com.darsshannair.coroutinesplayground.presentation.states.DashboardState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DashboardViewModel : ViewModel() {

    private val _state = MutableStateFlow(DashboardState())
    val state: StateFlow<DashboardState> = _state

    fun handleIntent(intent: DashboardIntent) {
        when (intent) {
            is DashboardIntent.LoadDashboardInfoIntent -> loadDashboardDataInOrderlyFashion()
        }
    }

    private fun loadDashboardDataInOrderlyFashion() {
        _state.value = _state.value.copy(isLoading = true)

        viewModelScope.launch {
            try {
                // Simultaneous API requests with structured concurrency
                val balanceDeferred = async { fetchAccountBalance() }
                val creditScoreDeferred = async { fetchCreditScore() }
                val portfolioValueDeferred = async { fetchPortfolioValue() }

                // Await all data concurrently
                val balance = balanceDeferred.await()
                val creditScore = creditScoreDeferred.await()
                val portfolioValue = portfolioValueDeferred.await()

                // Update state with fetched data
                _state.value = DashboardState(
                    balance = balance,
                    creditScore = creditScore,
                    portfolioValue = portfolioValue,
                    isLoading = false
                )

            } catch (e: Exception) {
                // Handle any exception and update the state with an error message
                _state.value = _state.value.copy(
                    isLoading = false,
                    errorMessage = e.localizedMessage ?: "An unknown error occurred"
                )
            }
        }
    }

    private fun loadDashboardDataWithoutOrder() {
        _state.value = _state.value.copy(isLoading = true)

        viewModelScope.launch {
            try {
                val balanceDeferred = async { fetchAccountBalance() }
                val creditScoreDeferred = async { fetchCreditScore() }
                val portfolioValueDeferred = async { fetchPortfolioValue() }

                val results = awaitAll(balanceDeferred, creditScoreDeferred, portfolioValueDeferred)

                val balance = results[0] as AccountBalance
                val creditScore = results[1] as CreditScore
                val portfolioValue = results[2] as PortfolioValue

                _state.value = DashboardState(
                    balance = balance,
                    creditScore = creditScore,
                    portfolioValue = portfolioValue,
                    isLoading = false
                )
            } catch (e: Exception) {
                _state.value = DashboardState(
                    errorMessage = e.localizedMessage,
                    isLoading = false
                )
            }
        }
    }

    private fun loadDashboardDataWithoutRunCatching() {
        _state.value = _state.value.copy(isLoading = true)

        viewModelScope.launch {
            try {
                // Structured concurrency without individual error handling
                val balanceDeferred = async { fetchAccountBalance() } // Will throw if it fails
                val creditScoreDeferred = async { fetchCreditScore() } // Will throw if it fails
                val portfolioValueDeferred = async { fetchPortfolioValueThrowException() } // Simulated exception

                // Awaiting results, exception in one will cancel all
                val balance = balanceDeferred.await()
                val creditScore = creditScoreDeferred.await()
                val portfolioValue = portfolioValueDeferred.await()

                // Update state with fetched data
                _state.value = DashboardState(
                    balance = balance,
                    creditScore = creditScore,
                    portfolioValue = portfolioValue,
                    isLoading = false
                )
            } catch (e: Exception) {
                // The entire coroutine is cancelled, and the app may lose partial data
                _state.value = _state.value.copy(
                    isLoading = false,
                    errorMessage = e.localizedMessage ?: "An unknown error occurred"
                )
            }
        }
    }

    private suspend fun fetchAccountBalance(): AccountBalance {
        delay(1000)
        return AccountBalance(amount = 1500.00)
    }

    private suspend fun fetchCreditScore(): CreditScore {
        delay(800)
        return CreditScore(score = 750)
    }

    private suspend fun fetchPortfolioValue(): PortfolioValue {
        delay(1100)
        return PortfolioValue(value = 5000.00)
    }

    private suspend fun fetchPortfolioValueThrowException(): PortfolioValue {
        delay(1500)
        throw Exception("Portfolio service unavailable")
    }
}
