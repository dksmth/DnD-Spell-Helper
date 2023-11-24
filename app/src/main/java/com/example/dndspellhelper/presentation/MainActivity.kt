package com.example.dndspellhelper.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.dndspellhelper.presentation.bottom_navigation.BottomBar
import com.example.dndspellhelper.presentation.bottom_navigation.BottomBarScreen
import com.example.dndspellhelper.presentation.character_info.AddSpell
import com.example.dndspellhelper.presentation.character_info.CharacterInfoScreen
import com.example.dndspellhelper.presentation.character_info.PickSpells
import com.example.dndspellhelper.presentation.character_selection.CharacterSelectScreen
import com.example.dndspellhelper.presentation.character_selection.CharactersViewModel
import com.example.dndspellhelper.presentation.filter_spells.FilterSpellsScreen
import com.example.dndspellhelper.presentation.spell_info.SpellInfoScreen
import com.example.dndspellhelper.presentation.spell_list.SpellList
import com.example.dndspellhelper.presentation.spell_list.SpellListViewModel
import com.example.dndspellhelper.ui.theme.DnDSpellHelperTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    // private val spellsViewModel: ActivityViewModel by viewModels()
    private val charactersViewModel: CharactersViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            DnDSpellHelperTheme {

                val navController = rememberNavController()

                Scaffold(
                    bottomBar = { BottomBar(navController = navController) },
                    content = { padding ->

                        NavHost(
                            navController = navController,
                            startDestination = SPELL_LIST_ROUTE,
                            modifier = Modifier.padding(padding)
                        ) {
                            composable(SPELL_LIST_ROUTE) {
                                SpellList(navController)
                            }
                            composable(BottomBarScreen.Characters.route) {
                                CharacterSelectScreen(navController, charactersViewModel)
                            }
                            composable(SPELL_INFO_ROUTE) { navBackStackEntry ->
                                val parentEntry = remember(navBackStackEntry) {
                                    navController.getBackStackEntry(SPELL_LIST_ROUTE)
                                }
                                val parentViewModel = hiltViewModel<SpellListViewModel>(parentEntry)

                                SpellInfoScreen(parentViewModel.chosenSpell)
                            }
                            composable(SPELL_FILTER_ROUTE) { navBackStackEntry ->
                                val parentEntry = remember(navBackStackEntry) {
                                    navController.getBackStackEntry(SPELL_LIST_ROUTE)
                                }
                                val parentViewModel = hiltViewModel<SpellListViewModel>(parentEntry)

                                FilterSpellsScreen(navController, parentViewModel)
                            }
                            composable(CHARACTER_INFO_ROUTE) {
                                CharacterInfoScreen(navController, charactersViewModel)
                            }
                            composable(PICK_SPELLS_ROUTE) {
                                PickSpells(navController, charactersViewModel)
                            }
                            composable(SPELL_INFO_FROM_CHARACTER_ROUTE) {
                                AddSpell(navController, charactersViewModel)
                            }
                        }
                    }
                )
            }
        }
    }

    companion object {
        const val SPELL_LIST_ROUTE = "spell_list"
        const val SPELL_INFO_ROUTE = "spell_info"
        const val SPELL_FILTER_ROUTE = "filter_spell"
        const val CHARACTER_INFO_ROUTE = "character_info"
        const val PICK_SPELLS_ROUTE = "pick_spells"
        const val SPELL_INFO_FROM_CHARACTER_ROUTE = "spell_info_from_character"
    }
}




