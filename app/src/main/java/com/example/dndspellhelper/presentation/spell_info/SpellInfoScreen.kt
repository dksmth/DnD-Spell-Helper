package com.example.dndspellhelper.presentation.spell_info

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.material.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dndspellhelper.R
import com.example.dndspellhelper.models.Spell
import com.example.dndspellhelper.ui.theme.DnDSpellHelperTheme


@Composable
fun SpellInfoScreen(
    spell: Spell
) {
    DnDSpellHelperTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 7.dp, end = 10.dp, top = 20.dp, bottom = 10.dp)
                .verticalScroll(rememberScrollState(0))
        ) {
            Row {
                Text(
                    text = spell.name,
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .weight(1f)
                )

                if (spell.concentration) {
                    Image(
                        painter = painterResource(id = R.drawable.concentration_icon),
                        contentDescription = null,
                        modifier = Modifier
                            .size(35.dp)
                            .align(Alignment.CenterVertically)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                }

                if (spell.ritual) {
                    Image(
                        painter = painterResource(id = R.drawable.ritual_icon),
                        contentDescription = null,
                        modifier = Modifier
                            .size(35.dp)
                            .align(Alignment.CenterVertically)
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            val spellLevelString = if (spell.level == 0) "Cantrip" else "${spell.level} level"

            Text(
                text = spellLevelString,
                fontStyle = FontStyle.Italic,
                fontSize = 20.sp,
            )

            Spacer(modifier = Modifier.height(15.dp))

            MultipleRowsWithStuff(
                mapOfTitlesAndValues = mapOf(
                    "Casting time" to spell.casting_time,
                    "Range" to spell.range,
                    "Components" to spell.components.joinToString(separator = ", ")
                ))

            if (spell.material != null) {
                RowWithBoldFirstText(firstString = "Materials", secondString = spell.material)
            }

            MultipleRowsWithStuff(
                mapOfTitlesAndValues = mapOf(
                    "Duration" to spell.duration,
                    "Classes" to (spell.classNames?.joinToString(separator = ", ") ?: ""),
                    "Subclasses" to "TODO"
                ))

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = spell.desc.joinToString(separator = "\n\n") { it.removeSurrounding("\"") },
                modifier = Modifier
                    .padding(start = 2.dp, end = 10.dp)
            )
        }
    }

}

@Composable
fun MultipleRowsWithStuff(
    mapOfTitlesAndValues: Map<String, String>,
) {
    mapOfTitlesAndValues.forEach { (title, value) ->
        RowWithBoldFirstText(firstString = title, secondString = value)
    }
}


@Composable
fun RowWithBoldFirstText(
    firstString: String,
    secondString: String,
) {
    Row {
        Text(
            text = "$firstString:",
            fontWeight = FontWeight.Bold,
        )

        Spacer(Modifier.width(5.dp))

        Text(
            text = secondString
        )
    }

    Spacer(modifier = Modifier.height(5.dp))
}
