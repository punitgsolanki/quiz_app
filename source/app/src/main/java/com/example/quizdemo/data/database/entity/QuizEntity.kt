package com.example.quizdemo.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.quizdemo.data.model.Result
import com.example.quizdemo.utils.extensions.nullSafe

@Entity(tableName = "quiz_questions")
data class QuizEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val category: String,
    val type: String,
    val difficulty: String,
    val question: String,
    val correctAnswer: String,
    val incorrectAnswers: String // Stored as JSON string
) {
    companion object {
        fun fromResult(result: Result): QuizEntity {
            return QuizEntity(
                category = result.category.nullSafe(),
                type = result.type.nullSafe(),
                difficulty = result.difficulty.nullSafe(),
                question = result.question.nullSafe(),
                correctAnswer = result.correctAnswer.nullSafe(),
                incorrectAnswers = result.incorrectAnswers.nullSafe().joinToString("|")
            )
        }
    }

    /*fun toResult(): Result {
        return Result(
            category = category.nullSafe(),
            type = type.nullSafe(),
            difficulty = difficulty.nullSafe(),
            question = question.nullSafe(),
            correctAnswer = correctAnswer.nullSafe(),
            incorrectAnswers = incorrectAnswers.nullSafe().split("|")
        )
    }*/
}