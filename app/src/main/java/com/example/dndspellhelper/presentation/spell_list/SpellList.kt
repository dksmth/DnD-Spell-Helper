package com.example.dndspellhelper.presentation.spell_list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import me.saket.swipe.SwipeAction
import me.saket.swipe.SwipeableActionsBox

@Composable
fun SpellList(
    navController: NavController,
    viewModel: ActivityViewModel,
) {
    LazyColumn {
        items(items = viewModel.spellNames) { spell ->

            val iconForSwipeAction =
                if (spell.favourite) Icons.Default.Delete else Icons.Default.Favorite

            val colorForSwipeAction =
                if (spell.favourite) Color(0xFFFF0000) else Color(0xFFDC95F5)

            val action = SwipeAction(
                icon = rememberVectorPainter(image = iconForSwipeAction),
                background = colorForSwipeAction,
                onSwipe = {
                    viewModel.updateFavouritesStatus(spell, !spell.favourite)
                }
            )

            SwipeableActionsBox(endActions = listOf(action)) {
                ItemForList(
                    spell,
                    modifier = Modifier
                        .clickable {
                            viewModel.chosenSpell = spell
                            navController.navigate("spell_info")
                        }
                )
            }

            Divider(
                color = Color.Black,
                thickness = 1.dp
            )
        }
    }
}