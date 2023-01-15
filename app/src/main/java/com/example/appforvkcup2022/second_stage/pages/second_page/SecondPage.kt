package com.example.appforvkcup2022.second_stage.pages.second_page

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.appforvkcup2022.ui.theme.White
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
                CreateComparison(SecondPageViewModel())
            }
        }
    }
}

@Composable
private fun CreateComparison(viewModel: SecondPageViewModel) {
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
            DrawButton(
                viewModel = viewModel,
                text = it.first,
                id = it.second,
                column = column
            )
        }
    }
}

@Composable
private fun DrawButton(viewModel: SecondPageViewModel, text: String, id: Int, column: Int) {

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
                            if (!viewModel.checkMatching()) offset = Offset.Zero
                        }
                    )
                }
        ) {
            Text(text = text, fontSize = 20.sp)
        }
    }
}