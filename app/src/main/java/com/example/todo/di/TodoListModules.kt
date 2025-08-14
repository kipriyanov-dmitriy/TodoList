package com.example.todo.di

import com.example.todo.ui.todo_list_screen.TodoListViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val presentationModule = module {
    viewModel { TodoListViewModel() }
}

val todoListModule = module {
    includes(
        presentationModule
    )
}