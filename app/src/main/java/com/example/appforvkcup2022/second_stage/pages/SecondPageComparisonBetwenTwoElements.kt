package com.example.appforvkcup2022.second_stage.pages

import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
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
    val context = LocalContext.current
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
                    var x = 100.0f
                    var y = 100.0f
                    var id = 0
                    comparison.pairs.forEach {
                        DrawBubble(startValueX = x, startValueY = y, text = it.first, id = id)
                        DrawBubble(startValueX = x+900, startValueY = y, text = it.second, id = id)
                        y += 200
                    }


//Здесь было то что в низу закоментировано
                }
            }
        }
    }
}

@Composable
fun DrawBubble(startValueX: Float, startValueY: Float, text: String, id: Int) {
    var offsetX by remember { mutableStateOf(startValueX) }
    var offsetY by remember { mutableStateOf(startValueY) }
    OutlinedButton(onClick = {},
        colors = ButtonDefaults.buttonColors(containerColor = White),
        modifier = Modifier
            .offset { IntOffset(offsetX.roundToInt(), offsetY.roundToInt()) }
            .pointerInput(Unit) {
                detectDragGestures(
                    onDrag = { change, dragAmount ->
                        change.consume()
                        offsetX += dragAmount.x
                        offsetY += dragAmount.y
                    },
                    onDragEnd = {
                        //TODO вызвать коллбэк и отправить новые координаты с ID
                    }
                )
            }
    ) {
        Text(text = text)
    }
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



//                    var offsetX by remember { mutableStateOf(0f) }
//                    var offsetY by remember { mutableStateOf(0f) }
//
//                    var offsetX2 by remember { mutableStateOf(1000f) }
//                    var offsetY2 by remember { mutableStateOf(0f) }
//
//                    OutlinedButton(onClick = {},
//                        colors = ButtonDefaults.buttonColors(containerColor = White),
//                        modifier = Modifier
//                            .offset { IntOffset(offsetX.roundToInt(), offsetY.roundToInt()) }
//                            .pointerInput(Unit) {
//                                detectDragGestures(
//                                    onDrag = { change, dragAmount ->
//                                        change.consume()
//                                        offsetX += dragAmount.x
//                                        offsetY += dragAmount.y
//                                    },
//                                    onDragEnd = {
//                                        if (offsetX in offsetX2 - ROUND..offsetX2 + ROUND &&
//                                            offsetY in offsetY2 - ROUND..offsetY2 + ROUND
//                                        ) {
//                                            Toast
//                                                .makeText(
//                                                    context,
//                                                    "СОВПАЛОСЬ FIRST",
//                                                    Toast.LENGTH_SHORT
//                                                )
//                                                .show()
//                                            println("detectDragGestures! onDragEnd!! FIRST")
//                                            println("СОВПАЛОСЬ!!!")
//                                        }
//                                    }
//                                )
//                            }
//                    ) {
//                        Text(text = "FIRST")
//                    }
//
//                    OutlinedButton(onClick = {},
//                        colors = ButtonDefaults.buttonColors(containerColor = White),
//                        modifier = Modifier
//                            .offset { IntOffset(offsetX2.roundToInt(), offsetY2.roundToInt()) }
//                            .pointerInput(Unit) {
//                                detectDragGestures(
//                                    onDrag = { change, dragAmount ->
//                                        change.consume()
//                                        offsetX2 += dragAmount.x
//                                        offsetY2 += dragAmount.y
//                                    },
//                                    onDragEnd = {
//                                        if (offsetX in offsetX2 - ROUND..offsetX2 + ROUND &&
//                                            offsetY in offsetY2 - ROUND..offsetY2 + ROUND
//                                        ) {
//                                            Toast
//                                                .makeText(
//                                                    context,
//                                                    "СОВПАЛОСЬ SECOND",
//                                                    Toast.LENGTH_SHORT
//                                                )
//                                                .show()
//                                            println("detectDragGestures! onDragEnd!! SECOND")
//                                            println("СОВПАЛОСЬ!!! SECOND")
//                                        }
//                                    }
//                                )
//                            }
//                    ) {
//                        Text(text = "SECOND")
//                    }