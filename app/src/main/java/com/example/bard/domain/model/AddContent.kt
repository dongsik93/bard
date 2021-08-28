package com.example.bard.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class AddContent(
    val word: String = "",
    val meaning: String = "",
) : Parcelable
