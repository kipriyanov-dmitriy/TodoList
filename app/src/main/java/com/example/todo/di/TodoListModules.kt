package com.example.todo.di

import androidx.room.Room
import com.example.todo.data.db.TaskDatabase
import com.example.todo.data.repository.TodoListRepositoryImpl
import com.example.todo.domain.repository.ITodoListRepository
import com.example.todo.domain.usecases.AddedTaskItemUseCase
import com.example.todo.domain.usecases.GetListOfBacklogTasksUseCase
import com.example.todo.ui.todo_list_screens.viewmodels.AddedTaskViewModel
import com.example.todo.ui.todo_list_screens.viewmodels.InProgressViewModel
import com.example.todo.ui.todo_list_screens.viewmodels.TodoListViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val presentationModule = module {
    viewModel { TodoListViewModel(get()) }
    viewModel { AddedTaskViewModel(get()) }
    viewModel { InProgressViewModel() }
}

val databaseModule = module {
    single {
        Room.databaseBuilder(
            get(),
            TaskDatabase::class.java,
            "backlog_tasks.db"
        )
            .fallbackToDestructiveMigration(true)
            .build()
    }
    single { get<TaskDatabase>().backlogTasksDao() }
    single<ITodoListRepository> { TodoListRepositoryImpl(get()) }
}

val domainModule = module {
    single { AddedTaskItemUseCase(get()) }
    single { GetListOfBacklogTasksUseCase(get()) }
}

val todoListModule = module {
    includes(
        presentationModule,
        databaseModule,
        domainModule
    )
}