package com.faacil.mathgame.helpers

import com.faacil.mathgame.enums.CalculationType

fun getOperationSign(action: CalculationType): String = when (action) {
    CalculationType.ADDITION -> "+"
    CalculationType.SUBTRACTION -> "-"
    CalculationType.DIVISION -> "÷"
    CalculationType.MULTIPLICATION -> "×"
}