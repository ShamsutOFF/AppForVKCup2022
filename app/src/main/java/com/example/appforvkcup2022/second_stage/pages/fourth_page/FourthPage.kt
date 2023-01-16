package com.example.appforvkcup2022.second_stage.pages.fourth_page

import androidx.compose.animation.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.consumeAllChanges
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.appforvkcup2022.ui.theme.Orange
import com.google.accompanist.flowlayout.FlowMainAxisAlignment
import com.google.accompanist.flowlayout.FlowRow
import kotlin.math.roundToInt

@Composable
fun DrawFourthPage() {
    val lazyListState: LazyListState = rememberLazyListState()
    LazyColumn(state = lazyListState) {
        items(count = Int.MAX_VALUE) { count ->
            ElevatedCard(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth()
            ) {
                CreateTextAndButtons(FourthPageViewModel())
            }
        }
    }
}


@Composable
private fun CreateTextAndButtons(viewModel: FourthPageViewModel) {
    val question = rememberSaveable {
        viewModel.getRandomTextAndWords()
    }
    val text = rememberSaveable {
        question.text
    }
    val missingWords = rememberSaveable {
        question.words
    }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        FlowRow(
            mainAxisAlignment = FlowMainAxisAlignment.Start,
            modifier = Modifier.fillMaxWidth()
        ) {
            var tempText = " "
            var id = 0
            text.forEachIndexed { index, it ->
                if (index == text.lastIndex) {
                    tempText += it
                    DrawOrdinaryText(string = tempText)
                }
                if (it == ' ') {
                    tempText += it
                    DrawOrdinaryText(string = tempText)
                    tempText = ""
                }
                if (it != '$') tempText += it
                else {
                    DrawOrdinaryText(tempText)
                    DrawReplaceableText(viewModel, ++id)
                    tempText = ""
                }
            }
        }
        FlowRow(mainAxisSpacing = 5.dp, mainAxisAlignment = FlowMainAxisAlignment.Center) {
            missingWords.shuffled().forEach { DrawButtonForPlace(viewModel, it.first, it.second) }
        }
    }

}

@Composable
private fun DrawOrdinaryText(string: String) {
    Text(text = string, fontSize = 25.sp)
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
private fun DrawReplaceableText(viewModel: FourthPageViewModel, id: Int) {
    var matchingWord by rememberSaveable {
        mutableStateOf(" _____ ")
    }
    viewModel.matchingWords.forEach {
        if (id == it.second) matchingWord = it.first
    }
    AnimatedContent(targetState = matchingWord) { text ->
        Text(
            text = text,
            color = Orange,
            fontSize = 25.sp,
            modifier = Modifier.onGloballyPositioned {
                viewModel.addTextPosition(
                    it.localToWindow(Offset.Zero), id
                )
            })
    }
}

@Composable
private fun DrawButtonForPlace(viewModel: FourthPageViewModel, text: String, id: Int) {
    var buttonPosition by remember { mutableStateOf(Offset.Zero) }
    var buttonOffset by remember { mutableStateOf(Offset.Zero) }
    var visible by rememberSaveable { mutableStateOf(true) }

    AnimatedVisibility(
        visible = visible,
        exit = slideOutVertically() + shrinkVertically() + fadeOut()
    ) {
        OutlinedButton(onClick = { },
            border = BorderStroke(2.dp, Orange),
            modifier = Modifier
                .onGloballyPositioned {
                    buttonPosition = it.localToWindow(
                        Offset.Zero
                    )
                }
                .offset {
                    IntOffset(
                        buttonOffset.x.roundToInt(),
                        buttonOffset.y.roundToInt()
                    )
                }
                .pointerInput(Unit) {
                    detectDragGestures(
                        onDrag = { change, dragAmount ->
                            change.consumeAllChanges()
                            buttonOffset += dragAmount
                        },
                        onDragEnd = {
                            buttonPosition += buttonOffset
                            if (!viewModel.checkMatching(buttonPosition, text, id))
                                buttonOffset = Offset.Zero
                            else visible = false
                        }
                    )
                }
        ) {
            Text(text = text, fontSize = 20.sp, color = Orange)
        }
    }
}