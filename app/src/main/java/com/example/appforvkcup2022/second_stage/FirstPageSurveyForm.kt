package com.example.appforvkcup2022.second_stage

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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.appforvkcup2022.R
import com.example.appforvkcup2022.first_stage.DIMENS
import com.example.appforvkcup2022.second_stage.model.Answer
import com.example.appforvkcup2022.second_stage.model.SurveyForm
import com.example.appforvkcup2022.ui.theme.White
import kotlin.random.Random

@Composable
fun DrawFirstPage() {
    LazyColumn() {
        items(count = Int.MAX_VALUE) { count ->
            ElevatedCard(
                modifier = Modifier
                    .padding(
                        8.dp,
                        3.dp
                    )
                    .fillMaxWidth()
            ) {
                val form = getRandomSurveyForm()
                Column(modifier = Modifier.padding(8.dp)) {
                    Text(modifier = Modifier.padding(3.dp), text = form.question, fontSize = 16.sp)
                    CreateAnswersChipsGroup(form.answers)
                }
            }
        }
    }
}

@Composable
fun CreateAnswersChipsGroup(answers: List<Answer>) {
    val percents = getRandomPercentForAnswers(answers.size)

    answers.forEachIndexed { index, answer ->
        answer.percent = percents[index]
        DrawCheckbox(answer = answer)
    }
}

@Composable
private fun DrawCheckbox(answer: Answer) {

    var enabled by rememberSaveable {
        mutableStateOf(true)
    }
    ElevatedButton(
        onClick = { enabled = !enabled },
        modifier = Modifier.fillMaxWidth(),
        enabled = enabled,
        shape = RoundedCornerShape(10)
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = answer.answer)
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.width(50.dp)
            ) {
                Icon(
                    modifier = Modifier
                        .size(dimensionResource(R.dimen.icon_size))
                        .alpha(
                            if (!enabled && answer.right) DIMENS.ALPHA_VISIBLE
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
                    text = answer.percent.toString() + " %"
                )
            }
        }
    }
}

fun getRandomSurveyForm(): SurveyForm {
    val forms = getSurveyForms()
    return forms[Random.nextInt(forms.size)]
}

fun getSurveyForms(): List<SurveyForm> {
    return listOf(
        SurveyForm(
            "Почему змеи высовывают язык:", listOf(
                Answer("Чтобы напугать хищников", 0, false),
                Answer("Чтобы облизать добычу", 0, false),
                Answer("Чтобы издать шипящий звук", 0, false),
                Answer("Чтобы понюхать воздух", 0, true)
            )
        ),
        SurveyForm(
            "Самая маленькая птица:", listOf(
                Answer("Колибри", 0, true),
                Answer("Снигирь", 0, false),
                Answer("Ворона", 0, false)
            )
        ),
        SurveyForm(
            "Самый кассовый фильм в истории это:", listOf(
                Answer("Титаник", 0, false),
                Answer("Аватар", 0, true),
                Answer("Мстители: Финал", 0, false)
            )
        ),
        SurveyForm(
            "Самая большая планета Солнечной системы это:", listOf(
                Answer("Марс", 0, false),
                Answer("Сатурн", 0, false),
                Answer("Юпитер", 0, true),
                Answer("Венера", 0, false),
                Answer("Земля", 0, false)
            )
        ),
        SurveyForm(
            "В каком году основали ВК:", listOf(
                Answer("2008", 0, false),
                Answer("2006", 0, true),
                Answer("2004", 0, false),
                Answer("2010", 0, false)
            )
        )
    )
}

fun getRandomPercentForAnswers(answersQuantity: Int): List<Int> {
    var maxPercent = 100
    val listOfPercents = mutableListOf<Int>()
    return if (answersQuantity > 1) {
        for (i in 1 until answersQuantity) {
            val randomPercent = Random.nextInt(maxPercent)
            listOfPercents.add(randomPercent)
            maxPercent -= randomPercent
        }
        listOfPercents.add(maxPercent)
        listOfPercents
    } else if (answersQuantity == 1) listOf(100)
    else emptyList()
}