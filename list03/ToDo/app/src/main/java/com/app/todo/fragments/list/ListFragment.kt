package com.app.todo.fragments.list

import android.app.AlertDialog
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.os.Build
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.todo.R
import com.app.todo.viewmodel.TaskViewModel
import kotlinx.android.synthetic.main.fragment_list.view.*


class ListFragment : Fragment() {
    private lateinit var mTaskViewModel: TaskViewModel
    private val adapter = ListAdapter()
    private var sortedBy = "default"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_list, container, false)

        val recView = view.recyclerView
        recView.adapter = adapter
        recView.layoutManager = LinearLayoutManager(requireContext())

        mTaskViewModel = ViewModelProvider(this).get(TaskViewModel::class.java)

        if (savedInstanceState != null) {
            sortedBy = savedInstanceState.getString("sortedBy").toString()
            restoreListOrder()
        } else {
            mTaskViewModel.readAllData.observe(viewLifecycleOwner, Observer { task ->
                adapter.setData(task)
            })
        }

        view.floatingActionButton.setOnClickListener {
            findNavController().navigate(R.id.action_listFragment_to_addFragment)
        }

        setHasOptionsMenu(true)
        return view
    }


    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("sortedBy", sortedBy)
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.delete_menu, menu)
        inflater.inflate(R.menu.sort_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_delete -> {
                deleteAllTasks()
            }
            R.id.type_sort -> {
                mTaskViewModel.sortedByType.observe(viewLifecycleOwner, Observer { task ->
                    adapter.setData(task)
                })
                sortedBy = "type"
            }
            R.id.name_sort -> {
                mTaskViewModel.sortedByName.observe(viewLifecycleOwner, Observer { task ->
                    adapter.setData(task)
                })
                sortedBy = "name"
            }
            R.id.date_sort -> {
                mTaskViewModel.sortedByDate.observe(viewLifecycleOwner, Observer { task ->
                    adapter.setData(task)
                })
                sortedBy = "date"

            }
            R.id.priority_sort -> {
                mTaskViewModel.sortedByPriority.observe(viewLifecycleOwner, Observer { task ->
                    adapter.setData(task)
                })
                sortedBy = "priority"

            }
        }
        return super.onOptionsItemSelected(item)
    }


    private fun deleteAllTasks() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Yes") { _, _ ->
            mTaskViewModel.deleteAllTasks()
            Toast.makeText(
                requireContext(),
                "Successfully removed all tasks.",
                Toast.LENGTH_SHORT
            ).show()
        }
        builder.setNegativeButton("Cancel") { _, _ -> }
        builder.setTitle("Clear task board")
        builder.setMessage("Are you sure you want to permanently delete all tasks?")
        builder.create()
        builder.show()
    }

    private fun restoreListOrder() {
        when (sortedBy) {
            "default" -> {
                mTaskViewModel.readAllData.observe(viewLifecycleOwner, Observer { task ->
                    adapter.setData(task)
                })
            }
            "type" -> {
                mTaskViewModel.sortedByType.observe(viewLifecycleOwner, Observer { task ->
                    adapter.setData(task)
                })
            }
            "priority" -> {
                mTaskViewModel.sortedByPriority.observe(viewLifecycleOwner, Observer { task ->
                    adapter.setData(task)
                })
            }
            "date" -> {
                mTaskViewModel.sortedByDate.observe(viewLifecycleOwner, Observer { task ->
                    adapter.setData(task)
                })
            }
            "name" -> {
                mTaskViewModel.sortedByName.observe(viewLifecycleOwner, Observer { task ->
                    adapter.setData(task)
                })
            }
        }
    }
}