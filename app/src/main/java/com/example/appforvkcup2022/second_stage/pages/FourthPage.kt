package com.example.appforvkcup2022.second_stage.pages

import android.util.Log
import androidx.compose.animation.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.consumeAllChanges
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.flowlayout.FlowMainAxisAlignment
import com.google.accompanist.flowlayout.FlowRow
import kotlin.math.roundToInt

@Composable
fun DrawFourthPage() {
    LazyColumn() {
        items(count = Int.MAX_VALUE) { count ->
            ElevatedCard(modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()) {
                TextAndButtonsLoader(FourthPageViewModel())
            }
        }
    }
}


@Composable
fun TextAndButtonsLoader(viewModel: FourthPageViewModel) {
    val question = viewModel.getRandomTextAndWords()
    val text = question.text
    val missingWords = question.words
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
                    OrdinaryText(string = tempText)
                }
                if (it == ' ') {
                    tempText += it
                    OrdinaryText(string = tempText)
                    tempText = ""
                }
                if (it != '$') tempText += it
                else {
                    OrdinaryText(tempText)
                    ReplaceableText(viewModel, ++id)
                    tempText = ""
                }
            }
        }
        FlowRow(mainAxisSpacing = 5.dp, mainAxisAlignment = FlowMainAxisAlignment.Center) {
            missingWords.shuffled().forEach { ButtonForPlace(viewModel, it.first, it.second) }
        }
    }

}

@Composable
fun OrdinaryText(string: String) {
    Text(text = string, fontSize = 25.sp)
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun ReplaceableText(viewModel: FourthPageViewModel, id: Int) {
    var matchingWord by remember {
        mutableStateOf(" _____ ")
    }
    viewModel.matchingWords.forEach {
        if (id == it.second) matchingWord = it.first
    }
    AnimatedContent(targetState = matchingWord) { text ->
        Text(text = text, fontSize = 25.sp, modifier = Modifier.onGloballyPositioned {
            viewModel.addTextPosition(
                it.localToWindow(Offset.Zero), id
            )
        })
    }
}

@Composable
fun ButtonForPlace(viewModel: FourthPageViewModel, text: String, id: Int) {
    var buttonPosition by remember { mutableStateOf(Offset.Zero) }
    var buttonOffset by remember { mutableStateOf(Offset.Zero) }
    var visible by remember { mutableStateOf(true) }

    AnimatedVisibility(
        visible = visible,
        exit = slideOutVertically() + shrinkVertically() + fadeOut()
    ) {
        OutlinedButton(onClick = {
            visible = false
            Log.d("ElevatedButton", "*** buttonPosition = $buttonPosition")
            Log.d("ElevatedButton", "*** buttonOffset = $buttonOffset")
        },
            border = BorderStroke(2.dp, Color.LightGray),
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
                            Log.d("ElevatedButton", "*** buttonPosition = $buttonPosition")
                            if (!viewModel.checkMatching(buttonPosition, text, id))
                                buttonOffset = Offset.Zero
                            else visible = false
                        }
                    )
                }
        ) {
            Text(text = text, fontSize = 20.sp, color = Color.LightGray)
        }
    }
}