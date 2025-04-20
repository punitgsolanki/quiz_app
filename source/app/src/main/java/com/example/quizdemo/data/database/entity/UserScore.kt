package com.example.quizdemo.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_scores")
data class UserScore(
    @PrimaryKey
    val id: Int = 1, // Single score record
    val score: Int,
    val totalQuestions: Int,
    val timestamp: Long = System.currentTimeMillis()
)