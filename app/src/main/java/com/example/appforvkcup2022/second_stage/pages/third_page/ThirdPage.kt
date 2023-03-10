package com.example.appforvkcup2022.second_stage.pages.third_page

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.appforvkcup2022.ui.theme.Orange
import com.google.accompanist.flowlayout.FlowMainAxisAlignment
import com.google.accompanist.flowlayout.FlowRow

@Composable
fun DrawThirdPage() {
    val lazyListState: LazyListState = rememberLazyListState()
    LazyColumn(state = lazyListState) {
        items(count = Int.MAX_VALUE) { count ->
            ElevatedCard(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth()
            ) {
                CreateText(ThirdPageViewModel())
            }
        }
    }
}

@Composable
private fun CreateText(viewModel: ThirdPageViewModel) {
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
                    DrawReplaceableInputText(missingWords[id++])
                    tempText = ""
                }
            }
        }
    }
}

@Composable
private fun DrawOrdinaryText(string: String) {
    Text(text = string, fontSize = 25.sp)
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun DrawReplaceableInputText(missingText: Pair<String, Int>) {
    val keyboardController = LocalSoftwareKeyboardController.current
    var text by rememberSaveable {
        mutableStateOf("          ")
    }
    BasicTextField(
        value = text, onValueChange = {
            text = it
        },
        textStyle = TextStyle(
            color = Orange,
            fontSize = 25.sp,
            textDecoration = TextDecoration.Underline
        ),
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
        keyboardActions = KeyboardActions(
            onDone = {
                keyboardController?.hide()
                repeat(10) {
                    text = text.removeSuffix(" ")
                    text = text.removePrefix(" ")
                }
                if (text.equals(missingText.first, true)) {
                    println("equals!!!")
                } else text = "          "
            })
    )
}