package com.example.todo.ui.todo_list_screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.todo.core.PreviewPhone
import com.example.todo.ui.components.AcceptButton
import com.example.todo.ui.components.AddedNoteDate
import com.example.todo.ui.components.DescriptionTextField
import com.example.todo.ui.components.PriorityItem
import com.example.todo.ui.components.TitleTextField
import com.example.todo.ui.theme.TodoTheme

@Composable
fun BacklogScreen(onAddNoteClick: () -> Unit) {
    Box(Modifier.fillMaxSize()) {
        Text("Список бэклога", modifier = Modifier.align(Alignment.Center))

        FloatingActionButton(
            onClick = onAddNoteClick,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
        ) {
            Icon(Icons.Default.Add, contentDescription = "Добавить")
        }
    }
}