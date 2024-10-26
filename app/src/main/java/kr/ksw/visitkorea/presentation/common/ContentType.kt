package kr.ksw.visitkorea.presentation.common

import androidx.annotation.StringRes
import kr.ksw.visitkorea.R
import kr.ksw.visitkorea.domain.common.*

enum class ContentType(
    val contentTypeId: String,
    @StringRes val title: Int
) {
    TOURIST(TYPE_TOURIST_SPOT, R.string.tourist_spot_title),
    CULTURE(TYPE_CULTURE, R.string.culture_center_title),
    LEiSURE(TYPE_LEiSURE, R.string.leisure_sports_title),
    RESTAURANT(TYPE_RESTAURANT, R.string.restaurant_title)
}