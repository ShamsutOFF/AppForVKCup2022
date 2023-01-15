package com.example.appforvkcup2022.second_stage.pages.first_page

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.appforvkcup2022.R
import com.example.appforvkcup2022.first_stage.DIMENS
import com.example.appforvkcup2022.second_stage.model.Answer
import com.example.appforvkcup2022.ui.theme.*

@Composable
fun DrawFirstPage() {
    LazyColumn() {
        items(count = Int.MAX_VALUE) { count ->
            ElevatedCard(
                modifier = Modifier
                    .padding(
                        8.dp,
                        8.dp
                    )
                    .fillMaxWidth()
            ) {
                val viewModel = FirstPageViewModel()
                val form = viewModel.getRandomSurveyForm()
                Column(modifier = Modifier.padding(8.dp)) {
                    Text(
                        modifier = Modifier.padding(3.dp),
                        text = form.question,
                        fontSize = DIMENS.FONT_SIZE_18,
                        fontWeight = FontWeight.ExtraBold
                    )
                    CreateAnswersChipsGroup(viewModel, form.answers.shuffled())
                }
            }
        }
    }
}

@Composable
fun CreateAnswersChipsGroup(viewModel: FirstPageViewModel, answers: List<Answer>) {
    val percents = viewModel.getRandomPercentForAnswers(answers.size)
    var enabled by rememberSaveable {
        mutableStateOf(true)
    }
    answers.forEachIndexed { index, answer ->
        answer.percent = percents[index]
        DrawChip(answer = answer, enabled) { enabled = false }
    }
}

@Composable
private fun DrawChip(answer: Answer, enabled: Boolean, click: () -> Unit) {
    val colorsRight = ButtonDefaults.elevatedButtonColors(disabledContainerColor = Green)
    val colorsWrong = ButtonDefaults.elevatedButtonColors(disabledContainerColor = Red)
    val colorsDefault = ButtonDefaults.elevatedButtonColors()
    var checked by rememberSaveable { mutableStateOf(false) }

    ElevatedButton(
        onClick = {
            checked = true
            click.invoke()
        },
        modifier = Modifier.fillMaxWidth(),
        enabled = enabled,
        shape = RoundedCornerShape(10),
        colors = if (answer.right) colorsRight
        else if (checked) colorsWrong
        else colorsDefault
    ) {
        Column() {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = answer.answer, fontSize = DIMENS.FONT_SIZE_16)
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.width(60.dp)
                ) {
                    Icon(
                        modifier = Modifier
                            .size(dimensionResource(R.dimen.icon_size))
                            .alpha(
                                if (checked) DIMENS.ALPHA_VISIBLE
                                else DIMENS.ALPHA_INVISIBLE
                            ),
                        tint = White,
                        imageVector = Icons.Filled.Done,
                        contentDescription = "Localized Description"
                    )
                    Text(
                        modifier = Modifier.alpha(
                            if (!enabled) DIMENS.ALPHA_VISIBLE
                            else DIMENS.ALPHA_INVISIBLE
                        ),
                        text = answer.percent.toString() + " %", fontSize = DIMENS.FONT_SIZE_16
                    )
                }
            }

            val progress by animateFloatAsState(
                targetValue = if (enabled) 0f else answer.percent / 100.toFloat(),
                animationSpec = tween(durationMillis = 1000, easing = LinearEasing)
            )
            LinearProgressIndicator(
                progress,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(2.dp),
                color = White,
                trackColor = InvisibleColor
            )
        }
    }
}