@file:Suppress("OPT_IN_IS_NOT_ENABLED")

package com.example.appforvkcup2022

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.appforvkcup2022.first_stage.DIMENS
import com.example.appforvkcup2022.first_stage.FirstStageScreen
import com.example.appforvkcup2022.second_stage.SecondStageScreen
import com.example.appforvkcup2022.ui.theme.AppForVKCup2022Theme
import com.example.appforvkcup2022.ui.theme.White

val sfproFont = FontFamily(Font(R.font.sfprodisp_regular))

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppForVKCup2022Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background,
                ) {
                    val navController = rememberNavController()
                    NavHost(navController = navController, startDestination = "main") {
                        composable("main") {
                            Column(
                                verticalArrangement = Arrangement.SpaceEvenly,
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier
//                                    .background(Color.Red)
                                    .fillMaxSize()
                            ) {
                                DrawButton({ navController.navigate("first") }, "Первый раунд")
                                DrawButton({ navController.navigate("second") }, "Второй раунд")
                            }
                        }
                        composable("first") { FirstStageScreen() }
                        composable("second") { SecondStageScreen() }
                    }

                }

            }
        }
    }
}


@Composable
fun DrawButton(openScreen: () -> Unit, text: String) {
    Button(
        onClick = openScreen,
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
    ) {
        Text(
            fontSize = DIMENS.FONT_SIZE_18,
            color = Color.Black,
            text = text,
            textAlign = TextAlign.Center
        )
    }
}