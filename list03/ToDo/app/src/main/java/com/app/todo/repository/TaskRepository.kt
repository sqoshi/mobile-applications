package com.app.todo.repository

import androidx.lifecycle.LiveData
import com.app.todo.data.TaskDao
import com.app.todo.model.Task

/**
 * Access to multiple data sourced. Handles data operations.
 */
class TaskRepository(private val taskDao: TaskDao) {
    val readAllData: LiveData<List<Task>> = taskDao.readAllData()
    val sortedByName: LiveData<List<Task>> = taskDao.sortedByName()
    val sortedByType: LiveData<List<Task>> = taskDao.sortedByType()
    val sortedByDate: LiveData<List<Task>> = taskDao.sortedByDate()
    val sortedByPriority: LiveData<List<Task>> = taskDao.sortedByPriority()

    /**
     * Return live data list filtered by priority.
     */
    fun filterByPriority(priority: String): LiveData<List<Task>> {
        return taskDao.filterByPriority(priority)
    }

    /**
     * Return tasks from specified date.
     */
    fun getTasksFrom(year: Int, month: Int, day: Int): LiveData<List<Task>> {
        return taskDao.getTasksFrom(year, month, day)
    }

    /**
     * Return list of tasks.
     */
    fun readTasks(): List<Task> {
        return taskDao.readTasks()
    }

    /**
     * Add task to database.
     */
    suspend fun addTask(task: Task) {
        taskDao.addTask(task)
    }

    /**
     * Update task in database.
     */
    suspend fun updateTask(task: Task) {
        taskDao.updateTask(task)
    }

    /**
     * Remove task from database.
     */
    suspend fun deleteTask(task: Task) {
        taskDao.deleteTask(task)
    }

    /**
     * Remove all tasks from database.
     */
    suspend fun deleteAllTasks() {
        taskDao.deleteAllTasks()
    }

}