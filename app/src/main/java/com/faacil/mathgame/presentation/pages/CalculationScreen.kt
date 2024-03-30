package com.faacil.mathgame.presentation.pages

import androidx.compose.runtime.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.faacil.mathgame.enums.CalculationType
import kotlin.random.Random

@Composable
fun CalculationScreen(title: String, action: CalculationType) {
    var firstNumber by remember { mutableStateOf(0) }
    var secondNumber by remember { mutableStateOf(0) }
    var correctAnswer by remember { mutableStateOf(0) }
    var userAnswer by remember { mutableStateOf(0) }
    var isCorrect by remember { mutableStateOf(false) }
    var isFirstAttempt by remember { mutableStateOf(true) }
    val answers = remember { mutableStateListOf<Int>() }
    var questionTrigger by remember { mutableStateOf(0) } // Nuevo estado para controlar la generación de preguntas

    // Ahora `LaunchedEffect` se dispara por cambios en `action` o `questionTrigger`
    LaunchedEffect(key1 = action, key2 = questionTrigger) {
        firstNumber = Random.nextInt(1, 101)
        secondNumber = Random.nextInt(1, 101)

        val newQuestion = generateQuestionAndAnswers(action, firstNumber, secondNumber)
        correctAnswer = newQuestion.correctAnswer
        answers.clear()
        answers.addAll(newQuestion.answers)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            "$firstNumber ${getOperationSign(action)} $secondNumber",
            style = MaterialTheme.typography.headlineMedium
        )
        Row {
            answers.forEach { answer ->
                Button(
                    onClick = {
                        userAnswer = answer
                        isCorrect = answer == correctAnswer
                        isFirstAttempt = false
                        if (isCorrect) {
                            // Incrementa el contador para generar una nueva pregunta
                            questionTrigger += 1
                        }
                    },
                    modifier = Modifier.padding(8.dp)
                ) {
                    Text("$answer")
                }
            }
        }
        if (!isFirstAttempt) {
            Text(
                if (isCorrect) "Correct!" else "Try again!",
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}

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

data class QuestionAndAnswers(
    val first: Int,
    val second: Int,
    val correctAnswer: Int,
    val answers: List<Int>
)

fun getOperationSign(action: CalculationType): String = when (action) {
    CalculationType.ADDITION -> "+"
    CalculationType.SUBTRACTION -> "-"
    CalculationType.DIVISION -> "÷"
    CalculationType.MULTIPLICATION -> "×"
}
