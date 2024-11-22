package com.darsshannair.coroutinesplayground.presentation.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.darsshannair.coroutinesplayground.data.models.Transaction
import com.darsshannair.coroutinesplayground.presentation.states.ResultState
import com.darsshannair.coroutinesplayground.presentation.viewmodels.TransactionViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TransactionListScreen(viewModel: TransactionViewModel = viewModel()) {
    val transactionState by viewModel.transactions.collectAsState()

    when (transactionState) {
        is ResultState.Loading -> {
            CircularProgressIndicator()
        }
        is ResultState.Success -> {
            val transactions = (transactionState as ResultState.Success<List<Transaction>>).data
            LazyColumn {
                items(transactions) { transaction ->
                    TransactionItem(transaction)
                }
            }
        }
        is ResultState.Error -> {
            Button(onClick = { viewModel.retryFetchTransactions() }) {
                Text("Retry")
            }
        }
    }
}

@Composable
fun TransactionItem(transaction: Transaction) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "Description: ${transaction.description}")
            Text(text = "Category: ${transaction.category}")
            Text(text = "Date: ${transaction.transactionDate}")
            Text(text = "Debit: ${transaction.debit ?: 0.0}")
            Text(text = "Credit: ${transaction.credit ?: 0.0}")
        }
    }
}
