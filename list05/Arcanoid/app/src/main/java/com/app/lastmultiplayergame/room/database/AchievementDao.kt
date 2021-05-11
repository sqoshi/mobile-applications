package com.app.lastmultiplayergame.room.database

import androidx.room.*
import androidx.lifecycle.LiveData
import com.app.lastmultiplayergame.room.database.model.Achievement

/**
 * Queries to database.
 */
@Dao
interface AchievementDao {
    /**
     * Add achievement.
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addAchievement(achievement: Achievement)
    /**
     * Read all achievements.
     */
    @Query("SELECT * FROM achievements ORDER BY id DESC")
    fun readAllData(): LiveData<List<Achievement>>


    /**
     * Update achievement.
     */
    @Update
    suspend fun updateAchievement(achievement: Achievement)
}