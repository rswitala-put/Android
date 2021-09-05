package com.example.myapplication.ui.main

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.myapplication.ui.main.models.GameModel

class DBClass {
    @Database(entities = [GameModel::class], version = 1)
    abstract class AppDatabase : RoomDatabase() {
        abstract fun gameDao(): GameDao
    }
}