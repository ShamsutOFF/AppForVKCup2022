package com.example.appforvkcup2022.second_stage.model

data class SurveyForm(
    val question: String,
    val answers: List<Answer>
)

data class Answer(
    val answer: String,
    var percent: Int?,
    val right: Boolean
    )