package kr.ksw.visitkorea.presentation.home.mapper

import kr.ksw.visitkorea.domain.common.TYPE_RESTAURANT
import kr.ksw.visitkorea.domain.common.TYPE_TOURIST_SPOT
import kr.ksw.visitkorea.domain.model.CardData
import kr.ksw.visitkorea.domain.model.CommonCardModel
import kr.ksw.visitkorea.domain.model.Restaurant
import kr.ksw.visitkorea.domain.model.TouristSpot
import kr.ksw.visitkorea.presentation.home.viewmodel.HomePagerItem

fun CardData.toHomePagerItem(): HomePagerItem {
    val pagerItem = HomePagerItem(
        title = title,
        address = address,
        dist = dist,
        image = firstImage,
        contentId = contentId,
    )
    return when(this) {
        is CommonCardModel -> pagerItem.copy(
            contentTypeId = contentTypeId
        )
        is Restaurant -> pagerItem.copy(
            contentTypeId = TYPE_RESTAURANT
        )
        is TouristSpot -> pagerItem.copy(
            contentTypeId = TYPE_TOURIST_SPOT
        )
    }
}