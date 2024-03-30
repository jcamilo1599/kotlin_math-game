package com.faacil.mathgame.models

data class QuestionAndAnswers(
    val first: Int,
    val second: Int,
    val correctAnswer: Int,
    val answers: List<Int>
)