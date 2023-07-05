package com.example.dndspellhelper.presentation.character_info

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.dndspellhelper.models.PlayerCharacter
import com.example.dndspellhelper.presentation.character_selection.CharactersViewModel
import com.example.dndspellhelper.presentation.spell_list.ItemForList


@Composable
fun CharacterInfoScreen(navController: NavController, viewModel: CharactersViewModel) {

    val character by viewModel.character.collectAsState()
    val characterSpellCasting by viewModel.characterInfo.collectAsState()

    if (character != null && characterSpellCasting != null) {

        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize()
                .verticalScroll(rememberScrollState(0))
        ) {
            NameClassAndLevel(character)

            Spacer(Modifier.height(20.dp))

            PlayerStats(character)

            Spacer(Modifier.height(30.dp))

            val allSpellSlots = characterSpellCasting!!.spellcasting.getSpellcastingPairs()

            allSpellSlots.forEach { spellSlot ->

                Row(
                    verticalAlignment = CenterVertically,
                    modifier = Modifier.padding(vertical = 10.dp)
                ) {
                    Text(
                        text = "Level ${spellSlot.slot_level}",
                        fontSize = 25.sp,
                        modifier = Modifier.weight(1f)
                    )

                    Text(
                        text = "${spellSlot.amountAtLevel}",
                        fontSize = 30.sp
                    )
                }

                val knownSpellsOfLevel =
                    character!!.knownSpells.filter { it.level == spellSlot.slot_level }

                Column {
                    knownSpellsOfLevel.forEach { spell ->
                        ItemForList(spell = spell)

                        Divider()
                    }
                }

                Button(
                    onClick = {
                        viewModel.getSpellsForLevelAndClass(spellSlot.slot_level)
                        navController.navigate("pick_spells")
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Add level ${spellSlot.slot_level} spell"
                    )
                }
            }
        }
    }
}

@Composable
private fun NameClassAndLevel(character: PlayerCharacter?) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(top = 30.dp)
        ) {
            Text(text = character!!.name, fontSize = 30.sp)

            Spacer(Modifier.height(10.dp))

            Text(text = character.characterClass)
        }

        Column(
            horizontalAlignment = Alignment.End,
            modifier = Modifier.weight(1f)
        ) {
            Text(text = "Level")

            Spacer(Modifier.height(10.dp))

            Text(text = character!!.level.toString(), fontSize = 30.sp)
        }
    }
}

@Composable
private fun PlayerStats(character: PlayerCharacter?) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .padding(20.dp)
            .fillMaxWidth()
    ) {
        StatsColumn("Attack Modifier", character!!.attackModifier.toString())

        StatsColumn("Spell DC", character.spellDC.toString())

        StatsColumn("Ability Modifier", character.attackModifier.toString())

        // Добавить введение Ability Modifier при создании персонажа
    }
}

@Composable
private fun StatsColumn(statName: String, statValue: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(statName)

        Spacer(Modifier.height(10.dp))

        Text(text = statValue)
    }
}