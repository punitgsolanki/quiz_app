package com.example.quizdemo.di

import android.content.Context
import androidx.room.Room
import com.example.quizdemo.data.database.AppDatabase
import com.example.quizdemo.data.database.db.QuizDao
import com.example.quizdemo.data.network.infrastructure.ApiClient
import com.example.quizdemo.utils.DB_NAME
import com.example.quizdemo.utils.JLog
import com.example.quizdemo.utils.extensions.getClassName
import com.example.quizdemo.data.network.apis.QuizApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideApiClient(): ApiClient = ApiClient(
    ).setLogger {
        JLog.d(getClassName(), it)
    }

    @Singleton
    @Provides
    fun provideQuizApi(apiClient: ApiClient): QuizApi = apiClient.createService(QuizApi::class.java)

    @Singleton
    @Provides
    fun provideAppDatabase(
        @ApplicationContext app: Context
    ): AppDatabase = Room.databaseBuilder(
        app,
        AppDatabase::class.java,
        DB_NAME
    ).allowMainThreadQueries().build()

    @Singleton
    @Provides
    fun provideQuizDao(database: AppDatabase): QuizDao = database.quizDao()
}