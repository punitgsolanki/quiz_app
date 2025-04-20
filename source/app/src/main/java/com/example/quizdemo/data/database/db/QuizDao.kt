package com.example.quizdemo.data.database.db

import androidx.room.*
import com.example.quizdemo.data.database.entity.QuizEntity
import com.example.quizdemo.data.database.entity.UserScore

@Dao
interface QuizDao {
    /**
     * Insert all questions into local DB
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertQuestions(questions: List<QuizEntity>)

    /**
     * Select all questions from local DB
     * @return QuizEntity type list
     */
    @Query("SELECT * FROM quiz_questions")
    suspend fun getAllQuestions(): List<QuizEntity>

    /**
     * Clear all questions from local DB
     */
    @Query("DELETE FROM quiz_questions")
    suspend fun clearAllQuestions()

    /**
     * Save last score into local DB
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveUserScore(userScore: UserScore)

    /**
     * Get latest score from DB
     * @return UserScore model class
     */
    @Query("SELECT * FROM user_scores WHERE id = 1")
    suspend fun getLastScore(): UserScore?
}