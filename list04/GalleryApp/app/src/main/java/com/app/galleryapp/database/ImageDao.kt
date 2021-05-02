package com.app.galleryapp.database

import androidx.room.*
import androidx.lifecycle.LiveData
import com.app.galleryapp.database.model.Image

@Dao
interface ImageDao {
    /**
     * Add image to a database.
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addImage(img: Image)

    /**
     * Read all images as LiveData from a database.
     */
    @Query("SELECT * FROM image_table ORDER BY id DESC")
    fun readAllData(): LiveData<List<Image>>

    /**
     * Read all images as List from a database.
     */
    @Query("SELECT * FROM image_table")
    fun readImages(): List<Image>


    /**
     * Updates image in database.
     */
    @Update
    suspend fun updateImage(img: Image)
}