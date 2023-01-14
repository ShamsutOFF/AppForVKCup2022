package com.example.appforvkcup2022.second_stage.pages.third_page

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.*
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
    LazyColumn() {
        items(count = Int.MAX_VALUE) { count ->
            ElevatedCard(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth()
            ) {
                TextLoader(ThirdPageViewModel())
            }
        }
    }
}


@Composable
private fun TextLoader(viewModel: ThirdPageViewModel) {
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
                    ReplaceableInputText(missingWords[id++])
                    tempText = ""
                }
            }
        }
    }
}

@Composable
private fun OrdinaryText(string: String) {
    Text(text = string, fontSize = 25.sp)
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun ReplaceableInputText(missingText: Pair<String, Int>) {
    val keyboardController = LocalSoftwareKeyboardController.current
    var text by remember {
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