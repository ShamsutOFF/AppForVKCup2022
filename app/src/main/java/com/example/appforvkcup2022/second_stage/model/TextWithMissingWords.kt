package com.example.appforvkcup2022.second_stage.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TextWithMissingWords(
    val text: String,
    val words: List<Pair<String, Int>>
) : Parcelable