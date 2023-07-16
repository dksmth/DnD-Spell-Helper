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
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Settings
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
import com.example.dndspellhelper.models.Spell
import com.example.dndspellhelper.presentation.character_selection.CharactersViewModel
import com.example.dndspellhelper.presentation.spell_list.ItemForList


@Composable
fun CharacterInfoScreen(navController: NavController, viewModel: CharactersViewModel) {

    val character by viewModel.character.collectAsState()
    val defaultSpellSlots = viewModel.defaultSpellSlots

    if (character != null) {

        Scaffold(topBar = {
            TopAppBar(Modifier.fillMaxWidth()) {

                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
                }

                Spacer(modifier = Modifier.weight(1f))

                IconButton(
                    onClick = {

                    }
                ) {
                    Icon(imageVector = Icons.Default.Settings, contentDescription = null)
                }
            }
        }, content = { it ->

            Column(
                modifier = Modifier
                    .padding(it)
                    .padding(16.dp)
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState(0))
            ) {
                NameClassAndLevel(character)

                Spacer(Modifier.height(20.dp))

                PlayerStats(character)

                Spacer(Modifier.height(30.dp))

                RefreshSpellIcon(viewModel)

                for (i in defaultSpellSlots.indices) {

                    val (slotLevel, defaultAmountAtLevel) = defaultSpellSlots[i]

                    if (defaultAmountAtLevel != 0) {
                        val knownSpellsOfLevel =
                            character!!.knownSpells.filter { it.level == slotLevel }

                        val (_, currentAmountAtLevel) = character!!.spellCasting[i]

                        LevelOfSpellSlots(
                            slotLevel,
                            knownSpellsOfLevel.size,
                            defaultAmountAtLevel,
                            currentAmountAtLevel,
                            viewModel
                        )

                        ShowSpellsColumn(
                            knownSpellsOfLevel = knownSpellsOfLevel,
                            actionOnSpellClick = {
                                viewModel.emitSpell(it)
                                viewModel.showAddButton = false
                                navController.navigate("spell_info_from_character")
                            },
                            actionOnDeleteSpell = {
                                viewModel.deleteSpellFromSpellList(it)
                            }
                        )

                        ButtonToAddSpellsOfLevel(
                            level = defaultSpellSlots[i].slot_level,
                            action = {
                                viewModel.filterClassSpellsForLevel(defaultSpellSlots[i].slot_level)
                                navController.navigate("pick_spells")
                            }
                        )
                    }
                }
            }
        })
    }
}

@Composable
private fun RefreshSpellIcon(viewModel: CharactersViewModel) {
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
}

@Composable
private fun LevelOfSpellSlots(
    slotLevel: Int,
    amountOfKnownSpells: Int,
    defaultAmountAtLevel: Int,
    currentAmountAtLevel: Int = 0,
    viewModel: CharactersViewModel,
) {
    Row(
        verticalAlignment = CenterVertically,
        modifier = Modifier
            .padding(vertical = 10.dp)
    ) {
        val isCantrip = slotLevel == 0

        val spellSlotLevelText = if (isCantrip) "Cantrips" else "Level $slotLevel"

        Text(text = spellSlotLevelText, fontSize = 25.sp, modifier = Modifier.weight(1f))

        if (!isCantrip && currentAmountAtLevel != 0) {
            MinusIcon(action = { viewModel.minusSpellSlotAtLevel(slotLevel) })
        }

        val amountAtLevel =
            if (isCantrip) "$amountOfKnownSpells / $defaultAmountAtLevel" else "$currentAmountAtLevel"

        Text(
            text = amountAtLevel,
            fontSize = 30.sp,
            modifier = Modifier.padding(start = 10.dp, end = 5.dp)
        )

        val isMaxSpellSlots = currentAmountAtLevel != defaultAmountAtLevel
        val showPlusIcon = !isCantrip && isMaxSpellSlots
        val spacerWidth = if (!isCantrip) 30.dp else 5.dp

        if (showPlusIcon) {
            PlusIcon(action = { viewModel.plusSpellSlotAtLevel(slotLevel) })
        } else {
            Spacer(modifier = Modifier.width(spacerWidth))
        }
    }
}


@Composable
private fun PlusIcon(action: () -> Unit) {
    IconButton(
        onClick = action,
        modifier = Modifier.size(30.dp)
    ) {
        Icon(
            Icons.Default.Add,
            contentDescription = null
        )
    }
}

@Composable
private fun MinusIcon(action: () -> Unit, modifier: Modifier = Modifier) {
    IconButton(
        onClick = action,
        modifier = modifier.size(30.dp)
    ) {
        Icon(
            painter = painterResource(id = R.drawable.minus),
            contentDescription = null
        )
    }
}

@Composable
private fun ButtonToAddSpellsOfLevel(level: Int, action: () -> Unit) {
    Button(
        onClick = action,
        modifier = Modifier.fillMaxWidth()
    ) {
        val buttonPrompt = if (level == 0) "Add cantrips" else "Add level $level spell"

        Text(buttonPrompt)
    }
}

@Composable
private fun ShowSpellsColumn(
    knownSpellsOfLevel: List<Spell>,
    actionOnSpellClick: (Spell) -> Unit,
    actionOnDeleteSpell: (Spell) -> Unit,
) {
    Column {
        knownSpellsOfLevel.forEach { spell ->
            ItemForList(
                spell = spell,
                modifier = Modifier.clickable {
                    actionOnSpellClick(spell)
                },
                showLevel = false,
                action = { actionOnDeleteSpell(spell) }
            )

            Divider()
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