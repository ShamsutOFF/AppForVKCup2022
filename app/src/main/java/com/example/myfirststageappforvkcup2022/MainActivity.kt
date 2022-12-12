@file:Suppress("OPT_IN_IS_NOT_ENABLED")

package com.example.myfirststageappforvkcup2022

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.myfirststageappforvkcup2022.ui.theme.*
import com.google.accompanist.flowlayout.FlowRow


private val categories = mutableListOf<String>()
private val buttonEnabled = mutableStateOf(false)
private val DIMENS = Dimens()
val sfproFont = FontFamily(Font(R.font.sfprodisp_regular))

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
        LazyColumn(
            modifier = Modifier
                .padding(dimensionResource(R.dimen.padding_medium))
        ) {
            item {
                FlowRow {
                    CreateTopTextAndButton()
                    CreateChips()
                    DrawButton()
                }
            }
        }
    }
}

@Composable
fun CreateTopTextAndButton() {
    val context = LocalContext.current
    Row(
        modifier = Modifier
            .padding(
                dimensionResource(id = R.dimen.padding_zero),
                dimensionResource(id = R.dimen.padding_medium)
            )
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .weight(3f)
        ) {
            Text(
                fontSize = DIMENS.FONT_SIZE_16,
                color = TransparentWhiteTopText,
                text = "Отметьте то, что вам интересно, чтобы настроить Дзен",
                fontFamily = sfproFont,
            )
        }
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .weight(1.5f)
        )
        {
            Button(
                onClick = {
                    Toast.makeText(context, "Click!", Toast.LENGTH_SHORT).show()
                },
                colors = ButtonDefaults.buttonColors(containerColor = TransparentWhiteTopButton),
            ) {
                Text(
                    fontSize = DIMENS.FONT_SIZE_16,
                    color = Color.White,
                    text = "Позже",
                    textAlign = TextAlign.Center
                )
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

    val filterChipsColors = FilterChipDefaults.filterChipColors(
        selectedContainerColor = Orange,
        containerColor = TransparentWhiteChips
    )

    FilterChip(
        colors = filterChipsColors,
        modifier = Modifier
            .padding(
                dimensionResource(R.dimen.padding_zero),
                dimensionResource(R.dimen.padding_small),
                dimensionResource(R.dimen.padding_small),
                dimensionResource(R.dimen.padding_zero)
            )
            .height(dimensionResource(R.dimen.chip_height)),
        border = null,
        selected = selected,
        onClick = {
            selected = !selected
            if (selected) categories.add(category)
            else categories.remove(category)
            buttonEnabled.value = categories.isNotEmpty()
        },
        label = {
            Text(
                fontSize = DIMENS.FONT_SIZE_16,
                color = White,
                text = category,
                fontFamily = sfproFont,
            )
        },
        trailingIcon = if (selected) {
            {
                Divider(
                    color = White,
                    modifier = Modifier
                        .height(dimensionResource(id = R.dimen.divider_height))
                        .width(dimensionResource(id = R.dimen.divider_width))
                        .alpha(DIMENS.ALPHA_INVISIBLE)
                )
                Icon(
                    modifier = Modifier.size(dimensionResource(id = R.dimen.icon_size)),
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
                        .height(dimensionResource(id = R.dimen.divider_height))
                        .width(dimensionResource(id = R.dimen.divider_width))
                )
                Icon(
                    modifier = Modifier.size(dimensionResource(id = R.dimen.icon_size)),
                    tint = White,
                    imageVector = Icons.Filled.Add,
                    contentDescription = "Localized description"
                )
            }
        }
    )

}

@Composable
fun DrawButton() {
    val context = LocalContext.current
    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
        Button(
            enabled = buttonEnabled.value,
            onClick = {
                Toast.makeText(context, "Вы выбрали: $categories", Toast.LENGTH_SHORT).show()
            },
            colors = ButtonDefaults.buttonColors(containerColor = White),
            modifier = Modifier
                .padding(
                    dimensionResource(R.dimen.padding_zero),
                    dimensionResource(R.dimen.padding_big),
                    dimensionResource(R.dimen.padding_zero),
                    dimensionResource(R.dimen.padding_zero)
                )
                .height(80.dp)
                .width(211.dp)
                .alpha(if (buttonEnabled.value) DIMENS.ALPHA_VISIBLE else DIMENS.ALPHA_INVISIBLE),
        ) {
            Text(
                fontSize = DIMENS.FONT_SIZE_18,
                color = Color.Black,
                text = "Продолжить",
                textAlign = TextAlign.Center
            )
        }
    }
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