package com.example.dndspellhelper.presentation.character_info

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.dndspellhelper.R
import com.example.dndspellhelper.models.PlayerCharacter
import com.example.dndspellhelper.presentation.character_selection.CharactersViewModel
import com.example.dndspellhelper.presentation.spell_list.ItemForList


@Composable
fun CharacterInfoScreen(navController: NavController, viewModel: CharactersViewModel) {

    val character by viewModel.character.collectAsState()
    val defaultSpellSlots = viewModel.defaultSpellSlots

    if (character != null) {

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

            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Spacer(modifier = Modifier.weight(1f))

                Icon(
                    painter = painterResource(id = R.drawable.moon_icon),
                    contentDescription = null,
                    modifier = Modifier
                        .size(80.dp)
                        .padding(end = 30.dp)
                        .clickable {
                            viewModel.refreshCharacterSpellSlots()
                        }
                )
            }


            for (i in defaultSpellSlots.indices) {

                Row(
                    verticalAlignment = CenterVertically,
                    modifier = Modifier.padding(vertical = 10.dp)
                ) {
                    Text(
                        text = "Level ${defaultSpellSlots[i].slot_level}",
                        fontSize = 25.sp,
                        modifier = Modifier.weight(1f)
                    )

                    if (character!!.spellCasting[i].amountAtLevel != 0) {

                        IconButton(
                            onClick = { viewModel.minusSpellSlotAtLevel(defaultSpellSlots[i].slot_level - 1) },
                            modifier = Modifier.size(30.dp)
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.minus),
                                contentDescription = null
                            )
                        }
                    }

                    Spacer(modifier = Modifier.width(10.dp))

                    Text(
                        text = "${character!!.spellCasting[i].amountAtLevel}",
                        fontSize = 30.sp
                    )

                    Spacer(modifier = Modifier.width(5.dp))

                    if (character!!.spellCasting[i].amountAtLevel != defaultSpellSlots[i].amountAtLevel) {

                        IconButton(

                            onClick = { viewModel.plusSpellSlotAtLevel(defaultSpellSlots[i].slot_level - 1) },
                            modifier = Modifier.size(30.dp)
                        ) {
                            Icon(
                                Icons.Default.Add,
                                contentDescription = null
                            )
                        }
                    } else {
                        Spacer(modifier = Modifier.width(30.dp))
                    }
                }

                val knownSpellsOfLevel =
                    character!!.knownSpells.filter { it.level == defaultSpellSlots[i].slot_level }

                Column {
                    knownSpellsOfLevel.forEach { spell ->
                        ItemForList(
                            spell = spell,
                            modifier = Modifier.clickable {
                                viewModel.emitSpell(spell)
                                viewModel.showAddButton = false
                                navController.navigate("spell_info_from_character")
                            },
                            showLevel = false,
                            action = {
                                viewModel.deleteSpellFromSpellList(spell)
                            }
                        )

                        Divider()
                    }
                }

                Button(
                    onClick = {
                        viewModel.filterClassSpellsForLevel(defaultSpellSlots[i].slot_level)
                        navController.navigate("pick_spells")
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Add level ${defaultSpellSlots[i].slot_level} spell"
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
        ) {
            Text(text = character!!.characterClass)

            Spacer(Modifier.height(10.dp))

            Text(text = character.name, fontSize = 30.sp)
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

        StatsColumn("Ability Modifier", character.abilityModifier.toString())

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