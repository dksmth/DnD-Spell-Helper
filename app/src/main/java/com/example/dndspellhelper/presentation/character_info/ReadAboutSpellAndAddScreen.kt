package com.example.dndspellhelper.presentation.character_info

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.dndspellhelper.presentation.character_selection.CharactersViewModel
import com.example.dndspellhelper.presentation.spell_info.SpellInfoScreen

@Composable
fun AddSpell(navController: NavController, viewModel: CharactersViewModel) {
    val spell = viewModel.chosenSpell.collectAsState().value

    Column {
        SpellInfoScreen(spell!!, Modifier.weight(1f))

        if (viewModel.showAddButton) {
            Button(
                onClick = {
                    viewModel.addNewSpellToCharacterSpellList(spell)
                    navController.popBackStack(
                        route = "character_info",
                        inclusive = false
                    )
                },
                modifier = Modifier
                    .padding(5.dp)
                    .fillMaxWidth()
            ) {
                Text("Add spell to character")
            }
        }
    }
}