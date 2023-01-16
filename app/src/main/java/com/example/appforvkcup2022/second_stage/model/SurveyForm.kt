package com.example.appforvkcup2022.second_stage.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SurveyForm(
    val question: String,
    val answers: List<Answer>
) : Parcelable
