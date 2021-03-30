package com.app.todo.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.app.todo.DateTypeConverter
import kotlinx.android.parcel.Parcelize
import java.util.Date

@Parcelize
@Entity(tableName = "task_table")
data class Task(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    val date: Date,
    val description: String,
) : Parcelable