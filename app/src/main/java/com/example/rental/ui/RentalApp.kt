/*
 * Copyright (C) 2023 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.rental.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.rental.R
import com.example.rental.ui.screens.ClientScreen
import com.example.rental.ui.screens.HomeScreen
import com.example.rental.ui.screens.RentalViewModel
import com.example.rental.ui.screens.VisitInfoScreen


object Routes {
    const val PROPERTIES: String = "properties"
    const val CLIENTS: String = "clients"
    const val VISITINFO: String = "visitInfo"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun rentalApp(
    navController: NavHostController = rememberNavController()
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    var selectedItem by remember { mutableStateOf(0) }

    val barItems = listOf(
        BarItem(
            title = "Properties",
            icon = Icons.Filled.Home,
            route = Routes.PROPERTIES
        ),
        BarItem(
            title = "Clients",
            icon = Icons.Filled.Face,
            route = Routes.CLIENTS
        ),
        BarItem(
            title = "VisitInfo",
            icon = Icons.Filled.Email,
            route = Routes.VISITINFO
        ),
    )
    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = { rentalTopAppBar(scrollBehavior = scrollBehavior) },
        bottomBar = {
            NavigationBar {
                barItems.forEachIndexed { index, barItem ->
                    NavigationBarItem(
                        selected = selectedItem == index,
                        onClick = { navController.navigate(barItem.route) },
                        icon = { Icon(barItem.icon, contentDescription = barItem.title) },
                        label = { Text(barItem.title) }
                    )
                }
            }
        }
    ) {innerPadding ->


//        Surface(
//            modifier = Modifier
//                .fillMaxSize()
//                .padding(it)
//        ) {


        val rentalViewModel: RentalViewModel =
            viewModel(factory = RentalViewModel.Factory)
        NavHost(
            navController = navController,
            startDestination = Routes.PROPERTIES,
            Modifier.padding(innerPadding)
        ) {
            composable(route = Routes.PROPERTIES) {
                HomeScreen(
                    rentalUiState = rentalViewModel.rentalUiState
//                    onNextButtonClicked = {
//                        viewModel.setQuantity(it)
//                        navController.navigate(CupcakeScreen.Flavor.name)
//
//                    }
                )
            }
            composable(route = Routes.CLIENTS) {
                ClientScreen(
                    rentalUiState = rentalViewModel.rentalUiState
//                    onNextButtonClicked = {
//                        viewModel.setQuantity(it)
//                        navController.navigate(CupcakeScreen.Flavor.name)
//
//                    }
                )
            }
            composable(route = Routes.VISITINFO) {
                VisitInfoScreen(
                    rentalUiState = rentalViewModel.rentalUiState
//                    onNextButtonClicked = {
//                        viewModel.setQuantity(it)
//                        navController.navigate(CupcakeScreen.Flavor.name)
//
//                    }
                )
            }

//        }
        }
    }
}

@Composable
fun rentalTopAppBar(scrollBehavior: TopAppBarScrollBehavior, modifier: Modifier = Modifier) {
    CenterAlignedTopAppBar(
        scrollBehavior = scrollBehavior,
        title = {
            Text(
                text = stringResource(R.string.app_name),
                style = MaterialTheme.typography.headlineSmall,
            )
        },
        modifier = modifier
    )
}


data class BarItem(
    val icon: ImageVector,
    val title: String,
    val route: String
)