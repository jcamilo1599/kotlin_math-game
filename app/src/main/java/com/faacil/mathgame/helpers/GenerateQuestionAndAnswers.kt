package com.faacil.mathgame.helpers

import com.faacil.mathgame.enums.CalculationType
import com.faacil.mathgame.models.QuestionAndAnswers
import kotlin.random.Random

fun generateQuestionAndAnswers(
    action: CalculationType,
    firstNumber: Int,
    secondNumber: Int
): QuestionAndAnswers {
    val correctAnswer: Int
    val answers = mutableListOf<Int>()

    when (action) {
        CalculationType.ADDITION -> {
            correctAnswer = firstNumber + secondNumber
        }

        CalculationType.SUBTRACTION -> {
            correctAnswer = firstNumber - secondNumber
        }

        CalculationType.DIVISION -> {
            correctAnswer = firstNumber / secondNumber
        }

        CalculationType.MULTIPLICATION -> {
            correctAnswer = firstNumber * secondNumber
        }
    }

    answers.add(correctAnswer)

    while (answers.size < 4) {
        val fakeAnswer = correctAnswer + Random.nextInt(-10, 10)
        if (fakeAnswer !in answers) answers.add(fakeAnswer)
    }

    answers.shuffle()
    return QuestionAndAnswers(firstNumber, secondNumber, correctAnswer, answers)
}