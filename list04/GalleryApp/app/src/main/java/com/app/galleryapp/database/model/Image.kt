package com.app.galleryapp.database.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

/**
 * Abstract table.
 * Each Image class argument is a column inside image_table.
 */
@Parcelize
@Entity(tableName = "image_table")
data class Image(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val path: String,
    val description: String?,
    val rating: Float?,
) : Parcelable