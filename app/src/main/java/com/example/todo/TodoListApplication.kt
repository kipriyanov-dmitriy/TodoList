package com.example.todo

import android.app.Application
import com.example.todo.di.todoListModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

open class TodoListApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@TodoListApplication)
            modules(todoListModule)
        }
    }
}