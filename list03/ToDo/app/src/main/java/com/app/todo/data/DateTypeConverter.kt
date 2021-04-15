package com.app.todo.data

import androidx.room.TypeConverter


import java.util.Date

/***
 * Converts date before database insertion, read.
 */
class DateTypeConverter {
    /**
     * Converts Timestamp to Date object.
     * out of <- database
     */
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return if (value == null) null else Date(value)

    }

    /**
     * Converts date to timestamps
     * to -> database
     */
    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }

}