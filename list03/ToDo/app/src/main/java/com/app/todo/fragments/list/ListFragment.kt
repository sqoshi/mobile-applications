package com.app.todo.fragments.list

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
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
    private var filteredBy = "default"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_list, container, false)

        val recView = view.recyclerView
        recView.adapter = adapter
        recView.layoutManager = LinearLayoutManager(requireContext())


        mTaskViewModel = ViewModelProvider(this).get(TaskViewModel::class.java)

        view.floatingActionButton.setOnClickListener {
            findNavController().navigate(R.id.action_listFragment_to_addFragment)
        }

        setHasOptionsMenu(true)
        return view
    }

    /**
     * Saves on orientation change last sort and filter type.
     */
    override fun onSaveInstanceState(outState: Bundle) {
        if (::mTaskViewModel.isInitialized)
            mTaskViewModel.saveState()
        super.onSaveInstanceState(outState)
        outState.putString("sortedBy", sortedBy)
        outState.putString("filteredBy", filteredBy)
    }

    /**
     * Loads data after orientation change setting filter and sort.
     */
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (savedInstanceState != null) {
            sortedBy = savedInstanceState.getString("sortedBy").toString()
            filteredBy = savedInstanceState.getString("filteredBy").toString()
            restoreListOrder()
        } else
            mTaskViewModel.readAllData.observe(viewLifecycleOwner, { task ->
                adapter.setData(task)
            })

    }

    /**
     * Inflates menu options.
     */
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.calendar_menu, menu)
        inflater.inflate(R.menu.delete_menu, menu)
//        inflater.inflate(R.menu.filter_menu, menu)
        inflater.inflate(R.menu.sort_menu, menu)
    }

    /**
     * Map menu options  on click to appropriate action.
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_delete -> {
                deleteAllTasks()
            }
            R.id.menu_calendar -> {
                findNavController().navigate(R.id.action_listFragment_to_calendarFragment)
            }
            R.id.type_sort -> {
                mTaskViewModel.sortedByType.observe(viewLifecycleOwner, { task ->
                    adapter.setData(task)
                })
                sortedBy = "type"
            }
            R.id.name_sort -> {
                mTaskViewModel.sortedByName.observe(viewLifecycleOwner, { task ->
                    adapter.setData(task)
                })
                sortedBy = "name"
            }
            R.id.date_sort -> {
                mTaskViewModel.sortedByDate.observe(viewLifecycleOwner, { task ->
                    adapter.setData(task)
                })
                sortedBy = "date"

            }
            R.id.priority_sort -> {
                mTaskViewModel.sortedByPriority.observe(viewLifecycleOwner, { task ->
                    adapter.setData(task)
                })
                sortedBy = "priority"

            }
            R.id.high_filter -> {
                mTaskViewModel.filterByPriority("HIGH").observe(viewLifecycleOwner, { task ->
                    adapter.setData(task)
                })
                filteredBy = "high"
            }
            R.id.medium_filter -> {
                mTaskViewModel.filterByPriority("MEDIUM").observe(viewLifecycleOwner, { task ->
                    adapter.setData(task)
                })
                filteredBy = "medium"
            }
            R.id.low_filter -> {
                mTaskViewModel.filterByPriority("LOW").observe(viewLifecycleOwner, { task ->
                    adapter.setData(task)
                })
                filteredBy = "low"
            }
            R.id.show_all -> {
                mTaskViewModel.readAllData.observe(viewLifecycleOwner, { task ->
                    adapter.setData(task)
                })
                filteredBy = "default"
            }
        }
        return super.onOptionsItemSelected(item)
    }

    /**
     * Remove all tasks from database.
     * Displays reassuring/ warning acceptance dialog.
     */
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

    /**
     * After rotation change data order need to be restored.
     * Restore sort and filtering config.
     */
    private fun restoreListOrder() {
        when (sortedBy) {
            "default" -> {
                mTaskViewModel.readAllData.observe(viewLifecycleOwner, { task ->
                    adapter.setData(task)
                })
            }
            "type" -> {
                mTaskViewModel.sortedByType.observe(viewLifecycleOwner, { task ->
                    adapter.setData(task)
                })
            }
            "priority" -> {
                mTaskViewModel.sortedByPriority.observe(viewLifecycleOwner, { task ->
                    adapter.setData(task)
                })
            }
            "date" -> {
                mTaskViewModel.sortedByDate.observe(viewLifecycleOwner, { task ->
                    adapter.setData(task)
                })
            }
            "name" -> {
                mTaskViewModel.sortedByName.observe(viewLifecycleOwner, { task ->
                    adapter.setData(task)
                })
            }
        }
        when (filteredBy) {
            "high" -> {
                mTaskViewModel.filterByPriority("HIGH").observe(viewLifecycleOwner, { task ->
                    adapter.setData(task)
                })
            }
            "medium" -> {
                mTaskViewModel.filterByPriority("MEDIUM").observe(viewLifecycleOwner, { task ->
                    adapter.setData(task)
                })
            }
            "low" -> {
                mTaskViewModel.filterByPriority("LOW").observe(viewLifecycleOwner, { task ->
                    adapter.setData(task)
                })
            }
            "default" -> {
                mTaskViewModel.readAllData.observe(viewLifecycleOwner, { task ->
                    adapter.setData(task)
                })
            }
        }
    }
}