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
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.example.rental.R
import com.example.rental.ui.screens.applyVisitScreen
import com.example.rental.ui.screens.ClientScreen
import com.example.rental.ui.screens.HomeScreen
import com.example.rental.ui.screens.RentalViewModel
import com.example.rental.ui.screens.visitorsScreen


object Routes {
    const val PROPERTIES: String = "properties"
    const val CLIENTS: String = "clients"
    const val VISITORS: String = "visitors"
    const val VISIT: String = "visit" // Virtual nested graph

    object ApplyVisitDirections {
        val root = object : NavigationCommand {
            override val arguments = emptyList<NamedNavArgument>()
            override val destination = "applyvisit"
        }
//    val root = object: NavigationCommand {
//        override val arguments = emptyList<NamedNavArgument>()
//        override val destination = "authentication"
//    }
    }
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
            title = "Visitors",
            icon = Icons.Filled.Email,
            route = Routes.VISITORS
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
    ) { innerPadding ->
        val rentalViewModel: RentalViewModel =
            viewModel(factory = RentalViewModel.Factory)
        NavHost(
            navController = navController,
            startDestination = Routes.PROPERTIES,
            Modifier.padding(innerPadding)
        ) {
            composable(route = Routes.PROPERTIES) {
                HomeScreen(
                    rentalUiState = rentalViewModel.rentalUiState,
                    navController = navController
                )
            }
            navigation(
                startDestination = Routes.ApplyVisitDirections.root.destination, // The true composable route, if not found, error comes.
                route = Routes.VISIT // virtual name of the nested graph
            ) {
                composable(route = Routes.ApplyVisitDirections.root.destination) {
                    applyVisitScreen(rentalUiState = rentalViewModel.rentalUiState)
                }

                // TODO: Add more...
            }
            composable(route = Routes.CLIENTS) {
                ClientScreen(rentalUiState = rentalViewModel.rentalUiState)
            }
            composable(route = Routes.VISITORS) {
                visitorsScreen(rentalUiState = rentalViewModel.rentalUiState)
            }
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