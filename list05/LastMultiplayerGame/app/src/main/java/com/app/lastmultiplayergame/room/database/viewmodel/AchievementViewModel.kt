package com.app.lastmultiplayergame.room.database.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.app.lastmultiplayergame.room.database.AchievementDatabase
import com.app.lastmultiplayergame.room.database.model.Achievement
import com.app.lastmultiplayergame.room.database.repository.AchievementRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Class provides data to UI and survive configuration changes.
 */
class AchievementViewModel(application: Application) : AndroidViewModel(application) {
    val readAllData: LiveData<List<Achievement>>
    private val repository: AchievementRepository

    init {
        val taskDao = AchievementDatabase.getDatabase(application).achievementDao()
        repository = AchievementRepository(taskDao)
        readAllData = repository.readAllData
    }

    /**
     * Add achievement object to database.
     */
    fun addAchievement(achievement: Achievement) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addAchievement(achievement)
        }
    }

    /**
     * Updates element by passing a achievement (with concrete id).
     */
    fun updateAchievement(achievement: Achievement) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateAchievement(achievement)
        }
    }

}