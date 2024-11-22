package com.darsshannair.coroutinesplayground.data.interfaces

import com.darsshannair.coroutinesplayground.data.models.Transaction
import retrofit2.http.GET

interface BankApiService {
    @GET("accounts")
    suspend fun getTransactions(): List<Transaction>
}