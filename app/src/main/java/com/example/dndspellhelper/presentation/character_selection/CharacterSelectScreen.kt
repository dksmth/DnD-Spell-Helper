package com.example.dndspellhelper.presentation.character_selection

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Divider
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.dndspellhelper.models.Character
import com.example.dndspellhelper.ui.theme.DnDSpellHelperTheme

@Composable
fun CharacterSelectScreen(navController: NavController) {

    DnDSpellHelperTheme() {
        val character = remember {
            mutableStateOf(Character())
        }

        var showDialog by remember {
            mutableStateOf(false)
        }

        Column(Modifier.padding(top = 10.dp)) {

            OutlinedButton(
                onClick = { showDialog = true },
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent,),
                border = BorderStroke(1.dp, Color(0xFF2196F3)),
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(
                    text = "Create new Character",
                    fontSize = 20.sp,
                    color = Color(0xFF2196F3)
                )
            }

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                item {
                    CharacterItem(character)
                }
            }
        }
    }
}

@Composable
private fun CharacterItem(character: MutableState<Character>) {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp)
            .height(60.dp),

        verticalAlignment = CenterVertically,
    ) {
        Text(
            text = character.value.name,
            fontSize = 20.sp,
        )

        Spacer(modifier = Modifier.width(10.dp))

        Divider(
            color = Color.White,
            modifier = Modifier
                .height(15.dp)
                .width(1.dp)
        )

        Spacer(modifier = Modifier.width(10.dp))


        Text(
            text = character.value.characterClass,
            modifier = Modifier
                .weight(1f)
        )

        Text(
            text = "level",
        )

        Spacer(Modifier.width(10.dp))

        Text(
            text = character.value.level,
            fontSize = 30.sp
        )
    }
}


@Preview
@Composable
fun Preview() {
    CharacterSelectScreen(navController = rememberNavController())
}