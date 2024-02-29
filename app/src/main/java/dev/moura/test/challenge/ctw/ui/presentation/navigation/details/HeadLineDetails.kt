package dev.moura.test.challenge.ctw.ui.presentation.navigation.details

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class HeadLineDetails(
    val title: String,
    val imageUrl: String,
    val content: String
) : Parcelable
