package com.darsshannair.coroutinesplayground.presentation.viewmodels

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow

class MemoryLeakViewModel : ViewModel() {
    val dataFlow = flow {
        while (true) {
            emit("Data received at ${System.currentTimeMillis()}")
            delay(1000L)  // Emitting data every second
        }
    }
}