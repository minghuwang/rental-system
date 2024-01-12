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

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.rental.RentalApplication
import com.example.rental.data.RentalRepository
import com.example.rental.model.RentalProperty
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

/**
 * UI state for the Home screen
 */
sealed interface RentalUiState {
    data class Success(val listProperties: List<RentalProperty>) : RentalUiState
    object Error : RentalUiState
    object Loading : RentalUiState
}

class RentalViewModel(private val rentalRepository: RentalRepository) : ViewModel() {
    /** The mutable State that stores the status of the most recent request */
    var rentalUiState: RentalUiState by mutableStateOf(RentalUiState.Loading)
        private set

    /**
     * Call getRetalProperties() on init so we can display status immediately.
     */
    init {
        getRetalProperties()
    }

    /**
     * Gets rental properties information from the Retrofit service and updates the
     * [RentalProperty] [List] [MutableList].
     */
    fun getRetalProperties() {
        viewModelScope.launch {
            rentalUiState = RentalUiState.Loading
            rentalUiState = try {
                val result = rentalRepository.getRentalProperties()
                RentalUiState.Success(result)
            } catch (e: IOException) {
                RentalUiState.Error
            } catch (e: HttpException) {
                RentalUiState.Error
            }
        }
    }

    /**
     * Factory for [RentalViewModel] that takes [RentalRepository] as a dependency
     */
    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as RentalApplication)
                val rRepository = application.container.rentalRepository
                RentalViewModel(rentalRepository = rRepository)
            }
        }
    }
}
