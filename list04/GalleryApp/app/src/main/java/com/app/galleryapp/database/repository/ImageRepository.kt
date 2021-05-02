package com.app.galleryapp.database.repository

import androidx.lifecycle.LiveData
import com.app.galleryapp.database.ImageDao
import com.app.galleryapp.database.model.Image

/**
 * Access to multiple data sourced. Handles data operations.
 */
class ImageRepository(private val imageDao: ImageDao) {
    val readAllData: LiveData<List<Image>> = imageDao.readAllData()


    /**
     * Add Image to database.
     */
    suspend fun addImage(img: Image) {
        imageDao.addImage(img)
    }

    /**
     * Update Image in database.
     */
    suspend fun updateImage(img: Image) {
        imageDao.updateImage(img)
    }

    /**
     * Return list of images.
     */
    fun readImages(): List<Image> {
        return imageDao.readImages()
    }


}