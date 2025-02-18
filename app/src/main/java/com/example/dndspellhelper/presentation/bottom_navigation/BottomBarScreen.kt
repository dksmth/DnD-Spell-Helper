package com.example.dndspellhelper.presentation.bottom_navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomBarScreen(
    val route: String,
    val title: String,
    val icon: ImageVector,
) {
    object SpellList: BottomBarScreen(
        route = "spell_list",
        title = "Spells",
        icon = Icons.Default.Search
    )
    object Characters: BottomBarScreen(
        route = "character_list",
        title = "Characters",
        icon = Icons.Default.Favorite
    )
}