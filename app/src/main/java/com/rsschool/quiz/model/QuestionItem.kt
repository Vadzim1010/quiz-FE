package com.rsschool.quiz.model

data class QuestionItem(
    val questionNumber: Int,
    val question: String,
    val answer1: String,
    val answer2: String,
    val answer3: String,
    val answer4: String,
    val answer5: String,
    val correctAnswer: Int
)
