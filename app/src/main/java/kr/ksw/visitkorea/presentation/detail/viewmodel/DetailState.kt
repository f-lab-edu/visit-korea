package kr.ksw.visitkorea.presentation.detail.viewmodel

import kr.ksw.visitkorea.data.remote.dto.DetailCommonDTO
import kr.ksw.visitkorea.data.remote.dto.DetailImageDTO
import kr.ksw.visitkorea.domain.usecase.model.CommonDetail

data class DetailState(
    val title: String = "",
    val firstImage: String = "",
    val address: String = "",
    val dist: String? = null,
    val contentTypeId: String = "",
    val eventStartDate: String? = null,
    val eventEndDate: String? = null,
    val detailCommon: DetailCommonDTO = DetailCommonDTO("",""),
    val detailIntro: CommonDetail = CommonDetail(),
    val images: List<DetailImageDTO> = emptyList(),
)