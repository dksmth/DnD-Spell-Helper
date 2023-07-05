package com.example.dndspellhelper.presentation.character_info

import androidx.compose.foundation.clickable
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
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
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
                        modifier = Modifier.clickable {
                            viewModel.addNewSpellToCharacterSpellList(spell)
                            navController.popBackStack(route = "character_info", inclusive = false)
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
