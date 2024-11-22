package com.darsshannair.coroutinesplayground.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.darsshannair.coroutinesplayground.presentation.intents.LoanEligibilityIntent
import com.darsshannair.coroutinesplayground.presentation.intents.Screen
import com.darsshannair.coroutinesplayground.presentation.states.LoanEligibilityViewState
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.plus
import kotlin.coroutines.cancellation.CancellationException

class LoanEligibilityViewModel : ViewModel() {

    private val _viewState = MutableStateFlow(LoanEligibilityViewState())
    val viewState: StateFlow<LoanEligibilityViewState> = _viewState

    private val supervisorJob = SupervisorJob()
    private val scope = viewModelScope + supervisorJob  // Structured concurrency

    private var creditScoreJob: Deferred<Int>? = null
    private var riskCheckJob: Deferred<Boolean>? = null
    private var getEligibleAmountJob: Deferred<Int>? = null
    private var loanEligibilityJob: Job? = null

    fun handleIntent(intent: LoanEligibilityIntent) {
        try {
            loanEligibilityJob = scope.launch {
                when (intent) {
                    is LoanEligibilityIntent.CheckCreditScoreIntent -> {
                        _viewState.value = _viewState.value.copy(
                            isCreditScoreCheckLoading = true,
                            screen = Screen.HighRiskChecker,
                            salary = intent.salary,
                        )
                        creditScoreJob =
                            async { calculateCreditScore(intent.salary, intent.commitments) }

                        val creditScore = creditScoreJob!!.await()
                        handleCreditScoreResult(creditScore)
                    }

                    is LoanEligibilityIntent.CheckHighRiskInfoIntent -> {
                        _viewState.value = _viewState.value.copy(
                            isRiskCheckLoading = true,
                            screen = Screen.LoanEligibilityResult
                        )
                        riskCheckJob = async {
                            calculateRisk(
                                intent.isPoliticallyExposed,
                                intent.isCelebrity,
                                intent.isIncomeGamblingRelated
                            )
                        }

                        val isHighRiskUser = riskCheckJob!!.await()
                        handleRiskResult(isHighRiskUser)
                    }

                    is LoanEligibilityIntent.ShowEligibleAmountIntent -> {
                        getEligibleAmountJob =
                                async { getEligibleLoanAmount(_viewState.value.creditScore, _viewState.value.salary) }

                        val eligibleLoanAmount = getEligibleAmountJob!!.await()
                        _viewState.value = _viewState.value.copy(
                            screen = Screen.LoanEligibilityResult,
                            loanAmount = eligibleLoanAmount,
                        )

                    }
                }
            }
        } catch (e: CancellationException) {
            println("Jobs canceled: ${e.message}")
        }
    }

    private suspend fun calculateCreditScore(salary: Int, commitments: Int): Int {
        delay(10000)
        val ratio = commitments.toDouble() / salary
        return when {
            ratio > 0.45 -> -1
            ratio <= 0.10 -> 9
            ratio <= 0.20 -> 8
            ratio <= 0.30 -> 7
            ratio <= 0.35 -> 6
            ratio <= 0.40 -> 5
            else -> 4
        }
    }

    private fun handleCreditScoreResult(creditScore: Int) {
        if (creditScore == -1) {
            cancelAllJobs()
            _viewState.value = _viewState.value.copy(
                isCreditScoreCheckLoading = false,
                isRiskCheckLoading = false,
                screen = Screen.LoanEligibilityResult,
                loanAmount = 0,
                errorMessage = "Your credit ratings are bad. You are not eligible to apply for a loan."
            )
        } else {
            _viewState.value = _viewState.value.copy(
                isCreditScoreCheckLoading = false,
                creditScore = creditScore
            )
        }
    }

    private suspend fun calculateRisk(
        isPoliticallyExposed: Boolean,
        isCelebrity: Boolean,
        isIncomeGamblingRelated: Boolean
    ): Boolean {
        delay(5000)
        return isPoliticallyExposed || isCelebrity || isIncomeGamblingRelated
    }

    private fun handleRiskResult(isHighRisk: Boolean) {
        if (isHighRisk) {
            cancelAllJobs()
            _viewState.value = _viewState.value.copy(
                isCreditScoreCheckLoading = false,
                isRiskCheckLoading = false,
                screen = Screen.LoanEligibilityResult,
                errorMessage = "You are a high-risk client. Please visit our branch."
            )
        } else {
            _viewState.value = _viewState.value.copy(
                isRiskCheckLoading = false,
            )
        }
    }

    // Function to calculate the eligible loan amount
    private suspend fun getEligibleLoanAmount(creditScore: Int, salary: Int): Int {
        // Simulate a delay (e.g., for network/database)
        delay(2500)

        return when (creditScore) {
            9 -> salary * 12
            8 -> salary * 10
            7 -> salary * 7
            6 -> salary * 5
            5 -> salary * 2
            4 -> salary * 1
            else -> 0
        }
    }

    private fun cancelAllJobs() {
        creditScoreJob?.cancel()
        riskCheckJob?.cancel()
        getEligibleAmountJob?.cancel()
        loanEligibilityJob?.cancel()
    }
}
