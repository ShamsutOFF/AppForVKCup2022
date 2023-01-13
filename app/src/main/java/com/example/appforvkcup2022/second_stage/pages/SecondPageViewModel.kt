package com.example.appforvkcup2022.second_stage.pages

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.geometry.Offset
import androidx.lifecycle.ViewModel
import kotlin.random.Random

private const val ROUND = 150

class SecondPageViewModel : ViewModel() {
    var positionFirstColumnButtons = mutableMapOf<Int, Offset>()
    var positionSecondColumnButtons = mutableMapOf<Int, Offset>()
    var matchingButtons = mutableStateListOf<Int>()
    private set

//    fun getRandomOffsetOutsideTheScreen(): Offset {
//        val randomPoints = listOf(
//            Random.nextInt(1500, 6500).toFloat(),
//            Random.nextInt(-5500, -500).toFloat(),
//            Random.nextInt(1500, 6500).toFloat(),
//            Random.nextInt(-5500, -500).toFloat()
//        )
//        return Offset(
//            randomPoints[Random.nextInt(randomPoints.size)],
//            randomPoints[Random.nextInt(randomPoints.size)]
//        )
//    }

    fun updatePosition(position :Offset, id: Int, column : Int){
        if (column == 1)
        positionFirstColumnButtons += id to position
        else positionSecondColumnButtons += id to position
    }
    fun checkMatching(): Boolean {
        var match: Boolean = false
        positionFirstColumnButtons.forEach {
            val firstOffset = it.value
            val secondOffset = positionSecondColumnButtons[it.key]
            if (secondOffset != null) {
                if (firstOffset.x in secondOffset.x - ROUND..secondOffset.x + ROUND &&
                    firstOffset.y in secondOffset.y - ROUND..secondOffset.y + ROUND
                ) {
                    matchingButtons.add(it.key)
                    match = true
                }
                    else
                        match = false
            }
        }
        return match
    }

    fun getRandomComparison(): Comparison {
        val forms = getAllComparison()
        return forms[Random.nextInt(forms.size)]
    }

    private fun getAllComparison(): List<Comparison> {
        return listOf(
            Comparison(
                listOf(
                    Pair("Один", "1"),
                    Pair("Два", "2"),
                    Pair("Три", "3"),
                    Pair("Четыре", "4")
                )
            ),
            Comparison(
                listOf(
                    Pair("Огурец", "Зеленый"),
                    Pair("Помидор", "Красный"),
                    Pair("Тыква", "Оранжевый"),
                    Pair("Лук", "Желтый")
                )
            ),
            Comparison(
                listOf(
                    Pair("Медведь", "Берлога"),
                    Pair("Лиса", "Нора"),
                    Pair("Белка", "Дупло"),
                    Pair("Птица", "Гнездо")
                )
            ),
            Comparison(
                listOf(
                    Pair("Кошка", "Мяукает"),
                    Pair("Собака", "Лает"),
                    Pair("Лошадь", "Ржет"),
                    Pair("Овца", "Блеет")
                )
            ),
            Comparison(
                listOf(
                    Pair("Январь", "Зима"),
                    Pair("Апрель", "Весна"),
                    Pair("Июль", "Лето"),
                    Pair("Сентябрь", "Осень")
                )
            )
        )
    }

}

data class Comparison(
    val pairs: List<Pair<String, String>>,
)
