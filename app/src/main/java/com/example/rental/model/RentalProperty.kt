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

package com.example.rental.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * This data class defines a rental properties which includes an id, addres and picture address.
 */
@Serializable
data class RentalProperty(
    val PropertyID: Int,
    val Address: String,
    val PictureLink: String,
    val OpenTime1: Int = 0,
    val OpenTime2:Int = 0,
)
@Serializable
data class Client(
    @SerialName(value = "full_name")
    val fullName: String,
    val email: String
)
@Serializable
data class VisitInfo(
    val visitorEmail: String,
    val propertiesAddr:String,
    val visitTime:String,
)
