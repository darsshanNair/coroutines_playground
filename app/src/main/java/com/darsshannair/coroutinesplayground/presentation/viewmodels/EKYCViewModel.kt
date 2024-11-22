package com.darsshannair.coroutinesplayground.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.darsshannair.coroutinesplayground.data.models.EKYCData
import com.darsshannair.coroutinesplayground.presentation.intents.EKYCIntent
import com.darsshannair.coroutinesplayground.presentation.states.EKYCState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

class EKYCViewModel : ViewModel() {

    private val _intentFlow = MutableSharedFlow<EKYCIntent>() // Receives intents
    private val _state = MutableStateFlow(EKYCState()) // Emits state to UI
    val state: StateFlow<EKYCState> = _state.asStateFlow()

    private val localDataFlow = MutableStateFlow(getLocalData()) // Simulate local DB flow
    private val apiDataFlow = MutableStateFlow(getAPIData()) // Simulate API data flow

    init {
        processIntents()
    }

    private fun processIntents() {
        viewModelScope.launch {
            _intentFlow.collectLatest { intent ->
                when (intent) {
                    is EKYCIntent.LoadLocalData -> loadLocalData()
                    is EKYCIntent.SyncData -> syncData()
                }
            }
        }
    }

    private fun loadLocalData() {
        viewModelScope.launch {
            _state.value = _state.value.copy(
                isLoading = false,
                syncedUsers = localDataFlow.value
            )
        }
    }

    private fun syncData() {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)

            combine(localDataFlow, apiDataFlow) { localData, apiData ->
                val syncedData = mutableListOf<EKYCData>()
                val updatesToAPI = mutableListOf<EKYCData>()

                localData.forEach { localUser ->
                    val apiUser = apiData.find { it.id == localUser.id }
                    if (apiUser == null) {
                        // No corresponding API data; add to syncedData as is
                        syncedData.add(localUser)
                    } else if (localUser.isEKYCDone && !apiUser.isEKYCDone) {
                        // Local data is updated but API is not; mark for API update
                        updatesToAPI.add(localUser)
                        syncedData.add(localUser) // Add to synced data after updating API
                    } else if (apiUser.isEKYCDone) {
                        // API is already up to date; use API data
                        syncedData.add(apiUser)
                    } else {
                        // No update needed; use local data
                        syncedData.add(localUser)
                    }
                }

                // Simulate sending updates to the API
                updateAPIWithEKYC(updatesToAPI)

                return@combine syncedData
            }
                .catch { e ->
                    _state.value = _state.value.copy(
                        isLoading = false,
                        errorMessage = e.localizedMessage ?: "Error syncing data"
                    )
                }
                .collect { syncedUsers ->
                    _state.value = _state.value.copy(
                        isLoading = false,
                        syncedUsers = syncedUsers,
                        errorMessage = null
                    )
                }
        }
    }

    private suspend fun updateAPIWithEKYC(users: List<EKYCData>) {
        delay(1000)
        if (users.isNotEmpty()) {
            println("API Update: ${users.map { it.id }}") // Log IDs of updated users
        }
    }

    fun handleIntent(intent: EKYCIntent) {
        viewModelScope.launch {
            _intentFlow.emit(intent)
        }
    }

    private fun getLocalData(): List<EKYCData> = listOf(
        EKYCData("950101-01-1235", "front1.jpg", "back1.jpg", "selfie1.jpg", true),
        EKYCData("960202-02-6789", "front2.jpg", "back2.jpg", "selfie2.jpg", true),
        EKYCData("970303-03-3456", "front3.jpg", "back3.jpg", "selfie3.jpg", false)
    )

    private fun getAPIData(): List<EKYCData> = listOf(
        EKYCData("950101-01-1235", "front1_remote.jpg", "back1_remote.jpg", "selfie1_remote.jpg", true),
        EKYCData("960202-02-6789", "front2_remote.jpg", "back2_remote.jpg", "selfie2_remote.jpg", false),
        EKYCData("970303-03-3456", "front3_remote.jpg", "back3_remote.jpg", "selfie3_remote.jpg", true)
    )
}
