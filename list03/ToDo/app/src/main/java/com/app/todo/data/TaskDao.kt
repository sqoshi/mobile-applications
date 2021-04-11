package com.app.todo.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.app.todo.model.Task
import java.util.Date

@Dao
interface TaskDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addTask(task: Task)

    @Query("SELECT * FROM task_table ORDER BY id DESC")
    fun readAllData(): LiveData<List<Task>>

    @Query("SELECT * FROM task_table")
    fun readTasks(): List<Task>

    @Update
    suspend fun updateTask(task: Task)

    @Delete
    suspend fun deleteTask(task: Task)

    @Query("DELETE FROM task_table")
    suspend fun deleteAllTasks()

    @Query("SELECT * FROM task_table WHERE priority = :priority")
    fun filterByPriority(priority: String): LiveData<List<Task>>

    @Query("SELECT * FROM task_table ORDER BY name ASC")
    fun sortedByName(): LiveData<List<Task>>

    @Query("SELECT * FROM task_table ORDER BY type ASC")
    fun sortedByType(): LiveData<List<Task>>

    @Query("SELECT * FROM task_table ORDER BY year ASC")
    fun sortedByDate(): LiveData<List<Task>>

    @Query("SELECT * FROM task_table ORDER BY priority ASC")
    fun sortedByPriority(): LiveData<List<Task>>

    @Query("SELECT * FROM task_table WHERE year = :year AND  month = :month AND day = :day")
    fun getTasksFrom(year: Int, month: Int, day: Int): LiveData<List<Task>>

}