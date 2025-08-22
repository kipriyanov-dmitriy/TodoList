package com.example.todo.ui.todo_list_screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun InProgressContent() {
    Box(Modifier.fillMaxSize()) {
        Text("Экран В работе", modifier = Modifier.align(Alignment.Center))
    }
}