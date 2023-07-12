package com.example.dndspellhelper.presentation.spell_list

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.IconToggleButton
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.dndspellhelper.R
import me.saket.swipe.SwipeAction
import me.saket.swipe.SwipeableActionsBox

@Composable
fun SpellList(
    navController: NavController,
    viewModel: SpellListViewModel = hiltViewModel(),
) {

    val sortByLevel by viewModel.sortByLevel.collectAsState()
    val showFavourites by viewModel.showFavourites.collectAsState()
    val spells by viewModel.spellNames.collectAsState()
    val searchText by viewModel.searchText.collectAsState()

    Column {

        TopAppBar(
            modifier = Modifier
                .fillMaxWidth()
        ) {

            IconToggleButton(
                checked = sortByLevel,
                onCheckedChange = {
                    viewModel.changeFavouriteState()
                }
            ) {
                Icon(
                    imageVector = if (showFavourites) Icons.Filled.Favorite else Icons.Default.FavoriteBorder,
                    contentDescription = "Icon",
                )
            }

            Spacer(Modifier.width(10.dp))

            TextField(
                value = searchText,
                onValueChange = viewModel::onSearchTextChange,
                modifier = Modifier.weight(1f),
                placeholder = { Text(text = "Search") }
            )

            Spacer(Modifier.width(10.dp))

            SortDropdownMenu(viewModel)

            IconButton(onClick = { navController.navigate("filter_spell") }) {
                Icon(painter = painterResource(id =R.drawable.filter_icon), contentDescription = null)
            }
        }

        LazyColumn {
            items(spells) { spell ->

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
                    color = Color.White,
                    thickness = 1.dp
                )
            }
        }
    }
}

@Composable
private fun SortDropdownMenu(
    viewModel: SpellListViewModel,
) {
    var expanded by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier.wrapContentSize(Alignment.TopEnd)
    ) {

        IconButton(onClick = { expanded = !expanded }) {
            Image(
                painter = painterResource(id = R.drawable.sort),
                contentDescription = "More"
            )
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
        ) {
            DropdownMenuItem(
                onClick = {
                    viewModel.sortByAlphabet()
                    expanded = false
                }
            ) {
                Text(
                    text = "Alphabetically",
                    fontSize = 16.sp,
                )
            }

            DropdownMenuItem(
                onClick = {
                    viewModel.sortByLevel()
                    expanded = false
                }
            ) {
                Text(
                    text = "By level",
                    fontSize = 16.sp,
                )
            }
        }
    }
}