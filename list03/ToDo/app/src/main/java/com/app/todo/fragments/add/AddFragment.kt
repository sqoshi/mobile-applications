package com.app.todo.fragments.add

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.app.todo.R
import com.app.todo.model.Task
import com.app.todo.viewmodel.TaskViewModel
import kotlinx.android.synthetic.main.fragment_add.*
import kotlinx.android.synthetic.main.fragment_add.view.*
import java.util.*


class AddFragment : Fragment() {
    private lateinit var mTaskViewModel: TaskViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_add, container, false)
        mTaskViewModel = ViewModelProvider(this).get(TaskViewModel::class.java)

        view.buttonAdd.setOnClickListener {
            insertDataToDataBase()
        }

        return view

    }

    private fun insertDataToDataBase() {
        //TODO PARSE STR TO DATE
        val desc = editTextDesc.text.toString()
        val name = editTextName.text.toString()
        val date = Date(datePicker.year, datePicker.month, datePicker.dayOfMonth)

        if (inputCheck(name)) {

            val task = Task(0, name = name, date = date, description = desc)
            mTaskViewModel.addTask(task)

            Toast.makeText(requireContext(), "Task added.", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_addFragment_to_listFragment)
        } else
            Toast.makeText(requireContext(), "Please input the name of the task.", Toast.LENGTH_SHORT).show()

    }


    private fun inputCheck(name: String): Boolean {
        return !(TextUtils.isEmpty(name))
    }


    private fun getDateFromDatePicker(datePicker: DatePicker): Date {
        val day = datePicker.dayOfMonth
        val month = datePicker.month
        val year = datePicker.year
        val calendar: Calendar = Calendar.getInstance()
        calendar.set(year, month, day)
        return calendar.time
    }
}