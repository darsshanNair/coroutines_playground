package com.darsshannair.coroutinesplayground.data.models

import com.squareup.moshi.Json

data class Transaction(
    @Json(name = "transactionDate") val transactionDate: String,
    @Json(name = "description") val description: String,
    @Json(name = "category") val category: String,
    @Json(name = "debit") val debit: Double?,
    @Json(name = "credit") val credit: Double?,
    @Json(name = "id") val id: Int
)
