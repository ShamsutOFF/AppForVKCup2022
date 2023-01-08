package com.example.appforvkcup2022.second_stage

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
                StarsRatingBar(rating = 0)
            }
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun StarsRatingBar(
    rating: Int
) {
    var animated by remember { mutableStateOf(false) }
    val rotation = remember { Animatable(initialValue = 360f) }

//    val transition = updateTransition(targetState = animated, label = "Star")
//    val sizeValue by transition.animateDp(
////    val sizeValue by transition.animateDp(
////        transitionSpec = { repeatable(2,tween(durationMillis = 1000), repeatMode = RepeatMode.Reverse) },
////        transitionSpec = { tween(durationMillis = 1000) },
//
//        label = "",
//    ) { screenState ->
//
//        if (screenState) {
//            72.dp
//        } else {
//            64.dp
//        }
//    }

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
//                    .size(sizeValue)
                    .size(size)
                    .graphicsLayer { rotationY = rotation.value }
                    .pointerInteropFilter {
                        println("it.action - ${it.action}")
                        when (it.action) {
                            MotionEvent.ACTION_DOWN -> {
                                selected = true
                                ratingState = i
                                animated = !animated
                            }
                            MotionEvent.ACTION_UP -> selected = false
//                            MotionEvent.ACTION_UP -> {
//                                selected = true
//                                ratingState = i
//                                animated = !animated
//                            }
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