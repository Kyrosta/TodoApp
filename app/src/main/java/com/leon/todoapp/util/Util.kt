package com.leon.todoapp.util

import android.content.Context
import androidx.room.Room
import com.leon.todoapp.model.TodoDatabase

val DB_NAME = "newtododb"

fun buildDb(context: Context):TodoDatabase{
    val db = Room.databaseBuilder(context,TodoDatabase::class.java, DB_NAME).build()
    return db
}