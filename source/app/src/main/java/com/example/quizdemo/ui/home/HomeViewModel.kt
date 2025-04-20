package com.example.quizdemo.ui.home

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.quizdemo.data.database.AppDatabase
import com.example.quizdemo.data.database.entity.QuizEntity
import com.example.quizdemo.data.database.entity.UserScore
import com.example.quizdemo.data.model.QuizResponse
import com.example.quizdemo.data.model.Resource
import com.example.quizdemo.data.repository.QuizRepository
import com.example.quizdemo.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    application: Application,
    private val repository: QuizRepository,
    private val database: AppDatabase,
) : BaseViewModel(application) {

    // LiveData for observe the other LiveData object, for API call and set the state of the screen.
    private val _status: MediatorLiveData<Any> by lazy {
        MediatorLiveData<Any>()
    }

    // Get function which can use in Activity for listen the LiveData value.
    val status: LiveData<Any> get() = _status

    // LiveData for set the live score value.
    private val _lastScore = MutableLiveData<UserScore?>()

    // Get variable which will use in Activity for update the score.
    val lastScore: LiveData<UserScore?> get() = _lastScore

    /**
     * Public function for get number of questions from the GET API
     */
    fun getQuizQuestions(totalQuestions: Int) {
        // Set 10 value for get the number of questions from the API
        _status.addSource(repository.getAllQuestions(totalQuestions)) { response ->
            _status.value = response

            // If successful response, save to Room database
            if (response is Resource.Success<*>) {
                val quizResponse = response.data as QuizResponse
                saveQuestionsToDatabase(quizResponse)
            }
        }
    }

    private fun saveQuestionsToDatabase(quizResponse: QuizResponse) {
        viewModelScope.launch(Dispatchers.IO) {
            // First clear old data
            database.quizDao().clearAllQuestions()
            val quizEntities = quizResponse.results.map { result ->
                QuizEntity.fromResult(result)
            }
            // Insert new questions
            database.quizDao().insertQuestions(quizEntities)
        }
    }

    /**
     * Save user score into the local DB
     */
    fun saveUserScore(userScore: UserScore) {
        viewModelScope.launch(Dispatchers.IO) {
            database.quizDao().saveUserScore(userScore)
        }
    }

    /**
     * Get the last scrom from the local DB
     */
    fun getLastScore() {
        viewModelScope.launch {
            val score = withContext(Dispatchers.IO) {
                database.quizDao().getLastScore()
            }
            _lastScore.value = score
        }
    }
}
