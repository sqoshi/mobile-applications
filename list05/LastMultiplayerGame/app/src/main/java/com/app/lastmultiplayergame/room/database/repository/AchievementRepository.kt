package com.app.lastmultiplayergame.room.database.repository

import androidx.lifecycle.LiveData
import com.app.lastmultiplayergame.room.database.AchievementDao
import com.app.lastmultiplayergame.room.database.model.Achievement

class AchievementRepository(private val achieveDao: AchievementDao) {
    val readAllData: LiveData<List<Achievement>> = achieveDao.readAllData()


    suspend fun addAchievement(achieve: Achievement) {
        achieveDao.addAchievement(achieve)
    }

    suspend fun updateAchievement(achieve: Achievement) {
        achieveDao.updateAchievement(achieve)
    }

}