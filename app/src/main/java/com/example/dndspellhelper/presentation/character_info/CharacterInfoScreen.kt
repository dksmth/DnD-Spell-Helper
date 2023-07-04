package com.example.dndspellhelper.presentation.character_info

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.dndspellhelper.R
import com.example.dndspellhelper.models.PlayerCharacter
import com.example.dndspellhelper.presentation.character_selection.CharactersViewModel
import com.example.dndspellhelper.ui.theme.DnDSpellHelperTheme


@Composable
fun CharacterInfoScreen(navController: NavController, viewModel: CharactersViewModel) {

    val character by viewModel.character.collectAsState()
    val characterSpellCasting by viewModel.characterInfo.collectAsState()

    if (character != null && characterSpellCasting != null) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(top = 30.dp)
                ) {
                    Text(text = character!!.name, fontSize = 30.sp)

                    Spacer(Modifier.height(10.dp))

                    Text(text = character!!.characterClass)
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

            Spacer(Modifier.height(20.dp))

            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier
                    .padding(20.dp)
                    .fillMaxWidth()
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("Attack Modifier")

                    Spacer(Modifier.height(10.dp))

                    Text(
                        text = character!!.attackModifier.toString(),
                    )
                }

                Spacer(Modifier.width(5.dp))

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("Spell DC")

                    Spacer(Modifier.height(10.dp))

                    Text(
                        text = character!!.spellDC.toString(),
                        textAlign = TextAlign.Center
                    )
                }
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("Ability Modifier")

                    Spacer(Modifier.height(10.dp))

                    Text(
                        text = character!!.attackModifier.toString(),
                        textAlign = TextAlign.Center
                    )
                }
            }

            Spacer(Modifier.height(30.dp))

            Column(Modifier.fillMaxSize()) {
                Column() {
                    Row() {
                        Text("Cantrip")

                    }

                    LazyColumn() {

                    }

                    Button(onClick = { /*TODO*/ }, modifier = Modifier.fillMaxWidth()) {
                        Text("Add cantrip")
                    }
                }

                Column() {
                    Column() {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.wrapContentSize()
                        ) {
                            Text("Level1", modifier = Modifier.weight(1f))

                            Image(
                                painter = painterResource(id = R.drawable.concentration_icon),
                                contentDescription = null,
                            )
                        }

                        LazyColumn() {

                        }

                        Button(
                            onClick = {

                            },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Add cantrip")
                        }
                    }
                }

                Column() {
                    Column() {
                        Row() {
                            Text("Cantrip")

                        }

                        LazyColumn() {

                        }

                        Button(onClick = { /*TODO*/ }, modifier = Modifier.fillMaxWidth()) {
                            Text("Add cantrip")
                        }
                    }
                }
            }

        }
    }
}

//@Preview
//@Composable
//fun Preview() {
//    DnDSpellHelperTheme() {
//        CharacterInfoScreen()
//    }
//}