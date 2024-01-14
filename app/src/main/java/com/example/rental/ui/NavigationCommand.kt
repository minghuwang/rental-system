package com.example.rental.ui

import androidx.navigation.NamedNavArgument

interface NavigationCommand {
    val arguments: List<NamedNavArgument>
    val destination: String
}
