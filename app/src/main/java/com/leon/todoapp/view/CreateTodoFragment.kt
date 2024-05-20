package com.leon.todoapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.navigation.Navigation
import com.leon.todoapp.R
import com.leon.todoapp.databinding.FragmentCreateTodoBinding
import com.leon.todoapp.model.Todo
import com.leon.todoapp.viewmodel.DetailTodoViewModel

class CreateTodoFragment : Fragment() {
    private lateinit var binding:FragmentCreateTodoBinding
    private lateinit var viewModel:DetailTodoViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(DetailTodoViewModel::class.java)

        binding.btnAdd.setOnClickListener {
            val radioID = binding.radioGroupPriority.checkedRadioButtonId
            val radio = view.findViewById<RadioButton>(radioID)
            val priority = radio.tag.toString().toInt()

            var todo = Todo(
                binding.txtTitle.text.toString(),
                binding.txtNotes.text.toString(),
                priority
            )
            viewModel.addTodo(todo)
            Toast.makeText(view.context, "Data added", Toast.LENGTH_LONG).show()
            Navigation.findNavController(it).popBackStack()
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCreateTodoBinding.inflate(inflater)
        return binding.root
    }


}