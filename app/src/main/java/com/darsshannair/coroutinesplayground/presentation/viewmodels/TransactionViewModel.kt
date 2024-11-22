package com.darsshannair.coroutinesplayground.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.darsshannair.coroutinesplayground.data.models.Transaction
import com.darsshannair.coroutinesplayground.data.repositories.TransactionRepository
import com.darsshannair.coroutinesplayground.presentation.states.ResultState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import okio.IOException
import retrofit2.HttpException

class TransactionViewModel : ViewModel() {
    private val repository = TransactionRepository()

    // Initial state set to loading state (ResultState.Loading)
    private val _transactions = MutableStateFlow<ResultState<List<Transaction>>>(ResultState.Loading)
    val transactions: StateFlow<ResultState<List<Transaction>>> = _transactions

    init {
        fetchTransactions()
    }

    fun retryFetchTransactions() {
        fetchTransactions()
    }

    private fun fetchTransactions() {
        viewModelScope.launch {
            _transactions.value = ResultState.Loading

            try {
                val response = repository.getTransactions()
                _transactions.value = ResultState.Success(response)
            } catch (e: IOException) {
                _transactions.value = ResultState.Error("Network error. Please check your connection.")
            } catch (e: HttpException) {
                _transactions.value = ResultState.Error("Server error: ${e.message()}")
            } catch (e: Exception) {
                _transactions.value = ResultState.Error("An unexpected error occurred.")
            }
        }
    }
}
