package com.darsshannair.coroutinesplayground.presentation.intents

sealed class LoanEligibilityIntent {
    data class CheckCreditScoreIntent(val salary: Int, val commitments: Int) : LoanEligibilityIntent()
    data class CheckHighRiskInfoIntent(
        val isPoliticallyExposed: Boolean,
        val isCelebrity: Boolean,
        val isIncomeGamblingRelated: Boolean
    ) : LoanEligibilityIntent()

    object ShowEligibleAmountIntent : LoanEligibilityIntent()
}

enum class Screen {
    CreditScoreChecker,
    HighRiskChecker,
    LoanEligibilityResult,
}
