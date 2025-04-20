package com.example.quizdemo.ui.result

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.quizdemo.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ResultViewModel @Inject constructor(
    application: Application,
) : BaseViewModel(application) {

    private val _scoreData = MutableLiveData<ScoreData>()
    val scoreData: LiveData<ScoreData> get() = _scoreData

    fun processScoreData(score: Int, totalQuestions: Int) {
        // Calculate the percentage of the correct answers
        val percentage = (score.toFloat() / totalQuestions.toFloat() * 100).toInt()
        val feedback = getFeedbackBasedOnScore(percentage)

        // Set the all score value into the data model class
        _scoreData.value = ScoreData(
            score = score,
            totalQuestions = totalQuestions,
            percentage = percentage,
            feedback = feedback
        )
    }

    /**
     * Get the feedback message based on the total percentage of correct answers.
     * @param percentage int value
     * @return String value
     */
    private fun getFeedbackBasedOnScore(percentage: Int): String {
        return when {
            percentage >= 80 -> "Excellent!"
            percentage >= 60 -> "Good job!"
            percentage >= 40 -> "Not bad!"
            else -> "Keep practicing!"
        }
    }

    // Create data class for store the Score Data
    data class ScoreData(
        val score: Int,
        val totalQuestions: Int,
        val percentage: Int,
        val feedback: String
    )
}