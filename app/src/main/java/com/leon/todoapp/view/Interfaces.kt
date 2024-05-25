package com.leon.todoapp.view

import android.view.View
import android.widget.CompoundButton
import com.leon.todoapp.model.Todo

interface TodoCheckedChangeListener {
    fun onCheckedChange(cb: CompoundButton,isCheckedBoolean: Boolean,obj:Todo)
}

interface TodoEditClickListener {
    fun onTodoEditClick(v: View)
}