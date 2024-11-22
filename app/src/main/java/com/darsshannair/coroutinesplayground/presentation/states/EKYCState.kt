package com.darsshannair.coroutinesplayground.presentation.states

import com.darsshannair.coroutinesplayground.data.models.EKYCData

data class EKYCState(
    val isLoading: Boolean = false,
    val syncedUsers: List<EKYCData> = emptyList(),
    val errorMessage: String? = null
)
