package com.app.galleryapp.database.viewmodel

import android.annotation.SuppressLint
import android.app.Application
import android.os.AsyncTask
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.app.galleryapp.database.ImageDatabase
import com.app.galleryapp.database.model.Image
import com.app.galleryapp.database.repository.ImageRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Class provides data to UI and survive configuration changes.
 */
class ImageViewModel(application: Application) : AndroidViewModel(application) {
    val readAllData: LiveData<List<Image>>
    private val repository: ImageRepository

    init {
        val taskDao = ImageDatabase.getDatabase(application).imageDao()
        repository = ImageRepository(taskDao)
        readAllData = repository.readAllData
    }

    /**
     * Add imageElement object to database.
     */
    fun addImage(img: Image) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addImage(img)
        }
    }

    /**
     * Updates element by passing a imageElement (with concrete id).
     */
    fun updateImage(img: Image) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateImage(img)
        }
    }

    /**
     * Using async reading all images as list.
     */
    fun readImages(): List<Image>? {
        return AsyncGet().execute().get()
    }

    /**
     * Allow to asynchronous request to database.
     */
    @SuppressLint("StaticFieldLeak")
    inner class AsyncGet : AsyncTask<Void, Void, List<Image>>() {
        override fun doInBackground(vararg params: Void?): List<Image> {
            return repository.readImages()
        }

    }
}