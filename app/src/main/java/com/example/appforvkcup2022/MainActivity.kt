@file:Suppress("OPT_IN_IS_NOT_ENABLED")

package com.example.appforvkcup2022

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.appforvkcup2022.first_stage.FirstStageScreen
import com.example.appforvkcup2022.second_stage.SecondStageScreen
import com.example.appforvkcup2022.ui.theme.AppForVKCup2022Theme

val sfproFont = FontFamily(Font(R.font.sfprodisp_regular))

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppForVKCup2022Theme {
                FirstStageScreen()


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
                                DrawButton ({ navController.navigate("first") },"Первый раунд")
                                DrawButton ({ navController.navigate("second") }, "Второй раунд")
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
fun DrawButton(openScreen: () -> Unit, text:String) {
    Button(onClick = openScreen) {
        Text(text = text, fontSize = 22.sp)
    }
}