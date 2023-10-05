package com.example.dndspellhelper.presentation.character_selection

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.FabPosition
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import com.example.dndspellhelper.presentation.MainActivity.Companion.CHARACTER_INFO_ROUTE
import com.example.dndspellhelper.presentation.filter_spells.RowWithDropDownMenu
import com.example.dndspellhelper.ui.theme.DnDSpellHelperTheme



@Composable
fun CharacterSelectScreen(navController: NavController, viewModel: CharactersViewModel) {

    DnDSpellHelperTheme {
        val characters by viewModel.allCharacters.collectAsState()

        var showDialog by remember { mutableStateOf(false) }

        Scaffold(
            floatingActionButton = {
                FloatingActionButton(
                    onClick = { showDialog = true },
                    backgroundColor = Color(0xFF2196F3),
                    contentColor = Color.White
                ) {
                    Icon(Icons.Filled.Add, contentDescription = "Add character")
                }
            }, floatingActionButtonPosition = FabPosition.End
        ) {

            if (showDialog) {
                NewCharacterCreationDialog(onDismiss = { showDialog = false }, viewModel)
            }

            Column(
                Modifier
                    .padding(it)
                    .padding(top = 10.dp)
            ) {
                LazyColumn(Modifier.fillMaxSize()) {
                    items(characters) { character ->
                        CharacterItem(character,
                            Modifier
                                .clickable {
                                    viewModel.setCharacter(character)
                                    navController.navigate(CHARACTER_INFO_ROUTE)
                                }
                        )

                        Divider()
                    }
                }
            }
        }
    }
}


@Composable
fun NewCharacterCreationDialog(
    onDismiss: () -> Unit,
    viewModel: CharactersViewModel,
) {
    var name by rememberSaveable { mutableStateOf("") }

    val classes = arrayOf("Bard", "Wizard", "Sorcerer")
    var expanded by rememberSaveable { mutableStateOf(false) }
    var selectedClass by rememberSaveable { mutableStateOf("") }

    var level by rememberSaveable{ mutableStateOf("") }
    var attackModifier by rememberSaveable { mutableStateOf("") }
    var spellSaveDiff by rememberSaveable { mutableStateOf("") }
    var abilityModifier by rememberSaveable { mutableStateOf("") }

    Dialog(onDismissRequest = onDismiss) {
        Card(shape = RoundedCornerShape(20.dp)) {
            Column(
                modifier = Modifier
                    .wrapContentSize()
                    .padding(16.dp)
            ) {

                Text(
                    text = "New Character",
                    fontSize = 20.sp,
                    modifier = Modifier.padding(bottom = 20.dp)
                )

                Row(verticalAlignment = Alignment.CenterVertically) {

                    Text(text = "Name", fontSize = 16.sp)

                    Spacer(Modifier.width(10.dp))

                    TextField(
                        value = name,
                        onValueChange = { name = it.replace("\n", "") },
                        placeholder = { Text("Name") }
                    )
                }

                Spacer(Modifier.height(10.dp))

                RowWithDropDownMenu(
                    text = "Class",
                    isMenuExpanded = expanded,
                    actionOnExpandedChange = { expanded = !expanded },
                    selectedItem = selectedClass,
                    onDismissRequest = { expanded = false },
                    dropdownMenuItems = classes,
                    onClickAction = { item ->
                        selectedClass = item
                        expanded = false
                    }
                )

                Spacer(Modifier.height(10.dp))

                CharacterInfoInputRow(
                    text = "Character level",
                    textFieldValue = level,
                    textFieldAction = { level = it.take(2) }
                )

                Spacer(Modifier.height(10.dp))

                CharacterInfoInputRow(
                    text = "Spell attack modifier",
                    textFieldValue = attackModifier,
                    textFieldAction = { attackModifier = it.take(2) }
                )

                Spacer(Modifier.height(10.dp))

                CharacterInfoInputRow(
                    text = "Spell save DC",
                    textFieldValue = spellSaveDiff,
                    textFieldAction = {
                        spellSaveDiff = it.take(2)
                    }
                )

                Spacer(Modifier.height(10.dp))

                CharacterInfoInputRow(
                    text = "Spellcasting ability modifier",
                    textFieldValue = abilityModifier,
                    textFieldAction = {
                        abilityModifier = it.take(2)
                    }
                )

                Spacer(Modifier.height(20.dp))

                Row(horizontalArrangement = Arrangement.SpaceBetween) {

                    TextButton(onClick = onDismiss) {
                        Text(text = "Cancel", color = Color(0xFFCC7575), fontSize = 18.sp)
                    }

                    Spacer(Modifier.weight(1f))

                    val isInputValidated =
                        canBeSaved(
                            name,
                            selectedClass,
                            level,
                            attackModifier,
                            spellSaveDiff,
                            abilityModifier
                        )

                    val nameIsValid = viewModel.nameNotInCharacterNames(name)

                    val canBeSaved = isInputValidated && nameIsValid

                    TextButton(
                        onClick = {
                            if (canBeSaved) {
                                viewModel.createNewCharacter(
                                    name,
                                    selectedClass,
                                    level.toInt(),
                                    attackModifier.toInt(),
                                    spellSaveDiff.toInt(),
                                    abilityModifier.toInt()
                                )
                                onDismiss()
                            }
                        },
                    ) {
                        val color = if (canBeSaved) Color(0xFF2196F3) else Color.Gray

                        Text(text = "Create Character", color = color, fontSize = 18.sp)
                    }
                }
            }
        }
    }
}


@Composable
fun CharacterInfoInputRow(
    text: String,
    textFieldValue: String,
    textFieldAction: (String) -> Unit,
) {
    val pattern = remember { Regex("^\\d+\$") }

    Row(verticalAlignment = Alignment.CenterVertically) {

        Text(text = text, fontSize = 16.sp)

        Spacer(Modifier.weight(1f))

        TextField(
            value = textFieldValue,
            onValueChange = {
                if (it.isEmpty() || it.matches(pattern)) {
                    textFieldAction(it)
                }
            },

            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
            textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.Center),

            modifier = Modifier.width(60.dp)
        )
    }
}

private fun canBeSaved(
    name: String,
    selectedClass: String,
    level: String,
    attackModifier: String,
    spellSaveDiff: String,
    abilityModifier: String,
): Boolean {
    return listOf(
        name,
        selectedClass,
        level,
        attackModifier,
        spellSaveDiff,
        abilityModifier
    ).none { it == "" }
            && level.toInt() in 1..MAX_LEVEL
            && attackModifier.toInt() in 1..MAX_ATTACK_MOD
            && spellSaveDiff.toInt() in 1..MAX_SPELLSAVE_MOD
            && abilityModifier.toInt() in 1..MAX_ABILITY_MOD

}

const val MAX_LEVEL = 20
const val MAX_ATTACK_MOD = 100
const val MAX_SPELLSAVE_MOD = 100
const val MAX_ABILITY_MOD = 100

