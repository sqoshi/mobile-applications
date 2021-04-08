package com.app.todo.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.app.todo.model.Task
import org.jetbrains.annotations.NotNull

@Dao
interface TaskDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addTask(task: Task)

    @Query("SELECT * FROM task_table ORDER BY id ASC")
    fun readAllData(): LiveData<List<Task>>

    @Update
    suspend fun updateTask(task: Task)

    @Delete
    suspend fun deleteTask(task: Task)

    @Query("DELETE FROM task_table")
    suspend fun deleteAllTasks()//: LiveData<List<Task>>

    @Query("SELECT * FROM task_table ORDER BY name ASC")
    fun sortedByName(): LiveData<List<Task>>

    @Query("SELECT * FROM task_table ORDER BY type ASC")
    fun sortedByType(): LiveData<List<Task>>

    @Query("SELECT * FROM task_table ORDER BY date ASC")
    fun sortedByDate(): LiveData<List<Task>>

    @Query("SELECT * FROM task_table ORDER BY priority ASC")
    fun sortedByPriority(): LiveData<List<Task>>

}