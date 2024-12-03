package kr.ksw.visitkorea.domain.usecase.detail

import kr.ksw.visitkorea.data.repository.DetailRepository
import kr.ksw.visitkorea.domain.usecase.mapper.toHotelRoomDetail
import kr.ksw.visitkorea.domain.model.HotelRoomDetail
import javax.inject.Inject

class GetHotelRoomDetailUseCaseImpl @Inject constructor(
    private val detailRepository: DetailRepository
) : GetHotelRoomDetailUseCase {
    override suspend fun invoke(
        contentId: String
    ): Result<List<HotelRoomDetail>> = runCatching {
        detailRepository.getDetailInfo(
            contentId = contentId
        ).getOrThrow().map {
            it.toHotelRoomDetail()
        }
    }
}