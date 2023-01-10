package com.example.appforvkcup2022.second_stage.pages

import androidx.compose.animation.*
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateOffsetAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.example.appforvkcup2022.ui.theme.White
import kotlin.math.roundToInt
import kotlin.random.Random

private const val ROUND = 100

@Composable
fun DrawSecondPage() {
    var draggableComposable by remember {
        mutableStateOf<(@Composable () -> Unit)?>(null)
    }

    LazyColumn() {
        items(count = Int.MAX_VALUE) { count ->
            ElevatedCard(
                modifier = Modifier.padding(
                    8.dp,
                    8.dp
                )
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp)
                ) {
                    val comparison = getRandomComparison()
                    val xCoordinates = listOf(100f, 900f)
                    val yCoordinates = listOf(100f, 300f, 500f, 700f)
                    var i = 0
                    comparison.pairs.forEach {
                        DrawBubble(
                            firstStartValue = Offset(xCoordinates[0], yCoordinates[i]),
                            SecondStartValue = Offset(xCoordinates[1], yCoordinates[i++]), text = it
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun DrawBubble(firstStartValue: Offset, SecondStartValue: Offset, text: Pair<String, String>) {
    var firstOffset by remember { mutableStateOf(firstStartValue) }
    var secondOffset by remember { mutableStateOf(SecondStartValue) }
    var match by remember { mutableStateOf(false) }
    val color by animateColorAsState(targetValue = if (match) Color.Green else White)

    val randomPoint by remember {
        mutableStateOf(getRandomOffsetOutsideTheScreen() )
    }


    val runawayOffsetFirst by animateOffsetAsState(
        targetValue = if (match) randomPoint else Offset(500f,500f),
//        targetValue = if (match) randomPoint else firstOffset,
        animationSpec = tween(durationMillis = 1000, easing = LinearEasing)
    )
    val runawayOffsetSecond by animateOffsetAsState(
        targetValue = if (match) randomPoint else Offset(500f,500f),
        animationSpec = tween(durationMillis = 1000, easing = LinearEasing)
    )


    OutlinedButton(onClick = {},
        colors = ButtonDefaults.buttonColors(containerColor = color),
        modifier = Modifier
            .offset {
                if (match)
                    IntOffset(runawayOffsetFirst.x.roundToInt(), runawayOffsetFirst.y.roundToInt())
                else
                    IntOffset(firstOffset.x.roundToInt(), firstOffset.y.roundToInt())
            }
            .pointerInput(Unit) {
                detectDragGestures(
                    onDrag = { change, dragAmount ->
                        change.consume()
                        firstOffset += dragAmount
                    },
                    onDragEnd = {
                        match = checkMatching(firstOffset, secondOffset)
                    }
                )
            }
    ) {
        Text(text = text.first)
    }

    OutlinedButton(onClick = {},
        colors = ButtonDefaults.buttonColors(containerColor = color),
        modifier = Modifier
            .offset {
                if (match)
                    IntOffset(
                        runawayOffsetSecond.x.roundToInt(),
                        runawayOffsetSecond.y.roundToInt()
                    )
                else
                    IntOffset(secondOffset.x.roundToInt(), secondOffset.y.roundToInt())
            }
            .pointerInput(Unit) {
                detectDragGestures(
                    onDrag = { change, dragAmount ->
                        change.consume()
                        secondOffset += dragAmount
                    },
                    onDragEnd = {
                        match = checkMatching(firstOffset, secondOffset)
                    }
                )
            }
    ) {
        Text(text = text.second)
    }
}

private fun getRandomOffsetOutsideTheScreen(): Offset {
    println("getRandomOffsetOutsideTheScreen()")
    val randomPoints = listOf(
        Random.nextInt(1500, 6500).toFloat(),
        Random.nextInt(-5500, -500).toFloat(),
        Random.nextInt(1500, 6500).toFloat(),
        Random.nextInt(-5500, -500).toFloat()
    )
    return Offset(
        randomPoints[Random.nextInt(randomPoints.size)],
        randomPoints[Random.nextInt(randomPoints.size)]
    )
}

private fun checkMatching(firstOffset: Offset, secondOffset: Offset): Boolean {
    return (firstOffset.x in secondOffset.x - ROUND..secondOffset.x + ROUND &&
        firstOffset.y in secondOffset.y - ROUND..secondOffset.y + ROUND)
}

private fun getRandomComparison(): Comparison {
    val forms = getAllComparison()
    return forms[Random.nextInt(forms.size)]
}

private fun getAllComparison(): List<Comparison> {
    return listOf(
        Comparison(
            listOf(
                Pair("Один", "1"),
                Pair("Два", "2"),
                Pair("Три", "3"),
                Pair("Четыре", "4")
            )
        )
    )
}

data class Comparison(
    val pairs: List<Pair<String, String>>,
)