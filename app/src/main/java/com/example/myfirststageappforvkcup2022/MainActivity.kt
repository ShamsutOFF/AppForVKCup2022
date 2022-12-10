package com.example.myfirststageappforvkcup2022

import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myfirststageappforvkcup2022.ui.theme.*
import com.google.accompanist.flowlayout.FlowRow

private const val TAG = "@@@ MainActivity"

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyFirstStageAppForVKCup2022Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    FlowRow(modifier = Modifier.padding(10.dp)) {
                        createChips()
                    }
                }
            }
        }
    }
}


@Composable
fun createChips() {
    val categoriesList = getListOfCategories()
    categoriesList.forEach { DrawCheckbox(it) }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DrawCheckbox(category: String) {
    var selected by remember {
        mutableStateOf(false)
    }

    val sfproFont = FontFamily(Font(R.font.sfprodisp_regular))
    val filterChipsColors = FilterChipDefaults.filterChipColors(
        selectedContainerColor = Orange,
        containerColor = TransparentWhite
    )

    FilterChip(
        colors = filterChipsColors,
        border = null,
        selected = selected,
        onClick = { selected = !selected },
        label = {
            Text(
                fontSize = 16.sp,
                color = White,
                text = category,
                fontFamily = sfproFont,
                textAlign = TextAlign.Center
            )
        },
        trailingIcon = if (selected) {
            {
                Icon(
                    tint = White,
                    imageVector = Icons.Filled.Done,
                    contentDescription = "Localized Description",
                    modifier = Modifier.size(FilterChipDefaults.IconSize)
                )
            }
        } else {
            {
                Divider(
                    color = White,
                    modifier = Modifier
                        .height(20.dp)
                        .width(1.dp)
                )
                Icon(
                    modifier = Modifier.size(18.dp),
                    tint = White,
                    imageVector = Icons.Filled.Add,
                    contentDescription = "Localized description"
                )
            }
        }
    )

}

fun getListOfCategories(): List<String> {
    return listOf(
        "Кино",
        "Автомобили",
        "Мотоциклы",
        "Книги",
        "Музыка",
        "Кино",
        "Сериалы",
        "Спорт",
        "Прогулки",
        "Фотографии",
        "Новости",
        "Юмор",
        "Политика",
        "Рецепты",
        "Еда",
        "Отдых",
        "Рестораны",
        "Путешествия",
        "История",
        "Дети",
        "Лайфхаки",
        "Игры",
        "Для взрослых 18+",
        "Экономика",
        "Технологии"
    )
}