package com.example.quizdemo

import org.junit.runner.RunWith;
import org.junit.runners.Suite

@RunWith(Suite::class)
@Suite.SuiteClasses(
    QuestionsEntityTest::class,
    ScoreEntityTest::class,
)
class DbTestSuit {
}