package com.example.dndspellhelper.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.dndspellhelper.presentation.bottom_navigation.BottomBar
import com.example.dndspellhelper.presentation.bottom_navigation.BottomBarScreen
import com.example.dndspellhelper.presentation.character_info.CharacterInfoScreen
import com.example.dndspellhelper.presentation.character_selection.CharacterSelectScreen
import com.example.dndspellhelper.presentation.character_selection.CharactersViewModel
import com.example.dndspellhelper.presentation.spell_info.SpellInfoScreen
import com.example.dndspellhelper.presentation.spell_list.ActivityViewModel
import com.example.dndspellhelper.presentation.spell_list.SpellList
import com.example.dndspellhelper.ui.theme.DnDSpellHelperTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val spellsViewModel: ActivityViewModel by viewModels()
    private val charactersViewModel: CharactersViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        spellsViewModel.getEverything()
        charactersViewModel.getAllCharacters()

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
                                SpellList(navController, spellsViewModel)
                            }
                            composable(BottomBarScreen.Characters.route) {
                                CharacterSelectScreen(
                                    navController,
                                    charactersViewModel
                                )
                            }
                            composable("spell_info") {
                                SpellInfoScreen(spellsViewModel)
                            }
                            composable("character_info") {
                                CharacterInfoScreen(
                                    navController, charactersViewModel
                                )
                            }
                            composable("pick_spells") {

                            }
                        }
                    }
                )
            }
        }
    }
}




