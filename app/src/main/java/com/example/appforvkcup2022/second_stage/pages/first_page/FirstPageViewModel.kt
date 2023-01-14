package com.example.appforvkcup2022.second_stage.pages.first_page

import androidx.lifecycle.ViewModel
import com.example.appforvkcup2022.second_stage.model.Answer
import com.example.appforvkcup2022.second_stage.model.SurveyForm
import kotlin.random.Random

class FirstPageViewModel : ViewModel() {

    fun getRandomSurveyForm(): SurveyForm {
        val forms = getSurveyForms()
        return forms[Random.nextInt(forms.size)]
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

}

private fun getSurveyForms(): List<SurveyForm> {
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
                Answer("Снегирь", 0, false),
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
