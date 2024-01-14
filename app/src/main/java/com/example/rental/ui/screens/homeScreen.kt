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
package com.example.rental.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.rental.R
import com.example.rental.baseUrl
import com.example.rental.model.RentalProperty
import com.example.rental.ui.Routes
import com.example.rental.ui.theme.RentalTheme

@Composable
fun HomeScreen(
    rentalUiState: RentalUiState,
    modifier: Modifier = Modifier,
    navController: NavController
) {
    when (rentalUiState) {
        is RentalUiState.Loading -> LoadingScreen(modifier = modifier.fillMaxSize())
        is RentalUiState.Success -> resultScreen(
            rentalUiState.listProperties, modifier = modifier.fillMaxWidth(), navController
        )

        is RentalUiState.Error -> ErrorScreen(modifier = modifier.fillMaxSize())
    }
}

/**
 * The home screen displaying the loading message.
 */
@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
    Image(
        modifier = modifier.size(200.dp),
        painter = painterResource(R.drawable.loading_img),
        contentDescription = stringResource(R.string.loading)
    )
}

/**
 * The home screen displaying error message with re-attempt button.
 */
@Composable
fun ErrorScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_connection_error), contentDescription = ""
        )
        Text(text = stringResource(R.string.loading_failed), modifier = Modifier.padding(16.dp))
    }
}

/**
 * ResultScreen displaying number of photos retrieved.
 */
@Composable
fun resultScreen(
    properties: List<RentalProperty>,
    modifier: Modifier = Modifier,
    navController: NavController
) {
    LazyColumn(modifier = modifier) {
        items(properties) { property ->
            propertyCard(property, modifier, navController)
        }
    }

}

@Composable
fun propertyCard(
    property: RentalProperty,
    modifier: Modifier = Modifier,
    navController: NavController
) {
    Card(
        modifier = modifier
            .padding(4.dp)
            .fillMaxWidth()
            .aspectRatio(5f), // this defines the width and height ratio
        shape = MaterialTheme.shapes.small,
        elevation = CardDefaults.cardElevation(defaultElevation = 12.dp)
    ) {
        Surface(
            onClick = { navController.navigate(Routes.VISIT) }
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
            ) {
                Column(
                    modifier = Modifier.weight(1f),
//                horizontalAlignment = Alignment.Start,
//                verticalArrangement = Arrangement.Center
                ) {
                    AsyncImage(
                        model = ImageRequest.Builder(context = LocalContext.current)
                            .data(baseUrl + property.PictureLink)
                            .crossfade(true).build(),
                        error = painterResource(R.drawable.ic_broken_image),
                        placeholder = painterResource(R.drawable.loading_img),
                        contentDescription = stringResource(R.string.placeholder),
                        contentScale = ContentScale.Crop,
//                    modifier = Modifier.size(width = 80.dp, height = 80.dp)
                        modifier = Modifier.aspectRatio(1f),
                    )
                }

                Column(
                    modifier = Modifier.fillMaxHeight().weight(1f),
//                horizontalAlignment = Alignment.CenterHorizontally,
//                verticalArrangement = Arrangement.Center
                ) {
                    // Local image
//            Image(
//                modifier = modifier,
//                painter = painterResource(id = R.drawable.pexels_photo),
//                contentDescription = stringResource(id = R.string.big_house),
//                contentScale = ContentScale.FillWidth,
//            )

                    Text(
                        modifier = modifier,
                        text = "Address: ${property.Address}",
                        style = MaterialTheme.typography.labelSmall
                    )
                    Text(
                        modifier = modifier,
                        text = "OpenTime1: ${property.OpenTime1}",
                        style = MaterialTheme.typography.labelSmall
                    )
                    Text(
                        modifier = modifier,
                        text = "OpenTime2: ${property.OpenTime2}",
                        style = MaterialTheme.typography.labelSmall
                    )

                }
//            Column(
//                modifier = Modifier.aspectRatio(1f).weight(1f),
////                horizontalAlignment = Alignment.End,
////                verticalArrangement = Arrangement.Center
//            ) {
//
//                Button(
//                    modifier = modifier.fillMaxHeight().aspectRatio(1f),
//                    onClick = { navController.navigate(Routes.VISIT) }
//                ) {
//                    Text(
//                        modifier = modifier,
//                        text = stringResource(R.string.apply),
//                        fontSize = 6.sp
//                    )
//                }
//            }
            }
        }
    }
}

//@Preview(showBackground = true)
@Composable
fun LoadingScreenPreview() {
    RentalTheme {
        LoadingScreen()
    }
}

//@Preview(showBackground = true)
@Composable
fun ErrorScreenPreview() {
    RentalTheme {
        ErrorScreen()
    }
}

@Preview(showBackground = true)
@Composable
fun resultScreenPreview() {
    RentalTheme {
        val mockData = List(10) {
            RentalProperty(PropertyID = it, Address = "xxx address", PictureLink = "pic")
        }
        resultScreen(mockData, navController = rememberNavController())
    }
}
