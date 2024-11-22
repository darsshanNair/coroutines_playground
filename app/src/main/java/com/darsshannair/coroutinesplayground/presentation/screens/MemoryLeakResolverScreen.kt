package com.darsshannair.coroutinesplayground.presentation.screens

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import com.darsshannair.coroutinesplayground.presentation.viewmodels.MemoryLeakViewModel

@Composable
fun MemoryLeakResolverScreen(viewModel: MemoryLeakViewModel = viewModel()) {
    val dataState = remember { mutableStateOf("Loading...") }

    // Log when the Composable enters and leaves the UI tree
    DisposableEffect(Unit) {
        println("MemoryLeakResolverScreen has entered the UI tree.")

        // Cleanup when the Composable leaves the UI tree
        onDispose {
            println("MemoryLeakResolverScreen has left the UI tree.")
        }
    }

    // Collecting data from Flow with lifecycle awareness
    LaunchedEffect(Unit) {
        viewModel.dataFlow.collect { data ->
            dataState.value = data
        }
    }

    Text(text = dataState.value) // Displaying the data in a Composable
}

