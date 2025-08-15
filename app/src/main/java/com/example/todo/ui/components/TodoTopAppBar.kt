package com.example.todo.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Build
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoTopAppBar() {
    TopAppBar(
        title = { },
        navigationIcon = {
            var expanded by remember { mutableStateOf(false) }
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .wrapContentSize(Alignment.TopStart)
            ) {
                IconButton(onClick = { expanded = true }) {
                    Icon(imageVector = Icons.Filled.Menu, contentDescription = "Menu")
                }
                DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                    DropdownMenuItem(
                        text = { Text("Ключ на 15") },
                        onClick = { /* Handle edit! */ },
                        leadingIcon = { Icon(Icons.Outlined.Build, contentDescription = null) },
                    )
                    DropdownMenuItem(
                        text = { Text("Настройки") },
                        onClick = { /* чёт делаем когда нажимаем на кнопку меню  */ },
                        leadingIcon = { Icon(Icons.Outlined.Settings, contentDescription = null) },
                    )
                    HorizontalDivider()
                    DropdownMenuItem(
                        text = { Text("Плюсик") },
                        onClick = { /* чёт делаем когда нажимаем на кнопку меню */ },
                        leadingIcon = { Icon(Icons.Outlined.Add, contentDescription = null) },
                    )
                }
            }
        },
        actions = {
            IconButton(onClick = {/* тут будет кнопка удаления которая становится галочкой в момент выбора айтемов */ }) {
                Icon(
                    imageVector = Icons.Filled.Delete, contentDescription = "Delete"
                )
            }
        }
    )
}