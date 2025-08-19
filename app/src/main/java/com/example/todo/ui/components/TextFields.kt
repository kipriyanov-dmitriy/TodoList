package com.example.todo.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier

@Composable
fun DescriptionTextField() {
    var text by rememberSaveable { mutableStateOf("") }
    var isError by rememberSaveable { mutableStateOf(false) }
    val charLimit = 300
    var currentLength by rememberSaveable { mutableIntStateOf(0) }


    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth(),
        value = text,
        minLines = 10,
        maxLines = 10,
        onValueChange = {
            currentLength = it.length
            isError = currentLength > charLimit
            if (!isError) text = it
        },
        label = {
            Text(if (isError) "Описание*" else "Описание")
        },
        supportingText = {
            Row {
                Spacer(Modifier.weight(1f))
                Text("лимит $currentLength/$charLimit")
            }
        },
        isError = isError
    )
}

@Composable
fun TitleTextField() {
    var text by rememberSaveable { mutableStateOf("") }
    var isError by rememberSaveable { mutableStateOf(false) }
    val charLimit = 30
    var currentLength by rememberSaveable { mutableIntStateOf(0) }


    OutlinedTextField(
        modifier = Modifier.fillMaxWidth(),
        singleLine = true,
        value = text,
        onValueChange = {
            currentLength = it.length
            isError = currentLength > charLimit
            if (!isError) text = it
        },
        label = {
            Text(if (isError) "Заголовок*" else "Заголовок")
        },
        supportingText = {
            Row {
                Spacer(Modifier.weight(1f))
                Text("лимит $currentLength/$charLimit")
            }
        },
        isError = isError
    )
}