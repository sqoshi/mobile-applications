package com.app.todo.fragments.calendar

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.findFragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.todo.R
import com.app.todo.fragments.list.ListAdapter
import com.app.todo.viewmodel.TaskViewModel
import kotlinx.android.synthetic.main.fragment_calendar.*
import kotlinx.android.synthetic.main.fragment_calendar.view.*
import kotlinx.android.synthetic.main.fragment_update.view.*
import sun.bob.mcalendarview.listeners.OnDateClickListener
import sun.bob.mcalendarview.views.ExpCalendarView
import sun.bob.mcalendarview.vo.DateData


class CalendarFragment : Fragment() {
    private val adapter = ListAdapter()
    private lateinit var mTaskViewModel: TaskViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_calendar, container, false)

        val recView = view.recViewDayShort
        recView.adapter = adapter
        recView.layoutManager = LinearLayoutManager(requireContext())
        mTaskViewModel = ViewModelProvider(this).get(TaskViewModel::class.java)


        val calendarView = view.findViewById(R.id.calendarView) as ExpCalendarView
        calendarView.setOnDateClickListener(DateClickerListener())


        for (task in mTaskViewModel.readTasks()!!) {
            calendarView.markDate(task.year, task.month, task.day)
        }


        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        calendarView.init(activity)
        //TODO: no way to saveInstance of calendar date, just no way
    }

    inner class DateClickerListener : OnDateClickListener() {
        override fun onDateClick(view: View?, date: DateData?) {
            if (date != null)
                mTaskViewModel.getTasksFrom(date.year, date.month, date.day)
                    .observe(viewLifecycleOwner, { task ->
                        adapter.setData(task)
                    })
        }

    }
}


