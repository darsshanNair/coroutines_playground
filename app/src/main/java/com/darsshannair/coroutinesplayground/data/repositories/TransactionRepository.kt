package com.darsshannair.coroutinesplayground.data.repositories

import com.darsshannair.coroutinesplayground.data.RetrofitInstance
import com.darsshannair.coroutinesplayground.data.models.Transaction

class TransactionRepository {
    private val api = RetrofitInstance.api

    suspend fun getTransactions(): List<Transaction> {
        return api.getTransactions()
    }
}