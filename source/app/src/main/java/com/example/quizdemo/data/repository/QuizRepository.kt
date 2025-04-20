package com.example.quizdemo.data.repository

import androidx.lifecycle.MutableLiveData
import com.example.quizdemo.data.network.apis.QuizApi
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class QuizRepository @Inject constructor(
    private val quizApi: QuizApi
) {
    fun getAllQuestions(
        amount: Int
    ): MutableLiveData<Any> {

        val authClientCall = quizApi.getAllQuestions(
            amount= amount,
        )

        return retrofitCall(authClientCall, false)
    }
}