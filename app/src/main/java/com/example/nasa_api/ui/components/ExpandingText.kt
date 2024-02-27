package com.example.nasa_api.ui.components

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp

@Composable
fun ExpandingText(
    modifier: Modifier = Modifier,
    text: String
) {
    // Creating a boolean value for
    // storing expanded state
    var showMore by rememberSaveable { mutableStateOf(false) }

    // Creating a clickable modifier
    // that consists text
    Column(
        modifier = Modifier
        .animateContentSize(animationSpec = tween(100))
        .clickable(
            interactionSource = remember { MutableInteractionSource() },
            indication = null
        ) { showMore = !showMore }
        .padding(20.dp)
    ) {
        // if showMore is true, the Text will expand
        // Else Text will be restricted to 3 Lines of display
        if (showMore) {
            Text(text = text)
        } else {
            Text(
                text = text,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}
