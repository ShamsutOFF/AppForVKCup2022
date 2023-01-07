package com.example.appforvkcup2022.second_stage

import android.content.res.Resources.Theme
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import com.google.accompanist.pager.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class)
@Composable
fun SecondStageScreen() {

    val pagerState = rememberPagerState(0)
    val scope = rememberCoroutineScope()
    val scaffoldState = rememberScaffoldState()

    Scaffold(
        scaffoldState = scaffoldState,
        bottomBar = {
            BottomAppBar(
                backgroundColor = MaterialTheme.colorScheme.background
            ) {
                BottomTabRow(pagerState, scope)
            }
        },
        modifier = Modifier.background(MaterialTheme.colorScheme.background),
        content = {
            HorizontalPager(5, state = pagerState) { page ->
                when (page) {
                    0 -> {
                        CreatePage("Первая страница!")
                    }
                    1 -> {
                        CreatePage("Вторая страница!")
                    }
                    2 -> {
                        CreatePage("Третья страница!")
                    }
                    3 -> {
                        CreatePage("Четвертая страница!")
                    }
                    4 -> {
                        CreatePage("Пятая страница!")
                    }
                }
            }
        }
    )
}

@ExperimentalPagerApi
@Composable
fun BottomTabRow(pagerState: PagerState, scope: CoroutineScope) {
    TabRow(
        selectedTabIndex = pagerState.currentPage,
        indicator = { tabPositions ->
            TabRowDefaults.Indicator(
                Modifier.pagerTabIndicatorOffset(pagerState, tabPositions),
                height = 5.dp,
                color = Color.Gray,
            )

        }

    ) {
        val pages = listOf("1й", "2й", "3й", "4й", "5й")
        pages.forEachIndexed { index, title ->
            Tab(
                text = { androidx.compose.material.Text(title) },
                selected = pagerState.currentPage == index,
                onClick = { scope.launch { pagerState.scrollToPage(index) } },
            )
        }
    }
}

@Composable
fun CreatePage(text:String) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background),
            contentAlignment = Alignment.Center
        ) {
            Text(text = text, fontSize = 22.sp)
        }
    }