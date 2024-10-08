package kr.ksw.visitkorea.presentation.detail.viewmodel

import kr.ksw.visitkorea.data.remote.dto.DetailCommonDTO
import kr.ksw.visitkorea.data.remote.dto.DetailImageDTO
import kr.ksw.visitkorea.domain.usecase.model.CommonDetail

data class DetailState(
    val detailCommon: DetailCommonDTO = DetailCommonDTO("",""),
    val detailIntro: CommonDetail = CommonDetail(),
    val images: List<DetailImageDTO> = emptyList()
)