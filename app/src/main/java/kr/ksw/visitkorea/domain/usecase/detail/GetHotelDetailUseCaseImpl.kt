package kr.ksw.visitkorea.domain.usecase.detail

import kr.ksw.visitkorea.data.repository.DetailRepository
import kr.ksw.visitkorea.domain.common.TYPE_HOTEL
import kr.ksw.visitkorea.domain.usecase.mapper.toHotelDetail
import kr.ksw.visitkorea.domain.usecase.model.HotelDetail
import javax.inject.Inject

class GetHotelDetailUseCaseImpl @Inject constructor(
    private val detailRepository: DetailRepository
): GetHotelDetailUseCase {
    override suspend fun invoke(
        contentId: String
    ): Result<HotelDetail> = runCatching {
        detailRepository.getDetailIntro(
            contentId = contentId,
            contentTypeId = TYPE_HOTEL
        ).getOrThrow().toHotelDetail()
    }
}