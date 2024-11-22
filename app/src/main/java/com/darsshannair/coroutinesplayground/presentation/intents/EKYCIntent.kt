package com.darsshannair.coroutinesplayground.presentation.intents

sealed class EKYCIntent {
    object LoadLocalData : EKYCIntent()
    object SyncData : EKYCIntent()
}