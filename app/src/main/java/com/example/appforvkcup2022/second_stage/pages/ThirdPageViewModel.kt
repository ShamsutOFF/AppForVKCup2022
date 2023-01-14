package com.example.appforvkcup2022.second_stage.pages

import androidx.lifecycle.ViewModel
import com.example.appforvkcup2022.second_stage.model.TextWithMissingWords
import kotlin.random.Random

class ThirdPageViewModel : ViewModel() {
    fun getRandomTextAndWords(): TextWithMissingWords {
        val questions = getAllTextAndWords()
        return questions[Random.nextInt(questions.size)]
    }

    private fun getAllTextAndWords(): List<TextWithMissingWords> {
        return listOf(
            TextWithMissingWords(
                "Текст $ несколькими пропусками $ вариантами.",
                listOf(
                    Pair("с", 1),
                    Pair("и", 2)
                )
            ),
            TextWithMissingWords(
                "Изначально данная $ сеть задумывалась как $. Рабочим названием была — Studlist.ru",
                listOf(
                    Pair("социальная", 1),
                    Pair("студенческая", 2)
                )
            ),
            TextWithMissingWords(
                "Средний $ сотрудников компании ВКонтакте — 26 лет. Самому младшему — всего 15.",
                listOf(
                    Pair("возраст", 1)
                )
            ),
            TextWithMissingWords(
                "На данный момент, единственным акционером соцсети $ является $.",
                listOf(
                    Pair("ВКонтакте", 1),
                    Pair("Mail.ru Group", 2)
                )
            ),
            TextWithMissingWords(
                "Штаб-квартира $ располагается в Доме компании $.",
                listOf(
                    Pair("ВКонтакте", 1),
                    Pair("Зингер", 2)
                )
            )
        )
    }
}