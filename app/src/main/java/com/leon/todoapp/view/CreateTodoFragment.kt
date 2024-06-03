package com.leon.todoapp.view

import android.content.pm.PackageManager
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
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.leon.todoapp.R
import com.leon.todoapp.databinding.FragmentCreateTodoBinding
import com.leon.todoapp.model.Todo
import com.leon.todoapp.util.NotificationHelper
import com.leon.todoapp.util.TodoWorker
import com.leon.todoapp.viewmodel.DetailTodoViewModel
import java.util.concurrent.TimeUnit

class CreateTodoFragment : Fragment() {
    private lateinit var binding:FragmentCreateTodoBinding
    private lateinit var viewModel:DetailTodoViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCreateTodoBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(DetailTodoViewModel::class.java)

        binding.btnAdd.setOnClickListener {
//            val notif = NotificationHelper(view.context, requireActivity())
//            notif.createNotification("Todo Created","New Notification!")
            val workRequest = OneTimeWorkRequestBuilder<TodoWorker>()
                .setInitialDelay(10,TimeUnit.SECONDS)
                .setInputData(workDataOf(
                    "title" to "Todo Created",
                    "message" to "Notif Created!")).build()
            WorkManager.getInstance(requireContext()).enqueue(workRequest)

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

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode == NotificationHelper.REQUEST_NOTIF){
            if(grantResults.isNotEmpty() && grantResults[0] ==
                PackageManager.PERMISSION_GRANTED) {
                NotificationHelper(
                    requireContext(),
                    requireActivity())
                    .createNotification(
                        "Create Todo", "Notif here!"
                    )
            } else {
                Toast.makeText(context,
                    "This feature requires notification to be allowed!",Toast.LENGTH_SHORT).show()
            }
        }
    }
}