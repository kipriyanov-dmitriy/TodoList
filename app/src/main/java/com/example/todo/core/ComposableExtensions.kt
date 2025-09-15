package com.example.todo.core

import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layout
import androidx.compose.ui.unit.Dp

fun Modifier.onSizeMeasuredDp(
    onSize: (width: Dp, height: Dp) -> Unit
): Modifier = this.then(
    Modifier.layout { measurable, constraints ->
        val placeable = measurable.measure(constraints)

        val density = this@layout
        val widthDp = with(density) { placeable.width.toDp() }
        val heightDp = with(density) { placeable.height.toDp() }

        onSize(widthDp, heightDp)

        layout(placeable.width, placeable.height) {
            placeable.place(0, 0)
        }
    }
)