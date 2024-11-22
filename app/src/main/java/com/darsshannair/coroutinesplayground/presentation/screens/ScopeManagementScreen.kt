package com.darsshannair.coroutinesplayground.presentation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

// Wrong invocation of LaunchedScope outside of Compose context
//@Composable
//fun ScopeManagementScreen() {
//    var coroutineStatus by remember { mutableStateOf("Coroutine Status: Not Started") }
//
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(16.dp),
//        horizontalAlignment = Alignment.CenterHorizontally,
//        verticalArrangement = Arrangement.Center
//    ) {
//        Text(text = coroutineStatus)
//
//        Button(
//            onClick = {
//                coroutineStatus = "Coroutine Status: Running..."
//                LaunchedEffect(Unit) {
//                    delay(5000) // Simulates long-running task
//                    coroutineStatus = "Coroutine Status: Completed!"
//                }
//            }
//        ) {
//            Text("Start Coroutine")
//        }
//    }
//}




















@Composable
fun ScopeManagementScreen() {
    var coroutineStatus by remember { mutableStateOf("Coroutine Status: Not Started") }
    var startCoroutine by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = coroutineStatus)

        Button(
            onClick = {
                coroutineStatus = "Coroutine Status: Running..."
                startCoroutine = true // Trigger LaunchedEffect
            }
        ) {
            Text("Start Coroutine")
        }

        // LaunchedEffect will respond to startCoroutine being set to true
        LaunchedEffect(startCoroutine) {
            if (startCoroutine) {
                delay(5000) // Simulates long-running task
                coroutineStatus = "Coroutine Status: Completed!"
                startCoroutine = false // Reset to avoid re-triggering
            }
        }
    }
}

//Note: In Jetpack Compose, the need for lifecycleScope is significantly reduced, as Compose provides
//      its own lifecycle-aware coroutine tools like LaunchedEffect, rememberCoroutineScope, and
//      DisposableEffect that automatically handle coroutine cancellation when a composable leaves
//      the UI tree. These tools allow for seamless coroutine management directly within composables,
//      making explicit lifecycleScope management unnecessary in most cases.

