package com.example.todo

import android.app.Application
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ProcessLifecycleOwner
import com.example.todo.core.WallpaperUtils
import com.example.todo.di.todoListModule
import com.example.todo.domain.model.StorageStatus
import com.example.todo.domain.repository.ITodoListRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import org.koin.android.ext.android.getKoin
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

open class TodoListApplication : Application(), LifecycleEventObserver {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@TodoListApplication)
            modules(todoListModule)
        }

        ProcessLifecycleOwner.get().lifecycle.addObserver(this@TodoListApplication)
    }

    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        if (event == Lifecycle.Event.ON_STOP) {
            val repo: ITodoListRepository = getKoin().get()
            CoroutineScope(SupervisorJob() + Dispatchers.Default).launch {
                val tasks = repo.getListOfTasks().first()
                    .filter { it.storageStatus == StorageStatus.IN_PROGRESS }
                    .map { it.title }
                WallpaperUtils.updateWallpaper(this@TodoListApplication, tasks)
            }
        }
    }
}