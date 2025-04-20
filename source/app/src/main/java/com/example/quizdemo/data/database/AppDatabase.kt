package com.example.quizdemo.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.quizdemo.data.database.db.QuizDao
import com.example.quizdemo.data.database.entity.QuizEntity
import com.example.quizdemo.data.database.entity.UserScore

@Database(entities = [QuizEntity::class, UserScore::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun quizDao(): QuizDao
}