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
                            startDestination = "spell_list",
                            modifier = Modifier.padding(padding)
                        ) {
                            composable("spell_list") {
                                SpellList(navController)
                            }
                            composable(BottomBarScreen.Characters.route) {
                                CharacterSelectScreen(navController, charactersViewModel)
                            }
                            composable("spell_info") { navBackStackEntry ->
                                val parentEntry = remember(navBackStackEntry) {
                                    navController.getBackStackEntry("spell_list")
                                }
                                val parentViewModel = hiltViewModel<SpellListViewModel>(parentEntry)

                                SpellInfoScreen(parentViewModel.chosenSpell)
                            }
                            composable("filter_spell") { navBackStackEntry ->
                                val parentEntry = remember(navBackStackEntry) {
                                    navController.getBackStackEntry("spell_list")
                                }
                                val parentViewModel = hiltViewModel<SpellListViewModel>(parentEntry)

                                FilterSpellsScreen(navController, parentViewModel)
                            }
                            composable("character_info") {
                                CharacterInfoScreen(navController, charactersViewModel)
                            }
                            composable("pick_spells") {
                                PickSpells(navController, charactersViewModel)
                            }
                            composable("spell_info_from_character") {
                                AddSpell(navController, charactersViewModel)
                            }
                        }
                    }
                )
            }
        }
    }
}




