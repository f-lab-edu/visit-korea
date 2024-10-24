package kr.ksw.visitkorea.data.repository

import kr.ksw.visitkorea.data.remote.dto.DetailCommonDTO
import kr.ksw.visitkorea.data.remote.dto.DetailImageDTO
import kr.ksw.visitkorea.data.remote.dto.DetailIntroDTO

interface DetailRepository {
    suspend fun getDetailCommon(
        contentId: String
    ): Result<DetailCommonDTO>

    suspend fun getDetailIntro(
        contentId: String,
        contentTypeId: String
    ): Result<DetailIntroDTO>

    suspend fun getDetailImage(
        contentId: String,
        imageYN: String
    ): Result<List<DetailImageDTO>>
}