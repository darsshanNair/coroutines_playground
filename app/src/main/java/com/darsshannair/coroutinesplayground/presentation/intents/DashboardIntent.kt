package com.darsshannair.coroutinesplayground.presentation.intents

sealed class DashboardIntent {
    object LoadDashboardInfoIntent : DashboardIntent()
}