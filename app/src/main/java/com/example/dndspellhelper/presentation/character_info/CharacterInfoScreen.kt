package com.example.dndspellhelper.presentation.character_info

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dndspellhelper.models.PlayerCharacter
import com.example.dndspellhelper.presentation.character_selection.CharactersViewModel
import com.example.dndspellhelper.ui.theme.DnDSpellHelperTheme


@Composable
fun CharacterInfoScreen(viewModel: CharactersViewModel? = null) {

    //val character by viewModel.character.collectAsState()
    //val characterSpellCasting by viewModel.characterInfo.collectAsState()

    val character = PlayerCharacter()
    val characterSpellCasting = 1


    if (character != null && characterSpellCasting != null) {

        Column(modifier = Modifier.fillMaxSize()) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                Column() {
                    Text(text = character!!.name, fontSize = 20.sp)

                    Spacer(Modifier.height(10.dp))

                    Text(text = character!!.characterClass)
                }
                Column() {
                    Text(text = "Level")

                    Spacer(Modifier.height(10.dp))

                    Text(text = character!!.level.toString(), fontSize = 20.sp)
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                Column() {

                }
                Column() {

                }
                Column() {

                }
            }
        }
    }
}

@Preview
@Composable
fun Preview() {
    DnDSpellHelperTheme() {
        CharacterInfoScreen()
    }
}