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
import com.example.dndspellhelper.presentation.character_selection.CharacterSelectScreen
import com.example.dndspellhelper.presentation.spell_info.SpellInfoScreen
import com.example.dndspellhelper.presentation.spell_list.ActivityViewModel
import com.example.dndspellhelper.presentation.spell_list.SpellList
import com.example.dndspellhelper.ui.theme.DnDSpellHelperTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: ActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.getEverything()

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
                                SpellList(navController, viewModel = viewModel)
                            }
                            composable(BottomBarScreen.Characters.route) {
                                CharacterSelectScreen(navController = navController)
                            }
                            composable("spell_info") {
                                SpellInfoScreen(myViewModel = viewModel)
                            }
                        }

                    }
                )

            }

        }
    }
}




