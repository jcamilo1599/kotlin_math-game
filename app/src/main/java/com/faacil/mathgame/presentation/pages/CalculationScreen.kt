package com.faacil.mathgame.presentation.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.faacil.mathgame.enums.CalculationType
import com.faacil.mathgame.helpers.generateQuestionAndAnswers
import com.faacil.mathgame.helpers.getOperationSign
import com.faacil.mathgame.presentation.common.organisms.WrapRow
import kotlin.random.Random

@Composable
fun CalculationScreen(title: String, action: CalculationType) {
    var firstNumber by remember { mutableIntStateOf(0) }
    var secondNumber by remember { mutableIntStateOf(0) }
    var correctAnswer by remember { mutableIntStateOf(0) }
    var userAnswer by remember { mutableIntStateOf(0) }
    var isCorrect by remember { mutableStateOf(false) }
    var isFirstAttempt by remember { mutableStateOf(true) }
    val answers = remember { mutableStateListOf<Int>() }

    // Controla la generación de preguntas
    var questionTrigger by remember { mutableIntStateOf(0) }

    // Se ejecuta por cambios en `action` o `questionTrigger`
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
            .padding(14.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            "$firstNumber ${getOperationSign(action)} $secondNumber",
            style = MaterialTheme.typography.headlineLarge
        )

        Spacer(modifier = Modifier.height(26.dp))

        WrapRow(spacing = 0.dp) {
            answers.forEach { answer ->
                Button(
                    onClick = {
                        userAnswer = answer
                        isCorrect = answer == correctAnswer
                        isFirstAttempt = false

                        if (isCorrect) {
                            questionTrigger += 1
                        }
                    },
                    modifier = Modifier.padding(6.dp),
                    content = {
                        Text(text = answer.toString())
                    }
                )
            }
        }

        if (!isFirstAttempt) {
            Spacer(modifier = Modifier.height(16.dp))

            Text(
                if (isCorrect) "Correct!" else "Try again!",
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}
