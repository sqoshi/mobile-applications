package com.app.todo.fragments.update

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.app.todo.R
import com.app.todo.model.Task
import com.app.todo.viewmodel.TaskViewModel
import kotlinx.android.synthetic.main.fragment_add.*
import kotlinx.android.synthetic.main.fragment_update.*
import kotlinx.android.synthetic.main.fragment_update.view.*
import java.util.Date

class UpdateFragment : Fragment() {

    private val args by navArgs<UpdateFragmentArgs>()

    private lateinit var mTaskViewModel: TaskViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_update, container, false)

        mTaskViewModel = ViewModelProvider(this).get(TaskViewModel::class.java)

//        view.updateDatePicker.init(
//            args.currentTask.date.year,
//            args.currentTask.date.month,
//            args.currentTask.date.day,
//            null
//        )
        view.editTextUpdateDesc.setText(args.currentTask.description)
        view.editTextUpdateName.setText(args.currentTask.name)

        view.buttonUpdate.setOnClickListener {
            updateItem()
        }

        setHasOptionsMenu(true)
        return view
    }


    private fun updateItem() {
        val name = editTextUpdateName.text.toString()
        val desc = editTextUpdateDesc.text.toString()
        val date1 = Date(datePicker.year, datePicker.month, datePicker.dayOfMonth)


        //TODO: CHECK INPUT

        val updatedTask = Task(args.currentTask.id, name = name, description = desc, date = date1,type="test")

        mTaskViewModel.updateTask(updatedTask)
        findNavController().navigate(R.id.action_updateFragment_to_listFragment)
        Toast.makeText(requireContext(), "Successfully updated task", Toast.LENGTH_SHORT).show()

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.delete_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_delete) {
            deleteUser()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun deleteUser() {
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
}