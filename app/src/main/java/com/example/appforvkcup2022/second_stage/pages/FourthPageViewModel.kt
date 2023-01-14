package com.example.appforvkcup2022.second_stage.pages

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.geometry.Offset
import androidx.lifecycle.ViewModel
import kotlin.random.Random

private const val ROUND = 150

class FourthPageViewModel : ViewModel() {

    var matchingWords = mutableStateListOf<Pair<String, Int>>()
        private set

    var replaceableTextPosition = mutableListOf<Pair<Offset, Int>>()
        private set

    private fun getAllTextAndWords(): List<TextWithMissingWords> {
        return listOf(
            TextWithMissingWords(
                "Текст $ несколькими пропусками $ вариантами.",
                listOf(
                    Pair("с", 1),
                    Pair("и", 2),
                    Pair("один", 0),
                    Pair("два", 0)
                )
            ),
            TextWithMissingWords(
                "Изначально данная $ сеть задумывалась как $. Рабочим названием была — Studlist.ru",
                listOf(
                    Pair("социальная", 1),
                    Pair("студенческая", 2),
                    Pair("рабочая", 0),
                    Pair("историческая", 0)
                )
            ),
            TextWithMissingWords(
                "Средний $ сотрудников компании ВКонтакте — 26 лет. Самому младшему — всего 15.",
                listOf(
                    Pair("возраст", 1),
                    Pair("рост", 0),
                    Pair("вес", 0)
                )
            ),
            TextWithMissingWords(
                    "На данный момент, единственным акционером соцсети $ является $.",
                listOf(
                    Pair("ВКонтакте", 1),
                    Pair("Mail.ru Group", 2),
                    Pair("Яндекс", 0),
                    Pair("Павел Дуров", 0)
                )
            ),
            TextWithMissingWords(
                    "Штаб-квартира $ располагается в Доме компании $.",
                listOf(
                    Pair("ВКонтакте", 1),
                    Pair("Зингер", 2),
                    Pair("Mail.ru Group", 0),
                    Pair("Яндекс", 0)
                )
            )
        )
    }

    fun getRandomTextAndWords(): TextWithMissingWords {
        val questions = getAllTextAndWords()
return questions[Random.nextInt(questions.size)]
    }

    fun addTextPosition(position: Offset, id: Int) {
        replaceableTextPosition.add(Pair(position, id))
    }

    fun checkMatching(buttonOffset: Offset, buttonText: String, buttonId: Int): Boolean {
        var match = false
        replaceableTextPosition.forEach {
            if (buttonId == it.second
                && buttonOffset.x in it.first.x - ROUND..it.first.x + ROUND
                && buttonOffset.y in it.first.y - ROUND..it.first.y + ROUND
            ) match = true
        }
        if (match) matchingWords.add(Pair(buttonText, buttonId))
        return match
    }
}

data class TextWithMissingWords(
    val text: String,
    val words: List<Pair<String, Int>>
)