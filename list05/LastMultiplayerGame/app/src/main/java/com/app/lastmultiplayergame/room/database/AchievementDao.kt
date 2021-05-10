package com.app.lastmultiplayergame.room.database

import androidx.room.*
import androidx.lifecycle.LiveData
import com.app.lastmultiplayergame.room.database.model.Achievement

@Dao
interface AchievementDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addAchievement(achievement: Achievement)

    @Query("SELECT * FROM achievements ORDER BY id DESC")
    fun readAllData(): LiveData<List<Achievement>>


    @Update
    suspend fun updateAchievement(achievement: Achievement)
}