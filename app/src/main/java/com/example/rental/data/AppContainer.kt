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
package com.example.rental.data

import com.example.rental.baseUrl
import com.example.rental.network.RentalService
import retrofit2.Retrofit
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

/**
 * Dependency Injection container at the application level.
 */
interface AppContainer {
    val rentalRepository: RentalRepository
}

/**
 * Implementation for the Dependency Injection container at the application level.
 *
 * Variables are initialized lazily and the same instance is shared across the whole app.
 */
class DefaultAppContainer : AppContainer {
//    private val baseUrl = "http://192.168.1.230:8088/"
    val interceptor : HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    val client : OkHttpClient = OkHttpClient.Builder().apply {
        addInterceptor(interceptor)
    }.build()


    /**
     * Use the Retrofit builder to build a retrofit object using a kotlinx.serialization converter
     */
    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
        .client(client)
        .build()

    /**
     * Retrofit service object for creating api calls
     */
    private val retrofitService: RentalService by lazy {
        retrofit.create(RentalService::class.java)
    }

    /**
     * DI implementation for Rental repository
     */
    override val rentalRepository: RentalRepository by lazy {
        NetworkRentalRepository(retrofitService)
    }
}
