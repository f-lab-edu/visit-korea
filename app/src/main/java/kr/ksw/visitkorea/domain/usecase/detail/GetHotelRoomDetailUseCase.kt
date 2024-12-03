package kr.ksw.visitkorea.domain.usecase.detail

import kr.ksw.visitkorea.domain.model.HotelRoomDetail

interface GetHotelRoomDetailUseCase {
    suspend operator fun invoke(
        contentId: String
    ): Result<List<HotelRoomDetail>>
}