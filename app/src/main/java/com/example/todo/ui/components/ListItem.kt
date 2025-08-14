package com.example.todo.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.todo.core.PreviewPhone
import com.example.todo.theme.Typography
import com.example.todo.ui.theme.TodoTheme

@Composable
fun ListItem(
    text: String,
    onClick: () -> Unit,
) {
    Card(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(6.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            Text(
                text = text,
                style = Typography.bodyLarge
            )
        }
    }
}

@Composable
@PreviewPhone
private fun ListItemPreview() {
    TodoTheme {
        ListItem(
            onClick = {},
            text = "Hello World"
        )

    }
}