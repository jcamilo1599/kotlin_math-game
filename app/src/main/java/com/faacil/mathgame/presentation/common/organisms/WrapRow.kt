package com.faacil.mathgame.presentation.common.organisms

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlin.math.max

@Composable
fun WrapRow(
    modifier: Modifier = Modifier,
    spacing: Dp = 8.dp,
    content: @Composable () -> Unit
) {
    Layout(
        content = content,
        modifier = modifier
    ) { measurables, constraints ->
        val rowConstraints = constraints.copy(minWidth = 0)
        val placeables = measurables.map { it.measure(rowConstraints) }

        var width = 0
        var height = 0
        var rowHeight = 0
        val rowWidths = mutableListOf<Int>()
        val rowHeights = mutableListOf<Int>()

        // Primero, mide y acumula las anchuras y alturas de cada fila
        placeables.forEach { placeable ->
            if (width + placeable.width > constraints.maxWidth) {
                rowWidths.add(width)
                rowHeights.add(rowHeight)
                height += rowHeight + spacing.roundToPx()
                width = 0
                rowHeight = 0
            }

            width += placeable.width + spacing.roundToPx()
            rowHeight = max(rowHeight, placeable.height)
        }

        // Añade la última fila
        rowWidths.add(width)
        rowHeights.add(rowHeight)
        height += rowHeight

        // Posiciona los elementos centrados en cada fila
        layout(constraints.maxWidth, height) {
            var rowIndex = 0
            width = 0
            height = 0

            placeables.forEachIndexed { index, placeable ->
                // Si este elemento es el inicio de una nueva fila, resetea el ancho y aumenta la
                // altura
                if (index > 0 && width + placeable.width > constraints.maxWidth) {
                    height += rowHeights[rowIndex] + spacing.roundToPx()
                    width = 0
                    rowIndex++
                }

                // Calcula el desplazamiento x para centrar la fila
                val rowWidth = rowWidths[rowIndex]
                val xOffset = (constraints.maxWidth - rowWidth) / 2 + width
                placeable.placeRelative(x = xOffset, y = height)
                width += placeable.width + spacing.roundToPx()
            }
        }
    }
}
