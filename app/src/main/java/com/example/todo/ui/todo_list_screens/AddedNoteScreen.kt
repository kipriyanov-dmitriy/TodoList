package com.example.todo.ui.todo_list_screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
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
fun AddNoteScreen(onNoteAdded: () -> Unit) {
    AddedNoteContent(
        onBackPressed = onNoteAdded
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AddedNoteContent(
    onBackPressed: () -> Unit,
){
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Новая заметка") },
                navigationIcon = {
                    IconButton(onClick = onBackPressed) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Назад"
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 8.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            TitleTextField()
            DescriptionTextField()
            PriorityItem { priority ->
                //TODO(Затолкать приоритет в стейт когда он появится, чтоб прокинуть в БД)
            }
            AddedNoteDate()
            AcceptButton {
                //TODO(Реакция на клик кнопки сохранить, кладём в БД заметку)
            }
        }
    }
}

@Composable
@PreviewPhone
private fun AddNoteScreenPreview() {
    TodoTheme {
        AddNoteScreen {}
    }
}