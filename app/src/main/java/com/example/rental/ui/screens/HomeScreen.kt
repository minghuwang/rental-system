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
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
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
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.rental.R
import com.example.rental.model.RentalProperty
import com.example.rental.ui.theme.RentalTheme

@Composable
fun HomeScreen(
    rentalUiState: RentalUiState, modifier: Modifier = Modifier
) {
    when (rentalUiState) {
        is RentalUiState.Loading -> LoadingScreen(modifier = modifier.fillMaxSize())
        is RentalUiState.Success -> resultScreen(
            rentalUiState.listProperties, modifier = modifier.fillMaxWidth()
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
fun resultScreen(properties: List<RentalProperty>, modifier: Modifier = Modifier) {
    LazyColumn(modifier = modifier) {
        items(properties) { property ->
            propertyCard(property, modifier)
        }
    }

}
@Composable
fun propertyCard(property: RentalProperty, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier
        .padding(4.dp)
        .fillMaxWidth()
        .aspectRatio(1.5f),
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(
            modifier = Modifier,
            verticalArrangement = Arrangement.Center,
        ){
            AsyncImage(
                model = ImageRequest.Builder(context = LocalContext.current).data(property.PictureLink)
                    .crossfade(true).build(),
                error = painterResource(R.drawable.ic_broken_image),
                placeholder = painterResource(R.drawable.loading_img),
                contentDescription = stringResource(R.string.placeholder),
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxWidth()
            )
            Text(
                text = "${property.Address}",
                style = MaterialTheme.typography.labelSmall
            )

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
        resultScreen(mockData)
    }
}
