package com.example.dndspellhelper.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.clickable
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Divider
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.dndspellhelper.presentation.spell_info.SpellInfoScreen
import com.example.dndspellhelper.ui.theme.DnDSpellHelperTheme
import me.saket.swipe.SwipeAction
import me.saket.swipe.SwipeableActionsBox
import androidx.compose.foundation.lazy.items
import com.example.dndspellhelper.presentation.spell_list.ActivityViewModel
import com.example.dndspellhelper.presentation.spell_list.ItemForList
import com.example.dndspellhelper.presentation.spell_list.SpellList
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

                NavHost(navController = navController, startDestination = "spell_list") {
                    composable("spell_list") {
                        SpellList(navController, viewModel = viewModel)
                    }
                    composable("spell_info") {
                        SpellInfoScreen(myViewModel = viewModel)
                    }
                }
            }
        }
    }
}








