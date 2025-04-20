package com.example.quizdemo

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.matcher.ViewMatchers.assertThat
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.quizdemo.data.database.AppDatabase
import com.example.quizdemo.data.database.db.QuizDao
import com.example.quizdemo.data.database.entity.UserScore
import kotlinx.coroutines.test.runTest
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.nullValue
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ScoreEntityTest {

    private lateinit var database: AppDatabase
    private lateinit var quizDao: QuizDao
    private lateinit var score1: UserScore
    private lateinit var score2: UserScore

    @Before
    fun createDb() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDatabase::class.java
        ).allowMainThreadQueries().build()
        quizDao = database.quizDao()
    }

    @Before
    fun initScores() {
        score1 = UserScore(score = 5, totalQuestions = 10)
        score2 = UserScore(score = 9, totalQuestions = 10)
    }

    @After
    fun closeDb() = database.close()

    @Test
    fun saveUserScore_getLastScore() = runTest {
        quizDao.saveUserScore(score1)

        val loadedScore = quizDao.getLastScore()

        assertThat(loadedScore, `is`(score1))
        assertThat(loadedScore?.score, `is`(score1.score))
        assertThat(loadedScore?.totalQuestions, `is`(score1.totalQuestions))
    }

    @Test
    fun saveMultipleUserScores_getLastScoreReturnsLatest() = runTest {
        quizDao.saveUserScore(score1)
        quizDao.saveUserScore(score2)

        val loadedScore = quizDao.getLastScore()

        assertThat(loadedScore, `is`(score2))
        assertThat(loadedScore?.score, `is`(score2.score))
    }

    @Test
    fun getLastScore_returnsNull_whenNoScoreSaved() = runTest {
        val loadedScore = quizDao.getLastScore()
        assertThat(loadedScore, `is`(nullValue()))
    }
}