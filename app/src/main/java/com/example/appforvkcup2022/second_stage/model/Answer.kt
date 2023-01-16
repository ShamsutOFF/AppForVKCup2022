package com.example.appforvkcup2022.second_stage.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Answer(
    val answer: String,
    var percent: Int,
    val right: Boolean
) : Parcelable