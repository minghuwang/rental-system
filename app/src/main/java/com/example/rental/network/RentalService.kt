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

package com.example.rental.network

import com.example.rental.model.RentalProperty
import retrofit2.http.GET

/**
 * A public interface that exposes the [GetProperties] method
 */
interface RentalService {
    /**
     * Returns a [List] of [RentalProperty] and this method can be called from a Coroutine.
     * The @GET annotation indicates that the "properties" endpoint will be requested with the GET
     * HTTP method
     */
    @GET("properties")
    suspend fun GetProperties(): List<RentalProperty>
//    @POST("properties")
//    suspend fun createProperties(rentalProperties: RentalProperties): Boolean
//    @GET("visitorsInfo")
//    suspend fun getVisitorsInfo(): List<VisitorsInfo>
}
