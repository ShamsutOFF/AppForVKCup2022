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
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.boundsInParent
import androidx.compose.ui.layout.boundsInWindow
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.example.appforvkcup2022.ui.theme.White
import kotlin.math.roundToInt
import kotlin.random.Random

private const val ROUND = 150

@Composable
fun DrawSecondPage() {
    val screenWidth = LocalConfiguration.current.screenWidthDp
    val screenHeight = LocalConfiguration.current.screenHeightDp
    LazyColumn() {
        items(count = Int.MAX_VALUE) { count ->
            ElevatedCard(
                modifier = Modifier
                    .padding(8.dp, 8.dp)
                    .fillMaxWidth()
                    .height(Dp(screenHeight / 2.5f))
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
//                        .height(Dp(screenHeight / 3f))
                        .height(300.dp)
                        .onGloballyPositioned {
                            println("screenHeight = $screenHeight")
                            println("screenWidth = $screenWidth")
                            println("Box it.boundsInParent().size = ${it.boundsInParent().size}")
                        }
                ) {
                    val comparison = getRandomComparison()

                    val xCoordinates = listOf(100f, 700f)
                    val yCoordinates = mutableListOf(100f, 300f, 500f, 700f)
                    val yCoordinates2 = mutableListOf(100f, 300f, 500f, 700f)
                    comparison.pairs.forEach {
                        val randomFirst = Random.nextInt(yCoordinates.size)
                        val randomSecond = Random.nextInt(yCoordinates2.size)
                        CreatePair(
                            firstStartValue = Offset(xCoordinates[0], yCoordinates[randomFirst]),
                            SecondStartValue = Offset(xCoordinates[1], yCoordinates2[randomSecond]),
                            text = it
                        )
                        yCoordinates.removeAt(randomFirst)
                        yCoordinates2.removeAt(randomSecond)
                    }
                }
            }
        }
    }
}

@Composable
fun CreatePair(firstStartValue: Offset, SecondStartValue: Offset, text: Pair<String, String>) {

    var firstOffset by remember { mutableStateOf(firstStartValue) }
    var secondOffset by remember { mutableStateOf(SecondStartValue) }
    var match by remember { mutableStateOf(false) }
    val color by animateColorAsState(targetValue = if (match) Color.Green else White)

    val randomPoint by remember {
        mutableStateOf(getRandomOffsetOutsideTheScreen())
    }

    val runawayOffsetFirst by animateOffsetAsState(
        targetValue = if (match) randomPoint else Offset(500f, 500f),
        animationSpec = tween(durationMillis = 2000, easing = LinearEasing)
    )
    val runawayOffsetSecond by animateOffsetAsState(
        targetValue = if (match) randomPoint else Offset(500f, 500f),
        animationSpec = tween(durationMillis = 2000, easing = LinearEasing)
    )

    var firstWidth = 0
    var secondWidth = 0

    OutlinedButton(onClick = {},
        colors = ButtonDefaults.buttonColors(containerColor = color),
        modifier = Modifier
            .onGloballyPositioned {
                firstWidth = it.size.width
            }
            .offset {
                if (match)
                    IntOffset(runawayOffsetFirst.x.roundToInt(), runawayOffsetFirst.y.roundToInt())
                else
                    IntOffset(
                        firstOffset.x.roundToInt(),
                        firstOffset.y.roundToInt()
                    )
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
            .onGloballyPositioned {
                secondWidth = it.size.width
            }
            .offset {
                if (match)
                    IntOffset(
                        runawayOffsetSecond.x.roundToInt(),
                        runawayOffsetSecond.y.roundToInt()
                    )
                else
                    IntOffset(
                        secondOffset.x.roundToInt(),
                        secondOffset.y.roundToInt()
                    )
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
        ),
        Comparison(
            listOf(
                Pair("Огурец", "Зеленый"),
                Pair("Помидор", "Красный"),
                Pair("Тыква", "Оранжевый"),
                Pair("Лук", "Желтый")
            )
        ),
        Comparison(
            listOf(
                Pair("Медведь", "Берлога"),
                Pair("Лиса", "Нора"),
                Pair("Белка", "Дупло"),
                Pair("Птица", "Гнездо")
            )
        ),
        Comparison(
            listOf(
                Pair("Кошка", "Мяукает"),
                Pair("Собака", "Лает"),
                Pair("Лошадь", "Ржет"),
                Pair("Овца", "Блеет")
            )
        ),
        Comparison(
            listOf(
                Pair("Январь", "Зима"),
                Pair("Апрель", "Весна"),
                Pair("Июль", "Лето"),
                Pair("Сентябрь", "Осень")
            )
        )
    )
}

data class Comparison(
    val pairs: List<Pair<String, String>>,
)