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
package com.example.rental.fake

import com.example.rental.model.RentalProperty

object FakeDataSource {

    private const val idOne = "1"
    private const val idTwo = "img2"
    private const val address1 = "url.one"
    private const val picture1 = "picture.1"
    val rentalList = listOf(
        RentalProperty(
            id = idOne,
            Address = address1,
            picture = picture1
        ),
        RentalProperty(
            id = idOne,
            Address = address1,
            picture = picture1
        )
    )
}
