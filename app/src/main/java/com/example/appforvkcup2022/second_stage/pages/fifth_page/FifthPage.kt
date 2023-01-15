package com.example.appforvkcup2022.second_stage.pages.fifth_page

import android.view.MotionEvent
import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.appforvkcup2022.R
import com.example.appforvkcup2022.ui.theme.Orange

@Composable
fun DrawFifthPage() {
    LazyColumn() {
        items(count = Int.MAX_VALUE) { count ->
            ElevatedCard(modifier = Modifier.padding(3.dp)) {
                CreateStarsRatingBar(rating = 0)
            }
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun CreateStarsRatingBar(
    rating: Int
) {
    var animated by remember { mutableStateOf(false) }
    val rotation = remember { Animatable(initialValue = 360f) }
    var ratingState by remember { mutableStateOf(rating) }
    var selected by remember { mutableStateOf(false) }

    LaunchedEffect(animated) {
        rotation.animateTo(
            targetValue = if (animated) 0f else 360f,
            animationSpec = tween(durationMillis = 1000)
        )
    }

    val size by animateDpAsState(
        targetValue = if (selected) 72.dp else 64.dp,
        spring(Spring.DampingRatioMediumBouncy)
    )

    Row(
        modifier = Modifier.fillMaxSize(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        for (i in 1..5) {
            Icon(
                painter = painterResource(
                    id =
                    if (i <= ratingState)
                        R.drawable.star_filled
                    else R.drawable.star_border
                ),
                contentDescription = "star",
                modifier = Modifier
                    .size(size)
                    .graphicsLayer { if (i <= ratingState) rotationY = rotation.value }
                    .pointerInteropFilter {
                        when (it.action) {
                            MotionEvent.ACTION_DOWN -> {
                                if (ratingState == i) {
                                    ratingState = 0
                                } else {
                                    ratingState = i
                                }
                                selected = true
                                animated = !animated

                            }
                            MotionEvent.ACTION_UP -> selected = false
                            MotionEvent.ACTION_MOVE -> selected = false
                            MotionEvent.ACTION_CANCEL -> selected = false
                        }
                        true
                    },
                tint = Orange
            )
        }
    }
}