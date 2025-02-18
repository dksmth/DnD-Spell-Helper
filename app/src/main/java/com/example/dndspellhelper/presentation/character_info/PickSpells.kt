package com.example.dndspellhelper.presentation.character_info

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.dndspellhelper.presentation.MainActivity.Companion.CHARACTER_INFO_ROUTE
import com.example.dndspellhelper.presentation.MainActivity.Companion.SPELL_INFO_FROM_CHARACTER_ROUTE
import com.example.dndspellhelper.presentation.character_selection.CharactersViewModel
import com.example.dndspellhelper.presentation.spell_list.ItemForList

@Composable
fun PickSpells(
    navController: NavController,
    viewModel: CharactersViewModel,
) {

    val spells by viewModel.filteredSpells.collectAsState()

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        if (spells != null) {
            LazyColumn {
                items(spells!!) { spell ->

                    ItemForList(
                        spell,
                        modifier = Modifier
                            .pointerInput(Unit) {
                                detectTapGestures(
                                    onTap = {
                                        viewModel.emitSpell(spell)
                                        viewModel.showAddButton = true
                                        navController.navigate(SPELL_INFO_FROM_CHARACTER_ROUTE)
                                    },
                                    onLongPress = {
                                        viewModel.addSpellToSpellList(spell)
                                        navController.popBackStack(
                                            route = CHARACTER_INFO_ROUTE,
                                            inclusive = false
                                        )
                                    }
                                )
                            }
                    )

                    Divider(
                        color = Color.White,
                        thickness = 1.dp
                    )
                }
            }
        }
    }
}
