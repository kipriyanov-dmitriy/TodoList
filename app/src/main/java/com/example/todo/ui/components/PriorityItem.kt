package com.example.todo.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.InputChip
import androidx.compose.material3.InputChipDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.todo.domain.model.BusinessPriority
import com.example.todo.ui.theme.Green
import com.example.todo.ui.theme.Green50
import com.example.todo.ui.theme.Red
import com.example.todo.ui.theme.Red50
import com.example.todo.ui.theme.Yellow
import com.example.todo.ui.theme.Yellow50

enum class Priority(
    val lightColor: Color,
    val darkColor: Color
) {
    Low(Green50, Green),
    Medium(Yellow50, Yellow),
    High(Red50, Red),
}

fun BusinessPriority.getPriority(): Priority =
    when(this){
        BusinessPriority.LOW -> Priority.Low
        BusinessPriority.MEDIUM -> Priority.Medium
        BusinessPriority.HIGH -> Priority.High
    }

@Composable
fun PriorityItem(
    initialPriority: Priority = Priority.Low,
    onPrioritySelected: (Priority) -> Unit
) {
    var selectedPriority by remember { mutableStateOf(initialPriority) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text("Приоритет:")

        Spacer(Modifier.width(8.dp))

        Priority.entries.forEach { priority ->
            PriorityChip(
                priority = priority,
                selected = selectedPriority == priority,
                onClick = {
                    selectedPriority = priority
                    onPrioritySelected(priority)
                }
            )
            Spacer(Modifier.width(4.dp))
        }
    }
}

@Composable
fun PriorityChip(
    priority: Priority,
    selected: Boolean = false,
    onClick: () -> Unit
) {
    InputChip(
        selected = selected,
        onClick = onClick,
        label = { },
        colors = InputChipDefaults.inputChipColors(
            containerColor = priority.lightColor,
            selectedContainerColor = priority.darkColor
        ),
        border = InputChipDefaults.inputChipBorder(
            enabled = selected,
            selected = !selected,
            selectedBorderWidth = 4.dp,
            borderColor = Color.White
        )
    )
}