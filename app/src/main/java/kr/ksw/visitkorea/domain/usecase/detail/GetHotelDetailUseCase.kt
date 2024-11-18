package kr.ksw.visitkorea.domain.usecase.detail

import kr.ksw.visitkorea.domain.usecase.model.HotelDetail

interface GetHotelDetailUseCase {
    suspend operator fun invoke(
        contentId: String,
    ): Result<HotelDetail>
}