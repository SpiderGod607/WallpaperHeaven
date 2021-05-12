package com.spidergod.wallpaperheaven.ui.presentation.common

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp

@Composable
fun TwoChipRow(
    chip1: String,
    chip2: String,
    onClick: (String) -> Unit,
    isEnabled: (String) -> Boolean
) {
    Row {
        FilterChips(
            name = chip1,
            isEnabled = isEnabled(chip1.toLowerCase()),
            modifier = Modifier.clip(
                RoundedCornerShape(
                    topStart = 12.dp,
                    bottomStart = 12.dp
                )
            )
        ) {
            onClick(chip1.toLowerCase())
        }

        FilterChips(
            name = chip2,
            isEnabled = isEnabled(chip2.toLowerCase()),
            modifier = Modifier.clip(
                RoundedCornerShape(
                    topEnd = 12.dp,
                    bottomEnd = 12.dp
                )
            )
        ) {
            onClick(chip2.toLowerCase())
        }

    }
}


@Composable
fun ThreeChipRow(
    chip1: String,
    chip2: String,
    chip3: String,
    onClick: (String) -> Unit,
    isEnabled: (String) -> Boolean
) {
    Row(
        modifier = Modifier.fillMaxWidth()
    ) {
        FilterChips(
            name = chip1,
            isEnabled = isEnabled(chip1.toLowerCase()),
            modifier = Modifier
                .clip(
                    RoundedCornerShape(
                        topStart = 12.dp,
                        bottomStart = 12.dp
                    )
                )
                .weight(1f)
        ) {
            onClick(chip1.toLowerCase())

        }

        FilterChips(
            name = chip2,
            isEnabled = isEnabled(chip2.toLowerCase()),
            modifier = Modifier.weight(1f)
        ) {
            onClick(chip2.toLowerCase())
        }

        FilterChips(
            name = chip3,
            isEnabled = isEnabled(chip3.toLowerCase()),
            modifier = Modifier
                .clip(
                    RoundedCornerShape(
                        topEnd = 12.dp,
                        bottomEnd = 12.dp
                    )
                )
                .weight(1f)
        ) {
            onClick(chip3.toLowerCase())
        }
    }
}

@Composable
fun FilterChips(
    name: String,
    isEnabled: Boolean,
    modifier: Modifier,
    onClick: (String) -> Unit
) {

    val color: Color by
    animateColorAsState(targetValue = if (isEnabled) MaterialTheme.colors.primaryVariant else Color.Gray)

    Surface(
        color = color,
        modifier = modifier.clickable { onClick(name) }
    ) {
        Text(
            text = name,
            color = Color.White,
            modifier = Modifier.padding(10.dp),
            textAlign = TextAlign.Center,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}
