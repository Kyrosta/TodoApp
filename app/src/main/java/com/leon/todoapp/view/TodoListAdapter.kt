package com.leon.todoapp.view

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.leon.todoapp.databinding.TodoItemLayoutBinding
import com.leon.todoapp.model.Todo

class TodoListAdapter(val todoList:ArrayList<Todo>,
        val adapterOnClick:(Todo)-> Unit
    )
    :RecyclerView.Adapter<TodoListAdapter.TodoViewHolder>(),TodoCheckedChangeListener,TodoEditClickListener {
        class TodoViewHolder(var binding: TodoItemLayoutBinding)
            :RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        var binding = TodoItemLayoutBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return TodoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
//        holder.binding.checkTask.text = todoList[position].title
//        holder.binding.checkTask.isChecked = false
//        holder.binding.imgEdit.setOnClickListener {
//            val action = TodoListFragmentDirections.actionEditTodo(todoList[position].uuid)
//            Navigation.findNavController(it).navigate(action)
//        }
//
//        holder.binding.checkTask.setOnCheckedChangeListener {
//                compoundButton,
//                b -> if(compoundButton.isPressed){
//                    adapterOnClick(todoList[position])
//                }
//        }
        holder.binding.todo = todoList[position]
        holder.binding.listener = this
        holder.binding.editListener = this

        holder.binding.checkTask.isChecked = false
        if (todoList[position].isDone == 1){
            holder.binding.checkTask.isChecked = true
        }
    }

    override fun getItemCount(): Int {
        return todoList.size
    }

    fun updateTodoList(newTodoList: List<Todo>){
        todoList.clear()
        todoList.addAll(newTodoList)
        notifyDataSetChanged()
    }

    override fun onCheckedChange(cb: CompoundButton, isCheckedBoolean: Boolean, obj: Todo) {
        if(cb.isPressed){
            adapterOnClick(obj)
            //cb.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG;
       }
    }

    override fun onTodoEditClick(v: View) {
        val uuid = v.tag.toString().toInt()
        val action = TodoListFragmentDirections.actionEditTodo(uuid)
            Navigation.findNavController(v).navigate(action)
    }

}