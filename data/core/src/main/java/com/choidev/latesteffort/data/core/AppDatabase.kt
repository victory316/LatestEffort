package com.choidev.latesteffort.data.core

import androidx.room.RoomDatabase
import com.choidev.latesteffort.data.core.dao.SearchDao

abstract class AppDatabase : RoomDatabase() {

    abstract fun searchDao(): SearchDao
}
