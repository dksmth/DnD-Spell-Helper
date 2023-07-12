package com.example.dndspellhelper.presentation.filter_spells

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ExposedDropdownMenuBox
import androidx.compose.material.ExposedDropdownMenuDefaults
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.Scaffold
import androidx.compose.material.Switch
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.text.isDigitsOnly
import androidx.navigation.NavController
import com.example.dndspellhelper.presentation.spell_list.SpellListViewModel
import com.example.dndspellhelper.ui.theme.DnDSpellHelperTheme

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun FilterSpellsScreen(navController: NavController, viewModel: SpellListViewModel) {

    DnDSpellHelperTheme {
        val levelOfSpell by viewModel.levelOfSpell.collectAsState()

        val classes = arrayOf("Any", "Bard", "Wizard", "Sorcerer")
        var isClassesMenuExpanded by remember { mutableStateOf(false) }
        val selectedClass by viewModel.classOfSpell.collectAsState()

        val castingTime =
            arrayOf(
                "Any",
                "1 action",
                "1 bonus action",
                "1 hour",
                "1 minute",
                "10 minutes",
                "Other"
            )
        var isCastTimeMenuExpanded by remember { mutableStateOf(false) }
        val selectedCastTime by viewModel.castingTime.collectAsState()

        val ritual by viewModel.isRitual.collectAsState()

        Scaffold(topBar = {
            TopAppBar(Modifier.fillMaxWidth()) {

                Spacer(modifier = Modifier.weight(1f))

                IconButton(
                    onClick = {
                        navController.popBackStack(
                            route = "spell_list",
                            inclusive = false
                        )
                    }
                ) {
                    Icon(imageVector = Icons.Default.Check, contentDescription = null)
                }
            }
        },
            content = { it ->
                Column(
                    modifier = Modifier
                        .padding(it)
                        .padding(10.dp)
                        .fillMaxSize()
                ) {
                    Row(
                        modifier = Modifier
                            .height(50.dp)
                            .fillMaxWidth(), verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = "Level of spell", modifier = Modifier.weight(1f))

                        LevelFilterTextField(
                            value = levelOfSpell.first,
                            onInputChanged = {
                                viewModel.onLevelChange(it..levelOfSpell.last)
                            }
                        )

                        Spacer(modifier = Modifier.width(10.dp))

                        Text("-")

                        Spacer(modifier = Modifier.width(10.dp))

                        LevelFilterTextField(
                            value = levelOfSpell.last,
                            onInputChanged = {
                                viewModel.onLevelChange(levelOfSpell.first..it)
                            }
                        )

                        Spacer(Modifier.width(10.dp))
                    }

                    Spacer(Modifier.height(20.dp))

                    RowWithDropDownMenu(
                        text = "Class",
                        isMenuExpanded = isClassesMenuExpanded,
                        actionOnExpandedChange = { isClassesMenuExpanded = !isClassesMenuExpanded },
                        selectedItem = selectedClass,
                        onDismissRequest = { isClassesMenuExpanded = false },
                        onClickAction = { item: String ->
                            viewModel.onClassChange(item)
                            isClassesMenuExpanded = false
                        },
                        dropdownMenuItems = classes
                    )

                    Spacer(Modifier.height(20.dp))

                    RowWithDropDownMenu(
                        text = "Casting time",
                        isMenuExpanded = isCastTimeMenuExpanded,
                        actionOnExpandedChange = {
                            isCastTimeMenuExpanded = !isCastTimeMenuExpanded
                        },
                        selectedItem = selectedCastTime,
                        onDismissRequest = { isCastTimeMenuExpanded = false },
                        onClickAction = { item: String ->
                            viewModel.onCastTimeChange(item)
                            isCastTimeMenuExpanded = false
                        },
                        dropdownMenuItems = castingTime
                    )

                    Spacer(Modifier.height(20.dp))

                    Row(modifier = Modifier.fillMaxWidth()) {
                        Text("Ritual", modifier = Modifier.weight(1f))

                        Switch(
                            checked = ritual,
                            onCheckedChange = { viewModel.onRitualChange(it) }
                        )
                    }
                }
            }
        )
    }
}

@Composable
@OptIn(ExperimentalMaterialApi::class)
fun RowWithDropDownMenu(
    text: String,
    isMenuExpanded: Boolean,
    actionOnExpandedChange: () -> Unit,
    selectedItem: String,
    onDismissRequest: () -> Unit,
    dropdownMenuItems: Array<String>,
    onClickAction: ((String) -> Unit),
) {
    Row(
        modifier = Modifier
            .height(60.dp)
            .fillMaxWidth(), verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = text, modifier = Modifier.weight(1f))

        Box(
            modifier = Modifier
                .width(200.dp)
        ) {
            ExposedDropdownMenuBox(
                expanded = isMenuExpanded,
                onExpandedChange = { actionOnExpandedChange() }
            ) {
                TextField(
                    value = selectedItem,
                    placeholder = { Text("Any") },
                    onValueChange = {},
                    readOnly = true,
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(
                            expanded = isMenuExpanded
                        )
                    },
                )

                ExposedDropdownMenu(
                    expanded = isMenuExpanded,
                    onDismissRequest = { onDismissRequest() }
                ) {
                    dropdownMenuItems.forEach { item ->
                        DropdownMenuItem(
                            content = { Text(text = item) },
                            onClick = { onClickAction(item) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun LevelFilterTextField(value: Int, onInputChanged: (Int) -> Unit) {

    val string = if (value != 0) value.toString() else ""

    TextField(
        modifier = Modifier
            .width(80.dp)
            .height(50.dp),

        value = string,
        onValueChange = {
            if (it.isDigitsOnly()) {
                val intValue = if (it == "") 0 else it.toInt()

                if (intValue < 10) {
                    onInputChanged(intValue)
                }
            }
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
        textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.Center),

        shape = RoundedCornerShape(8.dp),
        colors = TextFieldDefaults.textFieldColors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        )
    )
}