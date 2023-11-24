package com.example.dndspellhelper.presentation.spell_list

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dndspellhelper.R
import com.example.dndspellhelper.models.Spell

@Composable
fun ItemForList(
    spell: Spell,
    modifier: Modifier = Modifier,
    showLevel: Boolean = true,
    action: (() -> Unit) = {},
) {

    val color = if (spell.favourite) Color(0xFFDC95F5) else Color.Black

    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(80.dp)
            .border(width = 2.dp, color = color),
        verticalAlignment = Alignment.CenterVertically,

        ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(start = 10.dp)
        ) {
            Text(
                text = spell.name,
                fontSize = 20.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                color = Color.White
            )
            Text(
                text = spell.casting_time,
                fontStyle = FontStyle.Italic,
                maxLines = 1
            )
            Spacer(modifier = Modifier.width(10.dp))
        }

        Spacer(modifier = Modifier.width(10.dp))

        if (spell.concentration) {
            TypeIconInRow(resource = R.drawable.concentration_icon, Modifier.size(30.dp))
        }

        if (spell.ritual) {
            TypeIconInRow(resource = R.drawable.ritual_icon, Modifier.size(30.dp))
        }

        Text(
            text = spell.components.joinToString(separator = " "),
            fontSize = 18.sp,
            modifier = Modifier
                .width(50.dp)

        )

        Spacer(modifier = Modifier.width(10.dp))

        if (showLevel) {
            Text(
                text = "${spell.level}",
                fontSize = 40.sp,
            )
            Spacer(modifier = Modifier.width(20.dp))
        } else {
            IconButton(
                onClick = { action() },
            ) {
                Icon(
                    Icons.Default.Close,
                    contentDescription = null,
                    modifier = Modifier.size(40.dp)
                )
            }
            Spacer(modifier = Modifier.width(10.dp))
        }


    }
}

@Composable
fun TypeIconInRow(resource: Int, modifier: Modifier = Modifier) {
    Image(
        painter = painterResource(id = resource),
        contentDescription = null,
        modifier = modifier
    )

    Spacer(modifier = Modifier.width(10.dp))
}