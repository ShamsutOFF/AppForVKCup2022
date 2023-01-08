package com.example.appforvkcup2022.second_stage

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun DrawSecondPage() {
    LazyColumn() {
        items(count = Int.MAX_VALUE) { count ->
            ElevatedCard(modifier = Modifier.padding(3.dp)) {
                Text(text = "Second Page", fontSize = 22.sp)            }
        }
    }
}