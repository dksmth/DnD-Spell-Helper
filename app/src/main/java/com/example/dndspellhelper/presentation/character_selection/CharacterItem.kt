package com.example.dndspellhelper.presentation.character_selection

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dndspellhelper.models.PlayerCharacter

@Composable
fun CharacterItem(playerCharacter: PlayerCharacter, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp)
            .height(75.dp),

        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = playerCharacter.name,
            fontSize = 24.sp,
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
            text = playerCharacter.characterClass,
            fontSize = 18.sp,
            modifier = Modifier
                .weight(1f)
        )

        Text(
            text = "level",
        )

        Spacer(Modifier.width(10.dp))

        Text(
            text = playerCharacter.level.toString(),
            fontSize = 33.sp
        )
    }
}