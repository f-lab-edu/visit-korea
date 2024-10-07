package kr.ksw.visitkorea.data.repository

import kr.ksw.visitkorea.data.mapper.toItems
import kr.ksw.visitkorea.data.remote.api.DetailApi
import kr.ksw.visitkorea.data.remote.dto.DetailCommonDTO
import kr.ksw.visitkorea.data.remote.dto.DetailImageDTO
import kr.ksw.visitkorea.data.remote.dto.DetailIntroDTO
import javax.inject.Inject

class DetailRepositoryImpl @Inject constructor(
    private val detailApi: DetailApi
): DetailRepository {
    override suspend fun getDetailCommon(contentId: Int): Result<DetailCommonDTO> = runCatching {
        detailApi.getDetailCommon(
            contentId = contentId
        ).toItems().first()
    }

    override suspend fun getDetailIntro(
        contentId: Int,
        contentTypeId: Int
    ): Result<DetailIntroDTO> = runCatching {
        detailApi.getDetailIntro(
            contentId = contentId,
            contentTypeId = contentTypeId
        ).toItems().first()
    }

    override suspend fun getDetailImage(
        contentId: Int
    ): Result<List<DetailImageDTO>> = runCatching {
        detailApi.getDetailImage(
            contentId = contentId
        ).toItems()
    }
}