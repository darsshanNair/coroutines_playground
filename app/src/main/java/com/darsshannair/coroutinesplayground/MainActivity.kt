package com.darsshannair.coroutinesplayground

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.darsshannair.coroutinesplayground.presentation.intents.Screen
import com.darsshannair.coroutinesplayground.presentation.screens.CreditScoreCheckerScreen
import com.darsshannair.coroutinesplayground.presentation.screens.DashboardScreen
import com.darsshannair.coroutinesplayground.presentation.screens.DashboardScreenV2
import com.darsshannair.coroutinesplayground.presentation.screens.EKYCScreen
import com.darsshannair.coroutinesplayground.presentation.screens.HighRiskUserCheckerScreen
import com.darsshannair.coroutinesplayground.presentation.screens.HomeScreen
import com.darsshannair.coroutinesplayground.presentation.screens.LoanEligibilityResultScreen
import com.darsshannair.coroutinesplayground.presentation.screens.ManualJobHandlerScreen
import com.darsshannair.coroutinesplayground.presentation.screens.MemoryLeakResolverScreen
import com.darsshannair.coroutinesplayground.presentation.screens.ScopeManagementScreen
import com.darsshannair.coroutinesplayground.presentation.screens.TransactionListScreen
import com.darsshannair.coroutinesplayground.presentation.viewmodels.DownloadJobHandlerViewModel
import com.darsshannair.coroutinesplayground.presentation.viewmodels.LoanEligibilityViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val loanEligibilityViewModel = LoanEligibilityViewModel()
        val downloadJobHandlerViewModel = DownloadJobHandlerViewModel()

        setContent {
            MaterialTheme {
                val navController = rememberNavController()

                NavHost(navController = navController, startDestination = "home") {
                    // Home Screen
                    composable("home") {
                        HomeScreen(
                            onNavigateToTransactionListScreen = { navController.navigate("transactions") },
                            onNavigateToMemoryLeakResolverScreen = { navController.navigate("memoryLeakResolver") },
                            onNavigateToManualJobHandlerScreen = { navController.navigate("manualJobHandler") },
                            onNavigateToScopeManagementScreen = { navController.navigate("scopeManagement") },
                            onNavigateToDashboardScreen = { navController.navigate("dashboard") },
                            onNavigateToDashboardScreenV2 = { navController.navigate("dashboardV2") },
                            onNavigateToLoanEligibilityFlow = { navController.navigate(Screen.CreditScoreChecker.name) },
                            onNavigateToEKYCScreen = { navController.navigate("eKYC")},
                        )
                    }

                    // Existing routes
                    composable("transactions") { TransactionListScreen() }
                    composable("memoryLeakResolver") { MemoryLeakResolverScreen() }
                    composable("manualJobHandler") { ManualJobHandlerScreen(viewModel = downloadJobHandlerViewModel) }
                    composable("scopeManagement") { ScopeManagementScreen() }
                    composable("dashboard") { DashboardScreen() }
                    composable("dashboardV2") { DashboardScreenV2() }

                    // Loan Application Flow routes
                    composable(Screen.CreditScoreChecker.name) {
                        CreditScoreCheckerScreen(
                            navController = navController,
                            viewModel = loanEligibilityViewModel
                        )
                    }
                    composable(Screen.HighRiskChecker.name) {
                        HighRiskUserCheckerScreen(
                            navController = navController,
                            viewModel = loanEligibilityViewModel
                        )
                    }

                    composable(Screen.LoanEligibilityResult.name) {
                        LoanEligibilityResultScreen(
                            navController = navController,
                            viewModel = loanEligibilityViewModel
                        )
                    }

                    composable("eKYC") { EKYCScreen() }
                }
            }
        }
    }
}

