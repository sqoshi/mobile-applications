package com.app.lastmultiplayergame.room.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.app.lastmultiplayergame.room.database.model.Achievement


/**
 * Room database using singleton pattern to create a connection with a database.
 */
@Database(entities = [Achievement::class], version = 1, exportSchema = false)
abstract class AchievementDatabase : RoomDatabase() {

    abstract fun achievementDao(): AchievementDao

    companion object {
        @Volatile
        private var INSTANCE: AchievementDatabase? = null

        /**
         * Using singleton pattern creates and return database.
         */
        fun getDatabase(context: Context): AchievementDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AchievementDatabase::class.java,
                    "achievements"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}