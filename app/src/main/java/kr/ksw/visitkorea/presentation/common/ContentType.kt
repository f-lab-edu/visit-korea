package kr.ksw.visitkorea.presentation.common

import androidx.annotation.StringRes
import kr.ksw.visitkorea.R

enum class ContentType(
    val contentTypeId: String,
    @StringRes val title: Int
) {
    TOURIST("12", R.string.tourist_spot_title),
    CULTURE("14", R.string.culture_center_title),
    LEiSURE("28", R.string.leisure_sports_title),
    RESTAURANT("39", R.string.restaurant_title)
}