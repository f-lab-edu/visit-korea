package kr.ksw.visitkorea.data.repository

import kr.ksw.visitkorea.data.remote.dto.DetailCommonDTO
import kr.ksw.visitkorea.data.remote.dto.DetailImageDTO
import kr.ksw.visitkorea.data.remote.dto.DetailIntroDTO

interface DetailRepository {
    suspend fun getDetailCommon(
        contentId: Int
    ): Result<DetailCommonDTO>

    suspend fun getDetailIntro(
        contentId: Int,
        contentTypeId: Int
    ): Result<DetailIntroDTO>

    suspend fun getDetailImage(
        contentId: Int
    ): Result<List<DetailImageDTO>>
}