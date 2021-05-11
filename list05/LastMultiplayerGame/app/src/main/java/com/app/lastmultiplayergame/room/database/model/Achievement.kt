package com.app.lastmultiplayergame.room.database.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

/**
 * Abstract table.
 * Each Achievement class argument is a column inside achievements.
 */
@Parcelize
@Entity(tableName = "achievements")
data class Achievement(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val playerName: String,
    val result: Int,
) : Parcelable