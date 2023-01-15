package com.example.appforvkcup2022.second_stage

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.appforvkcup2022.second_stage.pages.fifth_page.DrawFifthPage
import com.example.appforvkcup2022.second_stage.pages.first_page.DrawFirstPage
import com.example.appforvkcup2022.second_stage.pages.fourth_page.DrawFourthPage
import com.example.appforvkcup2022.second_stage.pages.second_page.DrawSecondPage
import com.example.appforvkcup2022.second_stage.pages.third_page.DrawThirdPage
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState

@OptIn(ExperimentalPagerApi::class)
@Composable
fun SecondStageScreen() {
    Scaffold(
        modifier = Modifier.fillMaxSize().padding(0.dp, 32.dp, 0.dp, 0.dp)
    ) { padding ->
        Column(
            Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(padding)
        ) {
            val pagerState = rememberPagerState()

            HorizontalPager(
                count = 5,
                state = pagerState,
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
            ) {
                when (it){
                    0 -> DrawFirstPage()
                    1 -> DrawSecondPage()
                    2 -> DrawThirdPage()
                    3 -> DrawFourthPage()
                    4 -> DrawFifthPage()
                }
            }
            HorizontalPagerIndicator(
                pagerState = pagerState,
                activeColor = Color.White,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(16.dp, 16.dp, 16.dp, 16.dp),
            )
        }
    }
}