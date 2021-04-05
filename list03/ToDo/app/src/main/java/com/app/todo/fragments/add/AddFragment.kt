package com.app.todo.fragments.add

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.app.todo.R
import com.app.todo.model.Task
import com.app.todo.viewmodel.TaskViewModel
import kotlinx.android.synthetic.main.fragment_add.*
import kotlinx.android.synthetic.main.fragment_add.view.*
import kotlinx.android.synthetic.main.fragment_update.*
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

        view.iconImageView.tag = "ic_baseline_school_24"

        view.iconImageView.setOnClickListener {
            displayDialog(view)
        }

        view.timePicker.setIs24HourView(true)


        installSpinner(view)

        return view

    }

    private fun installSpinner(view: View) {
        val priorities = resources.getStringArray(R.array.priorities)

        val spinner = view.findViewById<Spinner>(R.id.prioritySpinner)
        if (spinner != null) {
            val adapter = ArrayAdapter(
                requireContext(),
                android.R.layout.simple_spinner_item, priorities
            )
            spinner.adapter = adapter

            spinner.onItemSelectedListener = object :
                AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View, position: Int, id: Long
                ) {
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.selected_item) + " " +
                                "" + priorities[position], Toast.LENGTH_SHORT
                    ).show()
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    // write code to perform some action
                }
            }
            prioritySpinner.setSelection(0); }

    }

    private fun insertDataToDataBase() {
        val desc = editTextDesc.text.toString()
        val name = editTextName.text.toString()
        val date = Date(
            datePicker.year,
            datePicker.month,
            datePicker.dayOfMonth,
            timePicker.hour,
            timePicker.minute
        )
        val priority = prioritySpinner.selectedItem
        val type = iconImageView.tag.toString()

        if (inputCheck(name)) {
            val task =
                Task(
                    0,
                    name = name,
                    date = date,
                    description = desc,
                    type = type,
                    priority = priority.toString()
                )
            mTaskViewModel.addTask(task)
            Toast.makeText(requireContext(), "Task added.", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_addFragment_to_listFragment)
        } else
            Toast.makeText(
                requireContext(),
                "Please input the name of the task.",
                Toast.LENGTH_SHORT
            ).show()

    }


    private fun inputCheck(name: String): Boolean {
        return !(TextUtils.isEmpty(name))
    }

    private fun displayDialog(view: View) {
        val dialogView: View =
            LayoutInflater.from(requireContext()).inflate(R.layout.alert_dialog, null)


        val dialog: AlertDialog = AlertDialog.Builder(
            requireContext()
        )
            .setTitle("Choose icon")
            .setMessage("Set icon for task.")
            .setView(dialogView)
            .setNegativeButton("Cancel", null)
            .create()

        val o1: LinearLayout = dialogView.findViewById(R.id.pets_option)
        o1.setOnClickListener {
            dialogListItemOnClick(view, dialog, R.drawable.ic_baseline_pets_24)
            view.iconImageView.tag = "ic_baseline_pets_24"
        }
        val o2: LinearLayout = dialogView.findViewById(R.id.school_option)
        o2.setOnClickListener {
            dialogListItemOnClick(view, dialog, R.drawable.ic_baseline_school_24)
            view.iconImageView.tag = "ic_baseline_school_24"

        }
        val o3: LinearLayout = dialogView.findViewById(R.id.work_option)
        o3.setOnClickListener {
            dialogListItemOnClick(view, dialog, R.drawable.ic_baseline_work_24)
            view.iconImageView.tag = "ic_baseline_work_24"

        }
        val o4: LinearLayout = dialogView.findViewById(R.id.person_option)
        o4.setOnClickListener {
            dialogListItemOnClick(view, dialog, R.drawable.ic_baseline_person_24)
            view.iconImageView.tag = "ic_baseline_person_24"

        }
        dialog.show()
    }

    private fun dialogListItemOnClick(view: View, dialog: AlertDialog, draw: Int) {
        view.iconImageView.setImageResource(draw)
        dialog.dismiss()
    }


}