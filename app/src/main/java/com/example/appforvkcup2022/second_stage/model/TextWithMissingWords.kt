package com.example.appforvkcup2022.second_stage.model

data class TextWithMissingWords(
    val text: String,
    val words: List<Pair<String, Int>>
)