package com.app.todo.fragments.update

import android.app.AlarmManager
import android.app.AlertDialog
import android.app.PendingIntent
import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.app.todo.notifications.NotificationReceiver
import com.app.todo.R
import com.app.todo.model.Task
import com.app.todo.viewmodel.TaskViewModel
import kotlinx.android.synthetic.main.fragment_add.*
import kotlinx.android.synthetic.main.fragment_update.*
import kotlinx.android.synthetic.main.fragment_update.view.*
import java.util.*

/**
 * Responsible for editing existing in database task.
 */
class UpdateFragment : Fragment() {

    private val args by navArgs<UpdateFragmentArgs>()

    private lateinit var mTaskViewModel: TaskViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_update, container, false)

        mTaskViewModel = ViewModelProvider(this).get(TaskViewModel::class.java)



        view.iconImageViewUpdate.setOnClickListener {
            displayDialog(view)
        }

        view.buttonUpdate.setOnClickListener {
            updateItem()
        }

        setHasOptionsMenu(true)


        installSpinner(view)

        return view
    }

    /**
     * Loads data from database and display it.
     */
    private fun setValuesFromDatabase(view: View?) {
        if (view != null) {
            view.datePickerUpdate.init(
                args.currentTask.year,
                args.currentTask.month,
                args.currentTask.day,
                null
            )

            view.datePickerUpdate.minDate = System.currentTimeMillis() - 1000


            view.timePickerUpdate.setIs24HourView(true)
            view.timePickerUpdate.hour = args.currentTask.hour
            view.timePickerUpdate.minute = args.currentTask.minute


            view.editTextUpdateDesc.setText(args.currentTask.description)
            view.editTextUpdateName.setText(args.currentTask.name)

            val resourceId = resources.getIdentifier(
                args.currentTask.type, "drawable",
                activity?.packageName
            )
            view.iconImageViewUpdate.tag = args.currentTask.type
            view.iconImageViewUpdate.setImageResource(resourceId)
        }

    }

    /**
     * Set xml elements' values to passed arguments.
     */
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
            view.datePickerUpdate.init(
                year,
                month,
                day,
                null
            )

            view.datePickerUpdate.minDate = System.currentTimeMillis() - 1000


            view.timePickerUpdate.setIs24HourView(true)
            view.timePickerUpdate.hour = hour
            view.timePickerUpdate.minute = minute


            view.editTextUpdateDesc.setText(desc)
            view.editTextUpdateName.setText(name)

            val resourceId = resources.getIdentifier(
                type, "drawable",
                activity?.packageName
            )
            view.iconImageViewUpdate.tag = type
            view.iconImageViewUpdate.setImageResource(resourceId)
        }

    }

    /**
     * Saves data on orientation change.
     */
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("type", iconImageViewUpdate.tag.toString())
        outState.putInt("year", datePickerUpdate.year)
        outState.putInt("month", datePickerUpdate.month)
        outState.putInt("day", datePickerUpdate.dayOfMonth)
        outState.putInt("hour", timePickerUpdate.hour)
        outState.putInt("minute", timePickerUpdate.minute)
        outState.putString("desc", editTextUpdateDesc.text.toString())
        outState.putString("name", editTextUpdateName.text.toString())
        outState.putString("priority", prioritySpinnerUpdate.selectedItem.toString())

    }

    /**
     * Loads data on orientation change.
     */
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (savedInstanceState != null) {
            setValues(
                view,
                savedInstanceState.getInt("hour"),
                savedInstanceState.getInt("minute"),
                savedInstanceState.getInt("day"),
                savedInstanceState.getInt("month"),
                savedInstanceState.getInt("year"),
                savedInstanceState.getString("desc").toString(),
                savedInstanceState.getString("priority").toString(),
                savedInstanceState.getString("type").toString(),
                savedInstanceState.getString("name").toString()
            )
        } else
            setValuesFromDatabase(view)

    }

    /**
     * Set up spinner and adding functionality as displaying dialog on element click.
     */
    private fun installSpinner(view: View) {
        val priorities = resources.getStringArray(R.array.priorities)

        val spinner = view.findViewById<Spinner>(R.id.prioritySpinnerUpdate)
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
            view.prioritySpinnerUpdate.setSelection(adapter.getPosition(args.currentTask.priority)); }
    }

    /**
     * Updates task in database after providing changes.
     */
    private fun updateItem() {
        val name = editTextUpdateName.text.toString()
        val desc = editTextUpdateDesc.text.toString()
        val type = iconImageViewUpdate.tag.toString()
        val priority = prioritySpinnerUpdate.selectedItem.toString()


        if (inputCheck(name, desc, type)) {
            val updatedTask =
                Task(
                    args.currentTask.id,
                    name = name,
                    description = desc,
                    type = type,
                    priority = priority,
                    year = datePickerUpdate.year,
                    month = datePicker.month + 1,
                    day = datePickerUpdate.dayOfMonth,
                    hour = timePickerUpdate.hour,
                    minute = timePickerUpdate.minute
                )

            mTaskViewModel.updateTask(updatedTask)
            setUpNotification(
                year = datePickerUpdate.year,
                month = datePickerUpdate.month,
                day = datePickerUpdate.dayOfMonth,
                hour = timePickerUpdate.hour,
                minute = timePickerUpdate.minute - 1
            )
            findNavController().navigate(R.id.action_updateFragment_to_listFragment)
            Toast.makeText(requireContext(), "Successfully updated task", Toast.LENGTH_SHORT).show()
        } else
            Toast.makeText(
                requireContext(),
                "Please, change any field.",
                Toast.LENGTH_SHORT
            ).show()

    }

    /**
     * Check if data passed by user is correct and enough to insert.
     */
    private fun inputCheck(name: String, desc: String, type: String): Boolean {
        return (args.currentTask.description != desc || args.currentTask.name != name || args.currentTask.type != type)
    }

    /**
     * Add menu icon to delete current task.
     */
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.delete_menu, menu)
    }

    /**
     * On menu `delete` option click adding task deletion functionality.
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_delete) {
            deleteTask()
        }
        return super.onOptionsItemSelected(item)
    }

    /**
     * Remove task from a database.
     */
    private fun deleteTask() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Yes") { _, _ ->
            mTaskViewModel.deleteTask(task = args.currentTask)
            Toast.makeText(
                requireContext(),
                "Successfully removed ${args.currentTask.name}.",
                Toast.LENGTH_SHORT
            ).show()
            findNavController().navigate(R.id.action_updateFragment_to_listFragment)
        }
        builder.setNegativeButton("Cancel") { _, _ -> }
        builder.setTitle("Delete ${args.currentTask.name}")
        builder.setMessage("Are you sure you want to delete ${args.currentTask.name}?")
        builder.create()
        builder.show()
    }

    /**
     * Display dialog within user can choose icon for a updated task.
     */
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
            view.iconImageViewUpdate.tag = "ic_baseline_pets_24"
        }
        val o2: LinearLayout = dialogView.findViewById(R.id.school_option)
        o2.setOnClickListener {
            dialogListItemOnClick(view, dialog, R.drawable.ic_baseline_school_24)
            view.iconImageViewUpdate.tag = "ic_baseline_school_24"

        }
        val o3: LinearLayout = dialogView.findViewById(R.id.work_option)
        o3.setOnClickListener {
            dialogListItemOnClick(view, dialog, R.drawable.ic_baseline_work_24)
            view.iconImageViewUpdate.tag = "ic_baseline_work_24"

        }
        val o4: LinearLayout = dialogView.findViewById(R.id.person_option)
        o4.setOnClickListener {
            dialogListItemOnClick(view, dialog, R.drawable.ic_baseline_person_24)
            view.iconImageViewUpdate.tag = "ic_baseline_person_24"

        }
        dialog.show()
    }

    /**
     * Sets icon on in-dialog icon click.
     */
    private fun dialogListItemOnClick(view: View, dialog: AlertDialog, draw: Int) {
        view.iconImageViewUpdate.setImageResource(draw)
        dialog.dismiss()
    }

    /**
     * set Up notification for updated task.
     */
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
}