package com.example.quizdemo.data.network.apis

import com.example.quizdemo.data.model.QuizResponse
import retrofit2.http.*
import retrofit2.Call

interface QuizApi {
    /**
     * Get the quiz questions
     *
     * Responses:
     *  - 200: Successful operation
     *
     * @param amount Total number of questions
     * @return [Call]<[QuizResponse]> will return the model class
     */
    @GET("api.php")
    fun getAllQuestions(@Query("amount") amount: Int): Call<QuizResponse>
}
