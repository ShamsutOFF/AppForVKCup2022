package com.example.myfirststageappforvkcup2022

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myfirststageappforvkcup2022.ui.theme.MyFirstStageAppForVKCup2022Theme
import com.example.myfirststageappforvkcup2022.ui.theme.Orange
import com.example.myfirststageappforvkcup2022.ui.theme.TransparentWhite
import com.example.myfirststageappforvkcup2022.ui.theme.White
import com.google.accompanist.flowlayout.FlowRow

private const val TAG = "@@@ MainActivity"

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyFirstStageAppForVKCup2022Theme {
                MainScreen()
            }
        }
    }
}

@Composable
private fun MainScreen() {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        LazyColumn {
            item {
                FlowRow(
                    modifier = Modifier
                    .padding(dimensionResource(R.dimen.padding_small))
                ) {
                    CreateChips()
                }
            }
        }
    }
}

@Composable
fun CreateChips() {
    val categoriesList = getListOfCategories()
    categoriesList.forEach { DrawCheckbox(it) }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DrawCheckbox(category: String) {
    var selected by rememberSaveable {
        mutableStateOf(false)
    }

    val sfproFont = FontFamily(Font(R.font.sfprodisp_regular))
    val filterChipsColors = FilterChipDefaults.filterChipColors(
        selectedContainerColor = Orange,
        containerColor = TransparentWhite
    )

    FilterChip(
        colors = filterChipsColors,
        modifier = Modifier.padding(
            dimensionResource(R.dimen.padding_zero),
            dimensionResource(R.dimen.padding_zero),
            dimensionResource(R.dimen.padding_small),
            dimensionResource(R.dimen.padding_zero)
        ),
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
                Divider(
                    color = White,
                    modifier = Modifier
                        .height(20.dp)
                        .width(1.dp)
                        .alpha(0.0f)
                )
                Icon(
                    modifier = Modifier.size(18.dp),
                    tint = White,
                    imageVector = Icons.Filled.Done,
                    contentDescription = "Localized Description"
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