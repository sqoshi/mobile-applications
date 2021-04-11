package com.app.todo.viewmodel

import android.annotation.SuppressLint
import android.app.Application
import android.os.AsyncTask
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.app.todo.data.TaskDatabase
import com.app.todo.model.Task
import com.app.todo.repository.TaskRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class TaskViewModel(application: Application, private val state: SavedStateHandle) :
    AndroidViewModel(application) {
    val readAllData: LiveData<List<Task>>
    val sortedByName: LiveData<List<Task>>
    val sortedByType: LiveData<List<Task>>
    val sortedByDate: LiveData<List<Task>>
    val sortedByPriority: LiveData<List<Task>>
    private val repository: TaskRepository

    init {
        val taskDao = TaskDatabase.getDatabase(application).taskDao()
        repository = TaskRepository(taskDao)
        readAllData = repository.readAllData
        sortedByName = repository.sortedByName
        sortedByType = repository.sortedByType
        sortedByDate = repository.sortedByDate
        sortedByPriority = repository.sortedByPriority
    }

    fun saveState() {
        state.set("readAllData", readAllData.value)
    }


    fun filterByPriority(priority: String): LiveData<List<Task>> {
        return repository.filterByPriority(priority)
    }

    fun getTasksFrom(year: Int, month: Int, day: Int): LiveData<List<Task>> {
        return repository.getTasksFrom(year, month, day)
    }

    fun addTask(task: Task) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addTask(task)
        }
    }

    fun updateTask(task: Task) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateTask(task)
        }
    }

    fun deleteTask(task: Task) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteTask(task)
        }
    }

    fun deleteAllTasks() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAllTasks()
        }
    }

    fun readTasks(): List<Task>? {
        return AsyncGet().execute().get()
    }

    @SuppressLint("StaticFieldLeak")
    inner class AsyncGet : AsyncTask<Void, Void, List<Task>>() {
        override fun doInBackground(vararg params: Void?): List<Task> {
            return repository.readTasks()
        }

    }


}