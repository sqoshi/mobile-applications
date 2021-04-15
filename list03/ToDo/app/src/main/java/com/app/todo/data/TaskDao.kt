package com.app.todo.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.app.todo.model.Task

/**
 * Interface provides abstract access to app's database.
 */
@Dao
interface TaskDao {
    /**
     * Add task to a database.
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addTask(task: Task)

    /**
     * Read all tasks as LiveData from a database.
     */
    @Query("SELECT * FROM task_table ORDER BY id DESC")
    fun readAllData(): LiveData<List<Task>>

    /**
     * Read all tasks as List from a database.
     */
    @Query("SELECT * FROM task_table")
    fun readTasks(): List<Task>

    /**
     * Updates task in database.
     */
    @Update
    suspend fun updateTask(task: Task)

    /**
     * Remove single task in database.
     */
    @Delete
    suspend fun deleteTask(task: Task)


    /**
     * Remove all tasks in database.
     */
    @Query("DELETE FROM task_table")
    suspend fun deleteAllTasks()

    /**
     * Read all tasks with specified priority.
     */
    @Query("SELECT * FROM task_table WHERE priority = :priority")
    fun filterByPriority(priority: String): LiveData<List<Task>>

    /**
     * Read all tasks sorted by name.
     */
    @Query("SELECT * FROM task_table ORDER BY name ASC")
    fun sortedByName(): LiveData<List<Task>>

    /**
     * Read all tasks sorted by type.
     */
    @Query("SELECT * FROM task_table ORDER BY type ASC")
    fun sortedByType(): LiveData<List<Task>>

    /**
     * Read all tasks sorted by year.
     */
    @Query("SELECT * FROM task_table ORDER BY year ASC")
    fun sortedByDate(): LiveData<List<Task>>

    /**
     * Read all tasks sorted by priority.
     */
    @Query("SELECT * FROM task_table ORDER BY priority ASC")
    fun sortedByPriority(): LiveData<List<Task>>

    /**
     * Read all tasks sorted by name.
     */
    @Query("SELECT * FROM task_table WHERE year = :year AND  month = :month AND day = :day")
    fun getTasksFrom(year: Int, month: Int, day: Int): LiveData<List<Task>>

}