package com.app.todo.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.app.todo.data.TaskDao
import com.app.todo.model.Task
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TaskRepository(private val taskDao: TaskDao) {
    val readAllData: LiveData<List<Task>> = taskDao.readAllData()
    val sortedByName: LiveData<List<Task>> = taskDao.sortedByName()
    val sortedByType: LiveData<List<Task>> = taskDao.sortedByType()
    val sortedByDate: LiveData<List<Task>> = taskDao.sortedByDate()
    val sortedByPriority: LiveData<List<Task>> = taskDao.sortedByPriority()



    suspend fun addTask(task: Task) {
        taskDao.addTask(task)
    }

    suspend fun updateTask(task: Task) {
        taskDao.updateTask(task)
    }

    suspend fun deleteTask(task: Task) {
        taskDao.deleteTask(task)
    }

    suspend fun deleteAllTasks() {
        taskDao.deleteAllTasks()
    }

}