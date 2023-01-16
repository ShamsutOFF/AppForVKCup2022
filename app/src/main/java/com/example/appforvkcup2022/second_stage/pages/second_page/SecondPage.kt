package com.example.appforvkcup2022.second_stage.pages.second_page

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import com.example.appforvkcup2022.ui.theme.White
import kotlin.math.roundToInt

@Composable
fun DrawSecondPage() {
    val lazyListState: LazyListState = rememberLazyListState()
    LazyColumn(state = lazyListState) {
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
    val comparison = rememberSaveable {
        viewModel.getRandomComparison()
    }
    var id = 0
    val firstPartWithId = mutableListOf<Pair<String, Int>>()
    val secondPartWithId = mutableListOf<Pair<String, Int>>()

    comparison.pairs.forEach {
        firstPartWithId.add(Pair(it.first, id))
        secondPartWithId.add(Pair(it.second, id))
        id++
    }

    val firstColumnShuffled = rememberSaveable {
        firstPartWithId.shuffled()
    }
    val secondColumnShuffled = rememberSaveable {
        secondPartWithId.shuffled()
    }

    var positionFirstColumnButtons = mutableMapOf<Int, Offset>()
    var positionSecondColumnButtons = mutableMapOf<Int, Offset>()
    var matchingButtons = remember { mutableStateListOf<Int>() }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        CreateColumn(viewModel, firstColumnShuffled, 1)
        CreateColumn(viewModel, secondColumnShuffled, 2)
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
    var match by rememberSaveable { mutableStateOf(false) }
    match = viewModel.matchingButtons.contains(id)
    viewModel.matchingButtons.forEach { println("matchingButtons contains $it") }

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