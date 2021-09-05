package com.example.myapplication.ui.main.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "game")
data class GameModel(
    @PrimaryKey(autoGenerate = true) val id : Int,
    @ColumnInfo(name = "score") val score: String?,
    @ColumnInfo(name = "time") val time: String?,
    @ColumnInfo(name = "firstPlayer") val firstPlayer: String?,
    @ColumnInfo(name = "secondPlayer") val secondPlayer: String?
)