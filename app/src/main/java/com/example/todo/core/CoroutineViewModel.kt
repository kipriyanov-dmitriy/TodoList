package com.example.todo.core

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlin.coroutines.CoroutineContext

abstract class CoroutineViewModel: ViewModel(), CoroutineScope {
    private val coroutineContextWithExceptionHandler by lazy {
        viewModelScope.coroutineContext + CoroutineExceptionHandler(::errorHandler)
    }

    override val coroutineContext: CoroutineContext
        get() = coroutineContextWithExceptionHandler

    open fun errorHandler(context: CoroutineContext, throwable: Throwable){
        throwable.message?.let {
            Log.d("Coroutine Scope", it)
        }
    }
}