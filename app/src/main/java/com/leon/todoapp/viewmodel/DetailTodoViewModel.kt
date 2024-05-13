package com.leon.todoapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.leon.todoapp.model.Todo
import com.leon.todoapp.model.TodoDatabase
import com.leon.todoapp.util.buildDb
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class DetailTodoViewModel (application: Application)
    : AndroidViewModel(application), CoroutineScope {
    private val job = Job()

    val todoLD = MutableLiveData<Todo>()

    fun addTodo(todo:Todo){
        launch {
            val db = buildDb(getApplication())
            db.todoDao().insertAll(todo)
        }
    }

    fun fetch(uuid:Int){
        launch{
            val db = buildDb(getApplication())
            todoLD.postValue(db.todoDao().selectTodo(uuid))
        }
    }

    override val coroutineContext: CoroutineContext
        get() = job +Dispatchers.IO
}