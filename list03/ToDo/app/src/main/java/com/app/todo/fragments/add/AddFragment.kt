package com.app.todo.fragments.add

import android.app.AlarmManager
import android.app.AlertDialog
import android.app.PendingIntent
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.app.todo.R
import com.app.todo.model.Task
import com.app.todo.notifications.NotificationReceiver
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

        val view: View? = inflater.inflate(R.layout.fragment_add, container, false)
        if (view != null) {
            mTaskViewModel = ViewModelProvider(this).get(TaskViewModel::class.java)
            view.buttonAdd.setOnClickListener {
                insertDataToDataBase()
            }

            view.iconImageView.tag = "ic_baseline_school_24"

            view.iconImageView.setOnClickListener {
                displayDialog(view)
            }

            view.timePicker.setIs24HourView(true)
            view.datePicker.minDate = System.currentTimeMillis() - 1000

            installSpinner(view)

        }
        return view

    }

    private fun setValues(
        view: View?,
        hour: Int,
        minute: Int,
        day: Int, month: Int,
        year: Int,
        desc: String,
        priority: String,
        type: String,
        name: String,
    ) {
        if (view != null) {
            view.datePicker.init(
                year,
                month,
                day,
                null
            )

            view.datePicker.minDate = System.currentTimeMillis() - 1000


            view.timePicker.setIs24HourView(true)
            view.timePicker.hour = hour
            view.timePicker.minute = minute


            view.editTextDesc.setText(desc)
            view.editTextName.setText(name)

            val resourceId = resources.getIdentifier(
                type, "drawable",
                activity?.packageName
            )
            view.iconImageView.tag = type
            view.iconImageView.setImageResource(resourceId)
        }

    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        if (iconImageView != null && datePicker != null && timePicker != null) {
            outState.putString("type", iconImageView.tag.toString())
            outState.putInt("year", datePicker.year)
            outState.putInt("month", datePicker.month)
            outState.putInt("day", datePicker.dayOfMonth)
            outState.putInt("hour", timePicker.hour)
            outState.putInt("minute", timePicker.minute)
            outState.putString("desc", editTextDesc.text.toString())
            outState.putString("name", editTextName.text.toString())
            outState.putString("priority", prioritySpinner.selectedItem.toString())
        }

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (savedInstanceState != null) {
            val t = savedInstanceState.getString("type")
            val y = savedInstanceState.getInt("year")
            val month = savedInstanceState.getInt("month")
            val d = savedInstanceState.getInt("day")
            val h = savedInstanceState.getInt("hour")
            val minute = savedInstanceState.getInt("minute")
            val desc = savedInstanceState.getString("desc")
            val name = savedInstanceState.getString("name")
            val priority = savedInstanceState.getString("priority")
            setValues(
                view, h, minute, d, month, y, desc.toString(),
                priority.toString(), t.toString(),
                name.toString()
            )

        }

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
                    view: View?, position: Int, id: Long
                ) {
                }

                override fun onNothingSelected(parent: AdapterView<*>) {}
            }
            view.prioritySpinner.setSelection(0)
        }

    }

    private fun setUpNotification(hour: Int, minute: Int, day: Int, month: Int, year: Int) {
        val calendar: Calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, hour)
        calendar.set(Calendar.MINUTE, minute)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.YEAR, year)
        calendar.set(Calendar.MONTH, month)
        calendar.set(Calendar.DAY_OF_MONTH, day)
        if (calendar.time < Date()) calendar.add(Calendar.DAY_OF_MONTH, 1)
        val intent = Intent(activity?.applicationContext, NotificationReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            activity?.applicationContext,
            (0..2147483647).random(),
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )
        val alarmManager =
            activity?.getSystemService(AppCompatActivity.ALARM_SERVICE) as AlarmManager
        alarmManager.setRepeating(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            AlarmManager.INTERVAL_DAY,
            pendingIntent
        )
    }

    private fun insertDataToDataBase() {
        val desc = editTextDesc.text.toString()
        val name = editTextName.text.toString()
        val priority = prioritySpinner.selectedItem
        val type = iconImageView.tag.toString()

        if (inputCheck(name)) {
            val task = Task(
                0,
                name = name,
                description = desc,
                type = type,
                priority = priority.toString(),
                year = datePicker.year,
                month = datePicker.month + 1,
                day = datePicker.dayOfMonth,
                hour = timePicker.hour,
                minute = timePicker.minute
            )

            // TODO: time check should be added here
            setUpNotification(
                year = datePicker.year,
                month = datePicker.month,
                day = datePicker.dayOfMonth,
                hour = timePicker.hour,
                minute = timePicker.minute - 1
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

