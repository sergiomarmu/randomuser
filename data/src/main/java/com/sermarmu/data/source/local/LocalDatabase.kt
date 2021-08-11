package com.sermarmu.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.sermarmu.data.source.local.io.UserDB


@Database(
    entities = [
        UserDB::class
    ], version = 1
)
abstract class LocalDatabase: RoomDatabase() {
    abstract fun localApi(): LocalApi
}