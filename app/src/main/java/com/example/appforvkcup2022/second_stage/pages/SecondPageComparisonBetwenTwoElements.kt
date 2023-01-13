package com.example.appforvkcup2022.second_stage.pages

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.*
import com.example.appforvkcup2022.ui.theme.White
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

@Composable
fun DrawSecondPage() {
    LazyColumn() {
        items(count = Int.MAX_VALUE) { count ->
            ElevatedCard(
                modifier = Modifier
                    .padding(8.dp, 8.dp)
                    .fillMaxWidth()
            ) {
                CreateComparison()
            }
        }
    }
}

@Composable
private fun CreateComparison() {
    val viewModel = SecondPageViewModel()
    val comparison = viewModel.getRandomComparison()
    var id = 0
    val firstPartWithId = mutableListOf<Pair<String, Int>>()
    val secondPartWithId = mutableListOf<Pair<String, Int>>()

    comparison.pairs.forEach {
        firstPartWithId.add(Pair(it.first, id))
        secondPartWithId.add(Pair(it.second, id))
        id++
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        CreateColumn(viewModel, firstPartWithId.shuffled(), 1)
        CreateColumn(viewModel, secondPartWithId.shuffled(), 2)
    }
}

@Composable
private fun CreateColumn(
    viewModel: SecondPageViewModel,
    shuffled: List<Pair<String, Int>>,
    column: Int
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        shuffled.forEach {
            CreateButton(
                viewModel = viewModel,
                text = it.first,
                id = it.second,
                column = column
            )
        }
    }
}

@Composable
private fun CreateButton(viewModel: SecondPageViewModel, text: String, id: Int, column: Int) {

    var offset by remember { mutableStateOf(Offset.Zero) }
    var position by remember { mutableStateOf(Offset.Zero) }
    var match by remember { mutableStateOf(false) }
    match = viewModel.matchingButtons.contains(id)

    AnimatedVisibility(
        visible = !match,
        exit = slideOutVertically() + shrinkVertically() + fadeOut()
    ) {
        OutlinedButton(onClick = { },
            colors = ButtonDefaults.buttonColors(containerColor = White),
            modifier = Modifier
                .onGloballyPositioned {
                    viewModel.updatePosition(position, id, column)
                    position = it.localToWindow(
                        Offset.Zero
                    )
                }
//                .alpha(alpha)
                .offset {
                    IntOffset(
                        offset.x.roundToInt(),
                        offset.y.roundToInt()
                    )
                }
                .pointerInput(Unit) {
                    detectDragGestures(
                        onDrag = { change, dragAmount ->
                            change.consume()
                            offset += dragAmount
                        },
                        onDragEnd = {
                            position += offset
                            viewModel.updatePosition(position, id, column)
                            viewModel.checkMatching()
                        }
                    )
                }
        ) {
            Text(text = text, fontSize = 20.sp)
        }
    }
}