package com.example.quizdemo

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.matcher.ViewMatchers.assertThat
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.quizdemo.data.database.AppDatabase
import com.example.quizdemo.data.database.db.QuizDao
import com.example.quizdemo.data.database.entity.QuizEntity
import kotlinx.coroutines.test.runTest
import org.hamcrest.CoreMatchers.`is`
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class QuestionsEntityTest {

    private lateinit var database: AppDatabase
    private lateinit var quizDao: QuizDao

    private lateinit var question1: QuizEntity
    private lateinit var question2: QuizEntity

    @Before
    fun createDb() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDatabase::class.java
        ).allowMainThreadQueries().build()
        quizDao = database.quizDao()
    }

    @Before
    fun initQuestions() {
        question1 = QuizEntity(
            id = 1,
            category = "Category 1",
            type = "multiple",
            difficulty = "easy",
            question = "Question 1?",
            correctAnswer = "Answer 1",
            incorrectAnswers = "Answer 2|Answer 3|Answer 4"
        )
        question2 = QuizEntity(
            id = 2,
            category = "Category 2",
            type = "boolean",
            difficulty = "medium",
            question = "Question 2?",
            correctAnswer = "False",
            incorrectAnswers = "True"
        )
    }

    @After
    fun closeDb() = database.close()

    @Test
    fun insertQuestions_getAllQuestions() = runTest {
        val questions = listOf(question1, question2)

        quizDao.insertQuestions(questions)
        val loadedQuestions = quizDao.getAllQuestions()

        assertThat(loadedQuestions.size, `is`(2))
        assertThat(loadedQuestions[0].question, `is`(question1.question))
        assertThat(loadedQuestions[1].category, `is`(question2.category))
    }

    @Test
    fun clearAllQuestions_getAllQuestionsReturnsEmpty() = runTest {
        val questions = listOf(question1)

        quizDao.insertQuestions(questions)
        quizDao.clearAllQuestions()
        val loadedQuestions = quizDao.getAllQuestions()

        assert(loadedQuestions.isEmpty())
    }

    @Test
    fun insertQuestions_withConflictReplacement() = runTest {
        quizDao.insertQuestions(listOf(question1.copy(id = 1)))
        quizDao.insertQuestions(listOf(question2.copy(id = 1)))

        val loadedQuestions = quizDao.getAllQuestions()
        assertThat(loadedQuestions.size, `is`(1))
        assertThat(loadedQuestions[0].category, `is`(question2.category))
        assertThat(loadedQuestions[0].question, `is`(question2.question))
    }
}