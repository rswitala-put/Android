package com.example.myapplication.ui.main

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.myapplication.ui.main.models.GameModel

@Dao
interface GameDao {
    @Query("SELECT * FROM game")
    fun getAll(): List<GameModel>

    @Insert
    fun insertAll(vararg games: GameModel)

    @Query("DELETE FROM game")
    fun deleteAll()
}