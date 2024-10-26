package kr.ksw.visitkorea.presentation.common

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DetailParcel(
    val title: String = "",
    val firstImage: String = "",
    val address: String = "",
    val dist: String? = null,
    val contentId: String = "",
    val contentTypeId: String = "",
    val eventStartDate: String? = null,
    val eventEndDate: String? = null,
): Parcelable