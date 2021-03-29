package com.app.todo.fragments.add

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.app.todo.R
import com.app.todo.model.Task
import com.app.todo.viewmodel.TaskViewModel
import kotlinx.android.synthetic.main.fragment_add.*
import kotlinx.android.synthetic.main.fragment_add.view.*
import java.sql.Date
import java.text.ParseException
import java.text.SimpleDateFormat

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
        val date = editTextDate.text.toString()
        val name = editTextName.text.toString()
        Log.i("info", date)
//        val date_formated: Date = formatter.parse(date)
//        inputCheck(name, date_formated)

        val task = Task(0, name = name, date = date, description = desc)

        mTaskViewModel.addTask(task)
        Toast.makeText(requireContext(), "ADDED TASK", Toast.LENGTH_SHORT).show()
        findNavController().navigate(R.id.action_addFragment_to_listFragment)
    }

    private fun stringToDate() {
        val dtStart = "2010-10-15T09:27:37Z"
        val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
        try {
//            val date: Date = format.parse(dtStart)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
    }

    private fun inputCheck(name: String, date: Date): Boolean {
        return !(TextUtils.isEmpty(name) && TextUtils.isEmpty(date.toString()))
    }
}